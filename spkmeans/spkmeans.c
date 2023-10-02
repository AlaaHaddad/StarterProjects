#include <stdio.h>
#include<stdlib.h>
#include <math.h>
#include <stdbool.h>
#include <string.h>
#include "spkmeans.h"

double*** read_file(char *file_name);
double** kmeans(int k,int max_iter,double eps,int d, int vecs_num, double **all_points, double **first_centroids);
double calc_norm(double* vec1, double* v2, int n);
double** weighted_matrix(double** input, int n, int d);
double** mult_mat(double** mat1, double** mat2, int n, int to_free);
double*** calc_laplacian(double** matrix, int n);
double mult_vec(double* vec1, double* vec2, int n);
double*** CreatePmatrix(int N, double **A_matrix);
double** transpose(int n, double** P);
double convergence(int n, double **matrix_1 , double **matrix_2);
void mergesort(double* arr, int left, int right);
double*** jacobi(double** L_norm, int n);
void merge(double arr[], int l, int m, int r);
double** find_largestK(double** matrix, double* values, int n, int k);
int Heuristic(double* values,int n);
double** renormalize(double** matrix, int n, int k);
void print_matrix(double **M, int row_number, int col_number);
bool is_diag(double **matrix, int n);
int IndexOfMin(double* x,double** arr,int k, int d);
double* divide(double* vec, int num, int vec_dim);
double distance(double* vec1, double* vec2, int d);
void freeMatrix(double** mat, int len);


int main(int argc, char *argv[]) {
    double ***values;
    double **all_points, **W, ***laplacian, **V, ***jacob;
    double **D_mat, **L_mat, **L_norm;
    int goal, i, n, d;
    if (argc != 3){
        printf("Invalid Input!");
        exit(1);
    }
    if(strcmp(argv[1],"wam")==0) goal = 0;
    else if(strcmp(argv[1],"ddg")==0) goal = 1;
    else if(strcmp(argv[1],"lnorm")==0) goal = 2;
    else if(strcmp(argv[1],"jacobi")==0) goal = 3;
    else{
        printf("Invalid Input!");
        exit(1);
    }
    values = read_file(argv[2]);
    all_points = values[0];
    n = (int) values[1][0][0];
    d = (int) values[1][1][0];
    if (goal == 3) {
        jacob = jacobi(all_points,n);
        V = jacob[0];
        L_mat = jacob[1];
        for(i=0;i<n;i++) {
            printf("%.4f",L_mat[i][i]);
            if(i != n-1) {
                printf(",");
            }
        }
        printf("\n");
        print_matrix(V,n,n);
        freeMatrix(V,n);
        freeMatrix(L_mat,n);
        free(jacob);
    } else {
        W = weighted_matrix(all_points,n,d);
        if(goal == 0) {
            print_matrix(W,n,n);
        } else {
            laplacian = calc_laplacian(W,n);
            D_mat = laplacian[1];
            L_norm = laplacian[0];
            if(goal == 1) {
                print_matrix(D_mat,n,n);
            } else {
                print_matrix(L_norm,n,n);
            }
            freeMatrix(D_mat,n);
            freeMatrix(L_norm,n);
            free(laplacian);
        }
        freeMatrix(W,n);
    }
    freeMatrix(all_points,n);
    freeMatrix(values[1],2);
    free(values);
    return 0;
}

