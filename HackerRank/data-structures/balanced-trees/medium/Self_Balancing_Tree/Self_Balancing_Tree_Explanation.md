# Problem Definition
The code implements an AVL tree insertion algorithm. An AVL tree is a self-balancing binary search tree, where the difference between heights of left and right subtrees cannot be more than one for all nodes.

## Solution Explanation
The implementation focuses on maintaining the AVL tree property during each insertion operation, ensuring the tree remains balanced.

### Insertion Process
The `insert` function recursively finds the correct position for the new value in the tree, maintaining the binary search tree property.

1. **Base Condition**: 
   - If the current node (`root`) is `null`, a new node is created with the given value (`val`).

2. **Recursive Insertion**: 
   - Depending on whether the value is less than or greater than the current node's value, the function is recursively called for the left or right subtree, respectively.

3. **Updating Height**: 
   - After insertion, the height of the current node is updated. It's the maximum height of its left and right subtrees plus one.

4. **Balancing the Tree**: 
   - The balance factor is calculated as the height difference between left and right subtrees. 
   - If the balance factor is more than 1 or less than -1, rotations (right or left) are performed to balance the tree.

### Rotations
`rightRotation` and `leftRotation` functions are used to rebalance the tree. These rotations are the key operations in AVL trees to maintain balance.

1. **Right Rotation**:
   - Applied when the left subtree is heavier. It involves rotating the nodes to the right, which becomes the new root of the subtree.

2. **Left Rotation**:
   - Applied when the right subtree is heavier. It involves rotating the nodes to the left, which becomes the new root of the subtree.

### Height Calculation
The `height` function calculates the height of a node in the tree. It returns -1 for a null node and the height value for a non-null node.

## Usage
The `insert` function is used to add new elements to an AVL tree while maintaining its balanced property. It can be utilized in scenarios requiring balanced search operations.

## Conclusion
The code implements AVL tree insertion with self-balancing capabilities. It demonstrates the importance of rotations in maintaining the height balance of the tree, ensuring optimal search, insertion, and deletion times.
