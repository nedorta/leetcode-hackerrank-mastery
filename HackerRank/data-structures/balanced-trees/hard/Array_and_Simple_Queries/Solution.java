import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

  public static class Treap {

    public static class Node {
      public int value;
      public int priority;
      public int count = 1;

      Node left;
      Node right;

      public Node(int value, int priority) {
        this.value = value;
        this.priority = priority;
      }

      public Node(int value, int priority, Node left, Node right) {
        this.value = value;
        this.priority = priority;
        this.left = left;
        this.right = right;
      }

      public void fixCount() {
        count = (left == null ? 0 : left.count) +
            (right == null ? 0 : right.count) + 1;
      }

      public List<Integer> toList() {
        List<Integer> list = new ArrayList<>();
        printNode(this, list);
        return list;
      }

      private void printNode(Node node, List<Integer> list) {
        if (node == null)
          return;
        printNode(node.left, list);
        list.add(node.value);
        printNode(node.right, list);
      }
    }

    public static Node merge(Node left, Node right) {
      if (left == null)
        return right;
      if (right == null)
        return left;

      if (left.priority > right.priority) {
        left.right = merge(left.right, right);
        left.fixCount();
        return left;
      } else {
        right.left = merge(left, right.left);
        right.fixCount();
        return right;
      }
    }

    public static Node[] splitByOrder(Node node, int order) {
      if (order == 0) {
        return new Node[] { null, node };
      } else if (node.count <= order) {
        return new Node[] { node, null };
      } else if (node.left != null && node.left.count >= order) {
        Node[] subResult = splitByOrder(node.left, order);
        node.left = subResult[1];
        node.fixCount();
        return new Node[] { subResult[0], node };
      } else {
        Node[] subResult = splitByOrder(node.right, order - (node.left != null ? node.left.count : 0) - 1);
        node.right = subResult[0];
        node.fixCount();
        return new Node[] { node, subResult[1] };
      }
    }

  }

  static Random random = new Random();

  public static void main(String[] args) {
    FastReader fastReader = new FastReader();
    int n = fastReader.nextInt();
    int m = fastReader.nextInt();

    Treap.Node root = new Treap.Node(fastReader.nextInt(), random.nextInt());
    for (int i = 1; i < n; i++) {
      root = Treap.merge(root, new Treap.Node(fastReader.nextInt(), random.nextInt()));
    }

    for (int i = 0; i < m; i++) {
      int queryType = fastReader.nextInt();
      int from = fastReader.nextInt();
      int to = fastReader.nextInt();

      Treap.Node[] subResult1 = Treap.splitByOrder(root, from - 1);
      Treap.Node firstPart = subResult1[0];
      Treap.Node temp = subResult1[1];
      Treap.Node[] subResult2 = Treap.splitByOrder(temp, to - (firstPart != null ? firstPart.count : 0));
      Treap.Node secondPart = subResult2[0];
      Treap.Node thirdPart = subResult2[1];
      Treap.Node combinedParts = Treap.merge(firstPart, thirdPart);

      if (queryType == 1) {
        root = Treap.merge(secondPart, combinedParts);
      } else {
        root = Treap.merge(combinedParts, secondPart);
      }
    }
    List<Integer> resultList = root.toList();
    System.out.println(Math.abs(resultList.get(0) - resultList.get(resultList.size() - 1)));
    System.out.println(resultList.stream().map(i -> Integer.toString(i)).collect(Collectors.joining(" ")));
  }

  public static class FastReader {
    BufferedReader br;
    StringTokenizer st;

    public FastReader() {
      br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
      while (st == null || !st.hasMoreElements()) {
        try {
          st = new StringTokenizer(br.readLine());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      return st.nextToken();
    }

    int nextInt() {
      return Integer.parseInt(next());
    }

    long nextLong() {
      return Long.parseLong(next());
    }

    double nextDouble() {
      return Double.parseDouble(next());
    }

    String nextLine() {
      String str = "";
      try {
        str = br.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return str;
    }
  }
}
