package Trees;

/*
 * Given a binary tree, determine if it is height-balanced.
 * 
 * A height-balanced binary tree is a binary tree in which the depth/height of the two
 * subtrees of every node never differs by more than one.
 */

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

class Solution {
  public boolean isBalanced(TreeNode root) {
    int height = getHeight(root);
    return height != -1;
  }

  private int getHeight(TreeNode root) {
    if (root == null) {
      return 0;
    }

    int left = getHeight(root.left);
    int right = getHeight(root.right);

    if (left == -1 || right == -1) {
      return -1;
    }

    if (Math.abs(left - right) > 1) {
      return -1;
    }

    return 1 + Math.max(left, right);
  }
}