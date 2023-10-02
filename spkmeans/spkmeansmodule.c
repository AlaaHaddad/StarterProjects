#include <stdio.h>
#include <stdlib.h>
#include <Python.h>
#include <malloc.h>
#include "spkmeans.h"
#define PY_SSIZE_T_CLEANS

static PyObject* kmeans_capi(PyObject *self, PyObject *args);
static double **parse_arrays(PyObject* _list, int num_row, int num_col);
static PyObject* p_main(PyObject *data, PyObject *centroids, int rows, int dim, int k, int max_iter, int goal, int part);


static PyObject* p_main(PyObject *data, PyObject *centroids, int rows, int dim, int k, int max_iter, int goal, int part) {
    double **vecs_mat, **initial_centroids;
    PyObject *py_curr_vec, *py_curr_centroid, *py_result, *temp, *num, *vec, *array;
    Py_ssize_t i,j,kds;
    double **Jacobi, **W, **L_norm, **D, **V, ***laplacian_matrices, ***jacobi_matrices, **DataPoints;
    double *values, *copy, **returned_V, **U, **T, **final_centroids;
    int vecs_num;

    DataPoints = parse_arrays(data, rows, dim);
    if(goal<0 || goal>5) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    if (part == 0) {
        if (goal == 5) {
            returned_V = (double**) malloc((rows+1)*sizeof(double*));
            if(returned_V == NULL) {
                printf("An Error Has Occurred!");
                exit(1);
            }
            returned_V[0] = (double*) malloc(rows*sizeof(double));
            if(returned_V[0] == NULL) {
                printf("An Error Has Occurred!");
                exit(1);
            }
            jacobi_matrices = jacobi(DataPoints,rows);
            Jacobi = jacobi_matrices[1];
            V = jacobi_matrices[0];
            for(i=0;i<rows;i++) {
                returned_V[0][i] = Jacobi[i][i];
                returned_V[i+1] = V[i];
            }
            array = PyList_New(rows+1);
            if (!array){
                exit(1);
            }
            for(i=0; i<rows+1; i++){
                vec = PyList_New(rows);
                if (!vec){
                    exit(1);
                }
                for (j = 0; j < rows; j++) {
                    num = PyFloat_FromDouble(returned_V[i][j]);
                    if (!num) {
                        Py_DECREF(vec);
                        exit(1);
                    }PyList_SET_ITEM(vec, j, num);
                }PyList_SET_ITEM(array, i, vec);
            }
            free(jacobi_matrices);
            freeMatrix(Jacobi,rows);
            free(V);
            freeMatrix(returned_V,rows+1);
            return array;
        }
        array = PyList_New(rows);
        if(!array) {
            return NULL;
        }
        W = weighted_matrix(DataPoints,rows,dim);
        if(goal == 2) {
            for(i=0; i<rows; i++){
                vec = PyList_New(rows);
                if(!vec) return NULL;
                for(j=0; j<rows; j++){
                    num = PyFloat_FromDouble(W[i][j]);
                    if(!num){
                        Py_DECREF(vec);
                        return NULL;
                    }PyList_SetItem(vec,j,num);
                }PyList_SetItem(array, i, vec);
            }
            return array;
        }
        laplacian_matrices = calc_laplacian(W,rows);
        D = laplacian_matrices[1];
        L_norm = laplacian_matrices[0];
        if(goal == 3) {
            for(i=0; i<rows; i++) {
                vec = PyList_New(rows);
                if(!vec) return NULL;
                for(j=0; j<rows; j++) {
                    num = PyFloat_FromDouble(D[i][j]);
                    if(!num) {
                        Py_DECREF(vec);
                        return NULL;
                    }PyList_SetItem(vec, j, num);
                }PyList_SetItem(array, i, vec);
            }
        }
        if(goal == 4) {
            for(i=0; i<rows; i++) {
                vec = PyList_New(rows);
                if(!vec) return NULL;
                for(j=0; j<rows; j++) {
                    num = PyFloat_FromDouble(L_norm[i][j]);
                    if(!num) {
                        Py_DECREF(vec);
                        return NULL;
                    }PyList_SetItem(vec, j, num);
                }PyList_SetItem(array, i, vec);
            }
        }
        if(goal == 3 || goal == 4) {
            freeMatrix(D,rows);
            freeMatrix(L_norm,rows);
            free(laplacian_matrices);
            return array;
        }
        values = (double*) malloc(rows*sizeof(double));
        copy = (double*) malloc(rows*sizeof(double));
        if(values == NULL || copy == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
        jacobi_matrices = jacobi(L_norm,rows);
        Jacobi = jacobi_matrices[1];
        V = jacobi_matrices[0];
        for(i=0;i<rows;i++) {
            values[i] = Jacobi[i][i];
            copy[i] = Jacobi[i][i];
        }
        if(k==0) {
            mergesort(copy,0,rows-1);
            k = Heuristic(copy,rows);
        }
        U = find_largestK(V,values,rows,k);
        T = renormalize(U,rows,k);
        for(i=0; i<rows; i++) {
            vec = PyList_New(k);
            if(!vec) return NULL;
            for(j=0; j<k; j++) {
                num = PyFloat_FromDouble(T[i][j]);
                if(!num) {
                    Py_DECREF(vec);
                    return NULL;
                }PyList_SetItem(vec, j, num);
            }PyList_SetItem(array, i, vec);
        }
        freeMatrix(D,rows);
        free(laplacian_matrices);
        freeMatrix(Jacobi,rows);
        freeMatrix(V,rows);
        free(jacobi_matrices);
        freeMatrix(U,rows);
        freeMatrix(T,rows);
        free(values);
        free(copy);
        return array;
    } else if (part == 1) {
        vecs_num = (int) PyObject_Length(data);
        vecs_mat = (double**) malloc(vecs_num*sizeof(double*));
        if(vecs_mat == NULL){
            printf("An Error Has Occurred!");
            exit(1);
        }
        if(k>=vecs_num){
            printf("Invalid Input!");
            exit(1);
        }
        for(i=0;i<vecs_num;i++){
            vecs_mat[i] = (double*) malloc(dim*sizeof(double));
            if(vecs_mat[i] == NULL){
                printf("An Error Has Occurrred!");
                exit(1);
            }
        }
        initial_centroids = (double**) malloc(k*sizeof(double*));
        if(initial_centroids == NULL){
            printf("An Error Has Occurred!");
            exit(1);
        }
        for(i=0;i<k;i++){
            initial_centroids[i] = (double*) malloc(dim*sizeof(double));
            if(initial_centroids[i] == NULL){
                printf("An Error Has Occurred!");
                exit(1);
            }
        }
        for(i=0;i<vecs_num;i++){
            py_curr_vec = PyList_GetItem(data,i);
            for(j=0;j<dim;j++){
                vecs_mat[i][j] = PyFloat_AsDouble(PyList_GetItem(py_curr_vec,j));
            }
        }
        for(i=0;i<k;i++){
            py_curr_centroid = PyList_GetItem(centroids,i);
            for(j=0;j<dim;j++){
                initial_centroids[i][j] = PyFloat_AsDouble(PyList_GetItem(py_curr_centroid,j));
            }
        }
        final_centroids = kmeans(k,300,0,dim, vecs_num, vecs_mat, initial_centroids);
        py_result = PyList_New(k);
        for(i=0;i<k;i++){
            temp = PyList_New(dim);
            for(j=0;j<dim;j++){
                PyList_SetItem(temp,j,PyFloat_FromDouble(final_centroids[i][j]));
            }
            PyList_SetItem(py_result,i,temp);
        }
        freeMatrix(initial_centroids,k);
        return(py_result);
    }
}
/* the wrapping function, parses PyObjects */
static PyObject* kmeans_capi(PyObject *self, PyObject *args){

    PyObject *data, *centroids;
    int rows, dim, K, MAX_ITER;
    int goal , part;
    if(!PyArg_ParseTuple(args, "OOiiiiii", &data, &centroids, &rows, &dim, &K, &MAX_ITER , &goal, &part)){
        return NULL;
    }
    return Py_BuildValue("O", p_main(data, centroids, rows, dim, K, MAX_ITER, goal, part));
}


/* functino that parses the data and puts them in arrays */
static double **parse_arrays(PyObject* _list, int num_row, int num_col) {

    int i, j;
    Py_ssize_t Py_i, Py_j;
    double **parsed_data;
    parsed_data = malloc(num_row * sizeof(double*));
    assert(parsed_data!=NULL);
    PyObject* item; PyObject* num;
    for (i = 0; i < num_row; i++) {
        Py_i = (Py_ssize_t)i;
        parsed_data[Py_i] = malloc(num_col * sizeof(double));
        assert(parsed_data[Py_i]!=NULL);
        item = PyList_GetItem(_list, Py_i);
        if (!PyList_Check(item)){ /* Skips non-lists */
            continue;
        }
        for (j = 0; j < num_col; j++) {
            Py_j = (Py_ssize_t)j;
            num = PyList_GetItem(item, Py_j);
            if (!PyFloat_Check(num)) continue; /* Skips non-floats */
            parsed_data[Py_i][Py_j] = PyFloat_AsDouble(num);
        }
    }
    return parsed_data;
}


/* Array that tells python what methods this module has */
static PyMethodDef capiMethods[] = {

        {"fit",
                (PyCFunction) kmeans_capi,
                     METH_VARARGS,
                PyDoc_STR("calculates the centroids using kmeans algorithm")},
        {NULL, NULL, 0, NULL}
};

/* This struct initiates the module using the above definition. */
static struct PyModuleDef moduledef = {
        PyModuleDef_HEAD_INIT,
        "kmeanssp",           //name of module
        NULL,                   //module documentation
        -1,                     //size of per-interpreter
        capiMethods
};

/* Module Creation */
PyMODINIT_FUNC
PyInit_kmeanssp(void) {
    PyObject *m;
    m = PyModule_Create(&moduledef);
    if (!m) {
        return NULL;
    }
    return m;
}
