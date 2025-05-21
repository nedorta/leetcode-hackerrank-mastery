# [Matrix Layer Rotation](https://www.hackerrank.com/challenges/matrix-rotation-algo/problem)

You are given a 2D matrix of dimension **M**x**N** and a positive integer **R**. You have to rotate the matrix **R** times and print the resultant matrix. Rotation should be in anti-clockwise direction.

Rotation of a **4x5** matrix is represented by the following figure. Note that in one rotation, you have to shift elements by one step only.

![Matrix Rotation](https://hr-challenge-images.s3.amazonaws.com/2517/matrix-rotation.png)

It is guaranteed that the minimum of **M** and **N** will be even.

As an example rotate the Start matrix by **2**:

`Start`\
`[[1, 2, 3, 4], [12, 1, 2, 5], [11, 4, 3, 6], [10, 9, 8, 7]]`\

`First`\
`[[2, 3, 4, 5], [1, 2, 3, 6], [12, 1, 4, 7], [11, 10, 9, 8]]`\

`Second`\
`[[3, 4, 5, 6], [2, 3, 4, 7], [1, 2, 1, 8], [12, 11, 10, 9]]`\


     Start           First           Second
     1  2  3  4      2  3  4  5      3  4  5  6
    12  1  2  5  ->  1  2  3  6  ->  2  3  4  7
    11  4  3  6     12  1  4  7      1  2  1  8
    10  9  8  7     11 10  9  8     12 11 10  9

## Function Description

Complete the matrixRotation function in the editor below.

matrixRotation has the following parameter(s):

- int matrix[m][n]: a 2D array of integers
- int r: the rotation factor

## Prints

It should print the resultant 2D integer array and return nothing. Print each row on a separate line as space-separated integers.

## Input Format

The first line contains three space separated integers, **M**, **N** and **R**, the number of rows and columns in the **matrix**, and the required rotation.

The next **M** lines contain **N** space-separated integers representing the elements of a row of the **matrix**.

## Constraints

**2 ≤ M, N ≤ 300**\
**1 ≤ R ≤ 10^9**\
**min(M, N) % 2 = 0**\
**1 ≤ matrix[i][j] ≤ 10^8**, where **0 ≤ i < M** and **0 ≤ j < N**

## Sample Input

`4 4 2`\
`inputMatrix = [[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12], [13, 14, 15, 16]]`

## Sample Output

`outputMatrix = [[3, 4, 8, 12], [2, 11, 10, 16], [1, 7, 6, 15], [5, 9, 13, 14]]`

## Explanation

`Start`\
`[[1, 2, 3, 4], [5, 6, 7, 8], [9, 10, 11, 12], [13, 14, 15, 16]]`\

`First`\
`[[2, 3, 4, 8], [1, 7, 11, 12], [5, 6, 10, 16], [9, 13, 14, 15]]`\

`Second`\
`[[3, 4, 8, 12], [2, 11, 10, 16], [1, 7, 6, 15], [5, 9, 13, 14]]`\