/* kmeans algorithm taken from HW2 */
double** kmeans(int k,int max_iter,double eps,int d, int vecs_num, double **all_points, double **first_centroids) {
    double  **old_centroids, **sums, **centroids;
    int *cluster_size;
    int vec_dim ,num_of_vecs, iter_cnt = 0,index,i,j, check;
    check=false;
    num_of_vecs= vecs_num;
    vec_dim =d;
    /*Initializing cluster sizes to zero*/
    cluster_size = (int*) malloc(k*sizeof(int));
    old_centroids = (double**) malloc(k*sizeof(double));
    sums = (double**) malloc(k*sizeof(double*));
    centroids = (double**) malloc(k*sizeof(double));
    if(sums == NULL || cluster_size == NULL || old_centroids == NULL || centroids == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<k;i++){
        sums[i] = (double*) malloc(vec_dim*sizeof(double));
        centroids[i] = (double*) malloc(vec_dim*sizeof(double));
        old_centroids[i] =(double*) malloc(vec_dim*sizeof(double));
        if(sums[i] == NULL || centroids[i] == NULL || old_centroids[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
        for(j=0;j<vec_dim;j++) {
            centroids[i][j] = first_centroids[i][j];
        }
    }
    /* the Algorithm loop*/
    while(!check && iter_cnt<max_iter) {
        iter_cnt+=1;
        /*reset clusters size and the sums*/
        for(i=0; i<k; i++){
            cluster_size[i]=0;
            for( j=0; j<vec_dim;j++){
                sums[i][j] = 0;
            }
        }
        /*assigning points to the closest cluster*/
        for(j=0; j<num_of_vecs; j++){
            index = IndexOfMin(all_points[j], centroids, k, vec_dim);
            cluster_size[index] +=1;
            for(i=0; i<vec_dim; i++){
                sums[index][i] += all_points[j][i];
            }
        }
        /*Substituting centroirds into old_centroids before updating centroids*/
        for(i=0; i<k; i++){
            for(j=0; j<vec_dim; j++){
                old_centroids[i][j] = centroids[i][j];
            }
        }
        /* updating the centroids*/
        for(i=0;i<k;i++) {
            if(cluster_size[i] == 0) {
                for(j=0;j<d;j++) {
                    centroids[i][j] = 0;
                }
                continue;
            }
            centroids[i] = divide(sums[i],cluster_size[i], vec_dim);
        }
        /*Calculating the difference between the old and new for the algorithm loop*/
        for(i=0;i<k;i++){
           if(distance(centroids[i], old_centroids[i],vec_dim) > eps){
                check = false;
                break;
            }
            check = true;
        }
    }
    /*releasing the memory*/
    for(i=0;i<num_of_vecs;i++){
        free(all_points[i]);
    }
    free(all_points);
    for(i=0; i<k;i++){
        free(old_centroids[i]);
        free(sums[i]);
    }
    free(sums);
    free(cluster_size);
    free(old_centroids);
    return centroids;
}

/*Read the input file*/
double*** read_file(char *file_name){
    FILE *in_file = fopen(file_name, "r");
    char c;
    int i ,j, d=0, n=0;
    double ***returned, **input, point;
    returned = (double***) malloc(2*sizeof(double**));
    returned[1] = (double**) malloc(2*sizeof(double*));
    returned[1][0] = (double*) malloc(sizeof(double));
    returned[1][1] = (double*) malloc(sizeof(double));
    if(returned == NULL || returned[1] == NULL || returned[1][0] == NULL || returned[1][1] == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    if (in_file == NULL){
        printf("Invalid Input!");
        exit(1);
    }
    c = fgetc(in_file);
    while (!feof(in_file)) {
        if (c == ',') {
            d++;
        }
        else if (c == '\n') {
            d++;
            n++;
        }
        c = fgetc(in_file);
    }
    d = d/n;
    rewind(in_file);
    input = (double**) malloc(n*sizeof(double*));
    if (input == NULL) {
        printf("An Error has Occurred!");
        exit(1);
    }
    for (i = 0;i<n;i++) {
        input[i] = (double*) malloc(d*sizeof(double));
        if (input[i] == NULL) {
            printf("An Error has Occurred!");
            exit(1);
        }
    }
    for(i=0;i<n;i++) {
        for (j=0;j<d;j++) {
            fscanf(in_file,"%lf",&point);
            input[i][j] = (double) point;
            fgetc(in_file);
        }
    }
    fclose(in_file);
    returned[0] = input;
    returned[1][0][0] = n;
    returned[1][1][0] = d;
    return returned;
}

/*Create the weighted adjacency matrix W*/
double** weighted_matrix(double** input, int n, int d){
    int i, j;
    double** matrix, curr;
    matrix = (double**) malloc(n*sizeof(double*));
    if (matrix == NULL) {
        printf("An Error has Occurred!");
        exit(1);
    }
    for (i=0; i<n ;i++) {
        matrix[i] = (double*) calloc(n,sizeof(double));
        if (matrix[i] == NULL) {
            printf("An Error has Occurred!");
            exit(1);
        }
        matrix[i][i] = 0.0;
    }
    for (i=0; i<n; i++) {
        for (j=i+1; j<n; j++) {
            curr = calc_norm(input[i],input[j],d);
            curr = -curr/2;
            matrix[i][j] = exp(curr);
            matrix[j][i] = matrix[i][j];
        }
    }
    return matrix;
} 

/* calculates Laplance matrix */
double*** calc_laplacian(double** matrix, int n) {
    double*** returned;
    double **D_mat, **D_sqrt, **L_mat, sum;
    int j,i;
    returned = (double***) malloc(2*sizeof(double**));
    D_mat = (double**) malloc(n*sizeof(double*));
    D_sqrt = (double**) malloc(n*sizeof(double*));
    if (D_mat == NULL || returned == NULL || D_sqrt == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for (i=0; i<n; i++) {
        D_mat[i] = (double*) calloc(n,sizeof(double));
        D_sqrt[i] = (double*) calloc(n,sizeof(double));
        if (D_mat[i] == NULL || D_sqrt[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(i=0;i<n;i++) {
        sum = 0;
        for(j=0;j<n;j++) {
            sum += matrix[i][j];
        }
        D_mat[i][i] = sum;
        D_sqrt[i][i] = 1/(sqrt(sum));
    }
    L_mat = mult_mat(D_sqrt,matrix,n,1);
    L_mat = mult_mat(L_mat,D_sqrt,n,0);
    for(i=0;i<n;i++) {
        for(j=0;j<n;j++) {
            if(i==j) {
                L_mat[i][i] = 1 - L_mat[i][i];
            } else {
                L_mat[i][j] = -L_mat[i][j];
            }
        }
    }
    freeMatrix(D_sqrt,n);
    returned[0] = L_mat;
    returned[1] = D_mat;
    return returned;
}

/* returns eigenvalues of matrix ordered */
double*** jacobi(double** L_mat, int n) {
    int i,j,r,t,rotations=0;
    double** P_mat, **V_mat, ***returned, ***created_P, **old_L, **P_trans;
    double conv=0;
    returned = (double***) malloc(2*sizeof(double**));
    old_L = (double**) malloc(n*sizeof(double*));
    V_mat = (double**) malloc(n*sizeof(double*));
    if(V_mat == NULL || old_L == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n;i++) {
        V_mat[i] = (double*) calloc(n,sizeof(double));
        old_L[i] = (double*) calloc(n,sizeof(double));
        if(V_mat[i] == NULL || old_L[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(r=0;r<n;r++) {
        for(t=0;t<n;t++) {
            V_mat[r][t] = 0;
            old_L[r][t] = L_mat[r][t];
        }
        V_mat[r][r] = 1;
    }
    if(!is_diag(L_mat,n)) {
        while(((conv>0.00001) && (rotations<100)) || rotations == 0) {
            rotations++;
            created_P = CreatePmatrix(n,L_mat);
            P_mat = created_P[0];
            i = (int) created_P[1][0][0];
            j = (int) created_P[1][0][1];
            P_trans = transpose(n,P_mat);
            L_mat = mult_mat(P_trans,L_mat,n,1);
            L_mat = mult_mat(L_mat,P_mat,n,0);
            conv = convergence(n,old_L,L_mat);
            V_mat = mult_mat(V_mat,P_mat,n,0);
            for(r=0;r<n;r++) {
                old_L[r][i] = L_mat[r][i];
                old_L[r][j] = L_mat[r][j];
                old_L[i][r] = L_mat[i][r];
                old_L[j][r] = L_mat[j][r];
                old_L[i][i] = L_mat[i][i];
                old_L[j][j] = L_mat[j][j];
                old_L[i][j] = 0;
                old_L[j][i] = 0;
            }
            freeMatrix(created_P[1],1);
            freeMatrix(P_mat,n);
            freeMatrix(P_trans,n);
            free(created_P);
        }
    }
    freeMatrix(old_L,n);
    returned[0] = V_mat;
    returned[1] = L_mat;
    return returned;
}

/*Creating the rotations matrix P for Jacobi algorithm*/
double*** CreatePmatrix(int n , double **A_matrix) {
    double max = fabs(A_matrix[0][1]);
    int i,j, k=0, z=1, sign;
    double angle, s, c, t;
    double** values = (double**) malloc(sizeof(double*));
    double** matrix = (double**) malloc(n*sizeof(double*));
    double*** final = (double***) malloc(2*sizeof(double**));
    if(matrix == NULL || values == NULL || final == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n;i++) {
        matrix[i] = (double*) malloc(n*sizeof(double));
        if(matrix[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    values[0] = (double*) malloc(2*sizeof(double));
    if(values[0] == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for (i=0; i<n; i++) {
        for (j=0; j<n; j++) {
            if(i!=j && fabs(A_matrix[i][j])==max){
                continue;
            }
            if(i!=j && fabs(A_matrix[i][j])>(max)){
                max = fabs(A_matrix[i][j]);
                k = i;
                z = j;
            }
        }
    }
    if(A_matrix[k][z]< 0){
        max = -max;
    }
    angle = (A_matrix[z][z] - A_matrix[k][k])/(2*max);
    if(angle >= 0){
        sign = 1;
    }else{
        sign = -1;
    }
    t = (sign)/((sign*(angle)) + (sqrt(pow(angle,2)+1)));
    c = 1/sqrt(pow(t,2) +1);
    s = t*c;

    for (i=0; i<n; i++) {
        for (j=0; j<n; j++){
            if(i==j){
                matrix[i][j] = 1;
            }
            else{
                matrix[i][j]=0;
            }
        }
    }
    matrix[k][k] = c;
    matrix[z][z] = c;

    matrix[k][z] = s;
    matrix[z][k] = -s;

    values[0][0] = k;
    values[0][1] = z;

    final[0] = matrix;
    final[1] = values;
    return final;
}

/* Eigengap Heuristic to find k*/
int Heuristic(double* values,int n) {
    int i,k=0;
    double* gap = (double*) malloc(n*sizeof(double));
    if(gap == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n-1;i++) {
        gap[i] = values[i] - values[i+1];
    }
    for(i=0;i<=n/2;i++) {
        if(k<gap[i]) {
            k = i+1;
        }
    }
    free(gap);
    return k;
}

/* merge sort and merge taken from edureka.com and modified */
void mergesort(double* arr, int left, int right) {
    int middle;
    if(left<right) {
        middle = left + (right-left)/2;
        mergesort(arr, left, middle);
        mergesort(arr, middle+1, right);
        merge(arr,left,middle,right);
    }
}

void merge(double arr[], int l, int m, int r) { 
    int i, j, k; 
    int n1 = m - l + 1; 
    int n2 =  r - m; 
    double *L, *R; 
    L = (double*) malloc(n1*sizeof(double));
    R = (double*) malloc(n2*sizeof(double));
    if (L == NULL || R == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for (i = 0; i < n1; i++) {
        L[i] = arr[l + i]; 
    }
    for (j = 0; j < n2; j++) {
        R[j] = arr[m + 1+ j]; 
    }
    i = 0; 
    j = 0; 
    k = l; 
    while (i < n1 && j < n2) { 
        if (L[i] >= R[j]) { 
            arr[k] = L[i]; 
            i++; 
        } 
        else { 
            arr[k] = R[j]; 
            j++; 
        } 
        k++; 
    } 
    while (i < n1) { 
        arr[k] = L[i]; 
        i++; 
        k++; 
    } 
    while (j < n2) { 
        arr[k] = R[j]; 
        j++; 
        k++; 
    } 
    free(L);
    free(R);
}

/* renormalizes matrix */
double** renormalize(double** matrix, int n, int k) {
    int i,j;
    double sum;
    double** returned_mat = (double**) malloc(n*sizeof(double*));
    if (returned_mat == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n;i++) {
        returned_mat[i] = (double*) malloc(k*sizeof(double));
        if(returned_mat[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(i=0;i<n;i++) {
        sum = 0;
        for(j=0;j<k;j++) {
            sum += pow(matrix[i][j],2);
        }
        sum = sqrt(sum);
        if(sum == 0) {
            for(j=0;j<k;j++) {
                returned_mat[i][j] = 0;
            }
        } else {
            for(j=0;j<k;j++) {
                returned_mat[i][j] = matrix[i][j]/sum;
            }
        }
    }
    return returned_mat;
}

/* returns largest k vectors from columns of matrix */
double** find_largestK(double** matrix, double* values, int n, int k) {
    int j,i,index = 0;
    double max;
    double *temp;
    double **returned = (double**) malloc(n*sizeof(double*));
    temp = (double*) malloc(k*sizeof(double));
    if(returned == NULL || temp == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n;i++) {
        returned[i] = (double*) malloc(k*sizeof(double));
        if(returned[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(j=0;j<k;j++) {
        max = -100000;
        for(i=0;i<n;i++) {
            if(values[i]>max && (j == 0 || values[i]<temp[j-1])) {
                max = values[i];
                index = i;
            }
        }
        temp[j] = max; 
        for(i=0;i<n;i++) {
            returned[i][j] = matrix[i][index];
        }
    }
    free(temp);
    return returned;
}

/* returns the L2 norm of vec1 and vec2 */
double calc_norm(double* vec1, double* vec2, int d) {
    int i;
    double sum = 0;
    for (i=0;i<d;i++) {
        sum = sum + pow((vec1[i] - vec2[i]),2);
    }
    return sqrt(sum);
}

/* returns the multiplication of vec1 and vec2 */
double mult_vec(double* vec1, double* vec2, int n) {
    double vec = 0;
    int i;
    for (i=0;i<n;i++) {
        vec += vec1[i] * vec2[i];
    }
    return vec;
}

/* returns the multiplication of mat1 and mat2 */
double** mult_mat(double** mat1, double** mat2, int n, int to_free) {
    int i,j;
    double** mat = (double**) malloc(n*sizeof(double*));
    double** mat2_tran = transpose(n,mat2);
    if (mat == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for (i=0; i<n; i++) {
        mat[i] = (double*) malloc(n*sizeof(double*));
        if(mat[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(i=0; i<n; i++) {
        for(j=0; j<n; j++) {
            mat[i][j] = mult_vec(mat1[i],mat2_tran[j],n);
        }
    }
    if(to_free) {
        freeMatrix(mat2,n);
    } else {
        freeMatrix(mat1,n);
    }
    freeMatrix(mat2_tran,n);
    return mat;
}

/*Creating transpose of matrix*/
double** transpose(int n, double** P){
    int i,j;
    double** T = (double**) malloc(n*sizeof(double*));
    if(T == NULL) {
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0;i<n;i++) {
        T[i] = (double*) malloc(n*sizeof(double));
        if(T[i] == NULL) {
            printf("An Error Has Occurred!");
            exit(1);
        }
    }
    for(i=0; i<n; i++){
        for(j=0; j<n; j++){
            T[i][j] = P[j][i];
        }
    }
    return T;
}

/*For the stop iterations*/
double convergence(int n, double **matrix_1 , double **matrix_2){
    int i , j;
    double off_1=0 , off_2=0;

    for (i=0; i<n; i++){
        for (j=0; j<n; j++){
            if (i!=j){
                off_1 += pow(matrix_1[i][j],2);
                off_2 += pow(matrix_2[i][j],2);
            }
        }
    }
    return off_1 - off_2;
}

/* prints matrix */
void print_matrix(double **M, int row_number, int col_number){
    int i,j;
    for(i=0;i<row_number;i++){
        for(j=0;j<col_number;j++){
            printf("%.4f",M[i][j]);
            if(j!=col_number-1) printf(",");
        }
        printf("\n");
    }
}

/* returns true if matrix with dimension (n,n) is diagonal */
bool is_diag(double **matrix, int n) {
    int i,j;
    for(i=0;i<n;i++) {
        for(j=0;j<n;j++) {
            if(matrix[i][j] != 0 && i != j) {
                return false;
            }
        }
    }
    return true;
}

/* divides vec by num, taken from HW2 */
double* divide(double* vec, int num, int vec_dim){
    double *res;
    int i;
    res = (double*) malloc(vec_dim*sizeof(double));
    if(res == NULL){
        printf("An Error Has Occurred!");
        exit(1);
    }
    for(i=0; i<vec_dim; i++){
        res[i] = vec[i]/num;
    }
    return res;
}

/*calculates the distance between vec1 and vec2, taken from HW2*/
double distance(double* vec1, double* vec2, int d){
    double* temp;
    double sum=0;
    int i;
    temp = (double*) malloc(d*sizeof(double));
    for(i=0;i<d;i++){
        temp[i] = vec1[i] - vec2[i];
        sum = sum + temp[i]*temp[i];
    }
    free(temp);
    return sqrt(sum);
}


/*finds the closest centroid to assign x to its cluster, taken from HW2.*/
int IndexOfMin(double* x,double** arr,int k, int d) {
    int index = 0, i;
    double diff_val = distance(arr[0],x,d);
    double min = diff_val;
    for(i=1;i<k;i++) {
        diff_val = distance(arr[i], x, d);
        if (diff_val < min) {
            min = diff_val;
            index = i;
        }
    }
    return index;
}

/* function to free memory */
void freeMatrix(double** mat, int len){
    int i;
    for (i = 0; i < len; i++) {
        free(mat[i]);
    }
    free(mat);
}
