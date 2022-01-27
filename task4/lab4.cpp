#include <stdio.h>
#include <omp.h>
#include "mpi.h"
#include <math.h>
#include <stdlib.h>
#define N 1000
MPI_Status status;



double a[N][N], b[N], c[N];

int main(int argc, char** argv)
{
    int numtasks, taskid, numworkers, source, dest, rows, offset, i, j, k;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &taskid);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);

    numworkers = numtasks - 1;

    if (taskid == 0) {

        for (i = 0; i < N; i++) {
            b[i] = rand() % 10;
        }

        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                a[i][j] = rand() % 10;
            }
        }

        printf("Vector: \n");
        for (i = 0; i < N; i++) {
            printf("%.f ", b[i]);
        }
        printf("\n\n");

        printf("Matrix: \n");
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                printf("%.f ", a[i][j]);
            }
            printf("\n");
        }


        /* send matrix data to the worker tasks */
        rows = N / numworkers;
        offset = 0;

        for (dest = 1; dest <= numworkers; dest++)
        {
            MPI_Send(&offset, 1, MPI_INT, dest, 1, MPI_COMM_WORLD);
            MPI_Send(&rows, 1, MPI_INT, dest, 1, MPI_COMM_WORLD);
            MPI_Send(&a[offset][0], rows * N, MPI_DOUBLE, dest, 1, MPI_COMM_WORLD);
            MPI_Send(&b, N * N, MPI_DOUBLE, dest, 1, MPI_COMM_WORLD);
            offset = offset + rows;
        }

        /* wait for results from all worker tasks */
        for (i = 1; i <= numworkers; i++)
        {
            source = i;
            MPI_Recv(&offset, 1, MPI_INT, source, 2, MPI_COMM_WORLD, &status);
            MPI_Recv(&rows, 1, MPI_INT, source, 2, MPI_COMM_WORLD, &status);
            MPI_Recv(&c[offset], rows * N, MPI_DOUBLE, source, 2, MPI_COMM_WORLD, &status);
        }



        printf("\nResult:\n");
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++){
                c[i] += a[i][j] * b[j];
                }
        }

        for (int i = 0; i < N; i++) {
            printf("%.f ", c[i]);
        }
        printf("\n");
     

    }

    if (taskid > 0) {
        source = 0;
        MPI_Recv(&offset, 1, MPI_INT, source, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(&rows, 1, MPI_INT, source, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(&a, rows * N, MPI_DOUBLE, source, 1, MPI_COMM_WORLD, &status);
        MPI_Recv(&b, N * N, MPI_DOUBLE, source, 1, MPI_COMM_WORLD, &status);

        /* Matrix multiplication */
        for (k = 0; k < N; k++)
            c[k] = 0.0;
            for (i = 0; i < rows; i++) {
                for (j = 0; j < N; j++)
                    c[k] += a[i][j] * b[j];
            }


        MPI_Send(&offset, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
        MPI_Send(&rows, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
        MPI_Send(&c, rows * N, MPI_DOUBLE, 0, 2, MPI_COMM_WORLD);
    }

    MPI_Finalize();

}