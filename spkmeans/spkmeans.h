#ifndef EE9D2BC4_F44A_4E73_B930_EF790C4C2CA1
#define EE9D2BC4_F44A_4E73_B930_EF790C4C2CA1

#include <stdbool.h>

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


#endif /* EE9D2BC4_F44A_4E73_B930_EF790C4C2CA1 */
