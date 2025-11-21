package Trees;

/*
 * Given a root of a Binary Tree, return its boundary traversal in the following
 * order:
 * 
 * Left Boundary: Nodes from the root to the leftmost non-leaf node, preferring
 * the left child over the right and excluding leaves.
 * 
 * Leaf Nodes: All leaf nodes from left to right, covering every leaf in the
 * tree.
 * 
 * Reverse Right Boundary: Nodes from the root to the rightmost non-leaf node,
 * preferring the right child over the left, excluding leaves, and added in
 * reverse order.
 * 
 * Note: The root is included once, leaves are added separately to avoid
 * repetition, and the right boundary follows traversal preference not the path
 * from the rightmost leaf.
 * 
 * Examples:
 * 
 * Input: root = [1, 2, 3, 4, 5, 6, 7, N, N, 8, 9, N, N, N, N]
 * Output: [1, 2, 4, 8, 9, 6, 7, 3]
 * Explanation:
 */

import java.util.*;

class Node {
  int data;
  Node left, right;

  public Node(int d) {
    data = d;
    left = right = null;
  }
}

class Solution {
  ArrayList<Integer> boundaryTraversal(Node root) {
    // code here
    ArrayList<Integer> res = new ArrayList<>();

    if (root == null) {
      return res;
    }

    if (!(root.left == null && root.right == null)) {
      //* The root should be added if it is NOT a leaf
      res.add(root.data);
    }

    insertLeftBoundary(res, root.left);
    insertLeaf(res, root);
    Stack<Integer> st = new Stack<>();
    insertRightBoundary(st, root.right); // dont want root again

    while (!st.isEmpty()) {
      res.add(st.pop());
    }

    return res;
  }

  void insertLeftBoundary(ArrayList<Integer> res, Node root) {
    while (root != null) {
      if (root.left == null && root.right == null) { // leaf node
        break;
      }

      res.add(root.data);

      if (root.left != null) { // prefer left child over right child
        root = root.left;
      } else {
        root = root.right;
      }
    }
  }

  void insertLeaf(ArrayList<Integer> res, Node root) {
    if (root == null) {
      return;
    }

    if (root.left == null && root.right == null) {
      res.add(root.data);
      return;
    }

    insertLeaf(res, root.left);
    insertLeaf(res, root.right);
  }

  void insertRightBoundary(Stack<Integer> st, Node root) {
    while (root != null) {
      if (root.left == null && root.right == null) {
        break;
      }

      st.add(root.data);

      if (root.right != null) {
        root = root.right;
      } else {
        root = root.left;
      }
    }
  }
}