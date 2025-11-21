package Trees;

/*
 * Given the root of a binary search tree, and an integer k, return the kth
 * smallest value (1-indexed) of all the values of the nodes in the tree.
 */

import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode() {
  }

  TreeNode(int val) {
    this.val = val;
  }

  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

/*
 * Inorder traversal of BST gives sorted order
 * 
 * This approach uses inorder traversal and stores elements in a list until k
 * elements are found.
 * 
 * Returning early when the kth smallest element is found optimizes the
 * traversal process.
 */

class Solution {
  public int kthSmallest(TreeNode root, int k) {
    List<Integer> list = new ArrayList<>();
    int val = inorder(root, list, k);

    return val;
  }

  public int inorder(TreeNode root, List<Integer> list, int k) {
    if (root == null)
      return -1;
    int left = inorder(root.left, list, k);
    if (left != -1) {
      return left;
    }

    list.add(root.val);

    if (list.size() == k) {
      return list.get(k - 1);
    }

    int right = inorder(root.right, list, k);

    if (right != -1) {
      return right;
    }

    return -1;
  }
}
