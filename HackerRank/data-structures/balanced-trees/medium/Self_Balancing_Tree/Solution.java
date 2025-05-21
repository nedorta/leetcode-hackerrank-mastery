static Node insert(Node root, int val) {
  if (root == null) {
      root = new Node();
      root.val = val;
      root.ht = 0;
      return root;
  }

  if (val <= root.val) {
      root.left = insert(root.left, val);
  } else {
      root.right = insert(root.right, val);
  }
  
  root.ht = 1 + Math.max(height(root.left), height(root.right));
  
  int balance = height(root.left) - height(root.right);
  
  if (balance > 1) {
      if (height(root.left.left) >= height(root.left.right)) {
          root = rightRotation(root);
      } else {
          root.left = leftRotation(root.left);
          root = rightRotation(root);
      }
  } else if (balance < -1) {
      if (height(root.right.right) >= height(root.right.left)) {
          root = leftRotation(root);
      } else {
          root.right = rightRotation(root.right);
          root = leftRotation(root);
      }
  }
  
  return root;
}

private static Node rightRotation(Node root) {
  Node newRoot = root.left;
  root.left = newRoot.right;
  newRoot.right = root;
  root.ht = 1 + Math.max(height(root.left), height(root.right));
  newRoot.ht = 1 + Math.max(height(newRoot.left), height(newRoot.right));
  return newRoot;
}

private static Node leftRotation(Node root) {
  Node newRoot = root.right;
  root.right = newRoot.left;
  newRoot.left = root;
  root.ht = 1 + Math.max(height(root.left), height(root.right));
  newRoot.ht = 1 + Math.max(height(newRoot.left), height(newRoot.right));
  return newRoot;
}

private static int height(Node root) {
  return root == null ? -1 : root.ht;
}
