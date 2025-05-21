# [Array and Simple Queries](https://www.hackerrank.com/challenges/array-and-simple-queries/problem)

Given two numbers ***N*** and ***M . N*** indicates the number of elements in the array ***A[](1 - indexed)*** and ***M*** indicates the number of queries. You need to perform two types of queries on the array ***A[]***.

You are given ***M*** queries. Queries can be of two types, type **1** and type **2**.

- Type 1 queries are represented as `1 i j` : Modify the given array by removing elements from ***i*** to ***j*** and adding them to the front.

- Type 2 queries are represented as `2 i j` : Modify the given array by removing elements from ***i*** to ***j*** and adding them to the back.

Your task is to simply print **|A[1] - A[N]|** of the resulting array after the execution of ***M*** queries followed by the resulting array.

**Note:** While adding at back or front the order of elements is preserved.

## Input Format

First line consists of two space-separated integers, ***N*** and ***M***.
Second line contains ***N*** integers, which represent the elements of the array.
***M*** queries follow. Each line contains a query of either type 1 or type 2 in the form ***type i j***.

## Constraints

***1 ≤ N ≤ 10^5***
***1 ≤ M ≤ 10^5***
***1 ≤ i ≤ j ≤ N***
***1 ≤ A[i] ≤ 10^9***

## Output Format

Print the absolute value i.e. **abs(A[1] - A[N])** in the first line.
Print elements of the resulting array in the second line. Each element should be seperated by a single space.

## Sample Input

`8 4`\
`1 2 3 4 5 6 7 8`\
`1 2 4`\
`2 3 5`\
`1 4 7`\
`2 1 4`

## Sample Output

`1`\
`2 3 6 5 7 8 4 1`

## Explanation

Given array is **{1,2,3,4,5,6,7,8}**.\
After execution of query **1 2 4**, the array becomes **{2, 3, 4, 1, 5, 6, 7, 8}**.\
After execution of query **2 3 5**, the array becomes **{2, 3, 6, 7, 8, 4, 1, 5}**.\
After execution of query **1 4 7**, the array becomes **{7, 8, 4, 1, 2, 3, 6, 5}**.\
After execution of query **2 1 4**, the array becomes **{2, 3, 6, 5, 7, 8, 4, 1}**.\
Now **|A[1] - A[N]|** is **|(2 - 1)|** i.e. **1** and array is **23657841**.
