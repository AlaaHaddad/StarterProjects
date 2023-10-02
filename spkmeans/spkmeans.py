import math
import pandas as pd
import numpy as np
import kmeanssp
import argparse


def start():  # gets arguments and starts the algorithm.
    parser = argparse.ArgumentParser()
    parser.add_argument("K", type=int, help="K is the number of clusters")
    parser.add_argument("goal", type=str, help="The goal which is need to calculate")
    parser.add_argument("file_name", type=str, help="The path to file which contains N observations")
    args = parser.parse_args()
    k = args.K
    goal = args.goal
    file_name = args.file_name
    # assertions

    if k is None:
        print("Invalid Input!")
        return -1
    if k < 0:
        print("Invalid Input!")
        return -1
    goals = ["wam", "ddg", "lnorm", "spk", "jacobi"]
    if goal not in goals:
        print("Invalid Input!")
        return -1
    if file_name is None:
        print("Invalid Input!")
        return -1
    KmeansPlus(k, goal, file_name)
    

def ReadData(filename):
    Points = pd.read_csv(filename, header=None)
    return Points


def printMatrix(matrix):
    for i in range(len(matrix)):
        print(','.join([format(matrix[i][j], ".4f") for j in range(len(matrix[i]))]))
    
    

def calc_dif(vec1, vec2):
    sum = 0
    for i in range(len(vec1)):
        sum += (vec1[i] - vec2[i]) ** 2
    return sum

     

def KmeansPlus(k, goal, filename):
    points = ReadData(filename)
    points = points.to_numpy()
    points = np.array(points)
    rows = points.shape[0]
    dimension = points.shape[1]

    if goal != "spk":
        if goal == "wam":
            goal = 2
        if goal == "ddg":
            goal = 3
        if goal == "lnorm":
            goal = 4
        if goal == "jacobi":
            goal = 5
        Data_list = points.tolist()
        matrix = kmeanssp.fit(Data_list, None, rows, dimension, k, 300, goal, 0)
        matrix = np.array(matrix)
        printMatrix(matrix)
        return
    else:
        np.random.seed(0)
        goal = 1
        if (k > rows or k < 0):
            print("Invalid Input!")
            return -1
        Data_list = points.tolist()
        matrix = kmeanssp.fit(Data_list, None, rows, dimension, k, 300, goal, 0)
        matrix = np.array(matrix)
        k = len(matrix[0])
        dimension = k
        if k==1:
            print("An Error Has Occurred!")
            return -1
    centroids = np.ndarray((k, dimension),float)
    indexes = [0 for i in range(k)]
    if centroids is None:
        print("An Error Has Occurred!")
        return -1
    indexes[0] = np.random.choice(rows)
    centroids[0] = matrix[indexes[0]]
    D = np.zeros(rows)
    P = np.zeros(rows)
    if D is None or P is None:
        print("An Error Has Occurred!")
        return 0
    sum = 0
    for i in range(1, k):
        ## finding the closest cluster for each point ##
        for l in range(rows):
            min = float("inf")
            for j in range(i):
                curr = calc_dif(matrix[l],centroids[j])
                if curr < min:
                    min = curr
            sum -= D[l]
            D[l] = min
            sum += D[l]
        ## calculating P for each point ##
        P = np.divide(D,sum)
        indexes[i] = np.random.choice(rows, p=P)
        centroids[i] = matrix[indexes[i]]
    ## printing the initial clusters indexes from all points array ##
    print(','.join([str(indexes[i]) for i in range(k)]),flush=True)
    ## the fit function from the C extension ##
    centroids = list(centroids.tolist())
    matrix = list(matrix.tolist())
    final_centroids = np.round(np.array(kmeanssp.fit(matrix, centroids, rows, k, k, 300, goal, 1)),decimals=4)
    ## printing the final clusters ##
    for i in range(k):
        print(','.join([format(final_centroids[i][j], ".4f") for j in range(len(final_centroids[1]))]))
    print('\n')
                    
                    
start()
