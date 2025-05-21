# Solution Explanation
The solution leverages a data structure called a Treap to efficiently perform the operations.

## Treap
A Treap is a hybrid data structure that combines the features of a binary search tree (BST) and a max heap. It provides efficient operations for splitting, merging, and maintaining a balanced structure.

## Node Structure
The **Node** class represents a node in the Treap. It contains the value, priority, count, and pointers to the left and right children. The count keeps track of the number of nodes in the subtree rooted at this node. The priority is assigned randomly to ensure that the Treap remains balanced on average.

## Merging
The **merge** method takes two Treap nodes and combines them into a single Treap, preserving the order and heap property.

## Splitting
The **splitByOrder** method splits a Treap into two parts based on a given order. This is useful to extract segments of the array.

## Main Algorithm
**1. Initialize Treap**: The Treap is initialized with the given array, and each element is added as a node with random priority.

**2. Process Queries**: For each query, the code splits the Treap based on the indices **i** and **j**, obtaining three parts.

- If the query is of type 1, the middle part is added to the front.
- If the query is of type 2, the middle part is added to the back.

**3. Compute Result**: After executing all queries, the code computes the absolute difference between the first and last elements of the resulting array and prints it along with the elements.

## Example
For the given example:

`8 4`\
`1 2 3 4 5 6 7 8`\
`1 2 4`\
`2 3 5`\
`1 4 7`\
`2 1 4`

The output is:

`1`\
`2 3 6 5 7 8 4 1`

## Conclusion
This code provides an efficient solution to the problem by using a Treap data structure. It allows for fast manipulation of the array according to the given queries and computes the final result in an elegant manner.




