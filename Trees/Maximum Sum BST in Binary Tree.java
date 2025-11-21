package Trees;

/*
 * Given a binary tree root, return the maximum sum of all keys of any sub-tree
 * which is also a Binary Search Tree (BST).
 * 
 * Assume a BST is defined as follows:
 * 
 * The left subtree of a node contains only nodes with keys less than the node's
 * key.
 * The right subtree of a node contains only nodes with keys greater than the
 * node's key.
 * Both the left and right subtrees must also be binary search trees.
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {
  int ans;

  public int maxSumBST(TreeNode root) {
    ans = 0;
    dfs(root);
    return ans;
  }

  // arr of size 3 -> [min,max,sum]
  // min will check from right subtree
  // max will check from left subtree
  public int[] dfs(TreeNode root) {
    // base case
    if (root == null) {
      return new int[] { Integer.MAX_VALUE, Integer.MIN_VALUE, 0 };
    }
    // POST order traversal
    int[] leftSubtree = dfs(root.left);
    int[] rightSubtree = dfs(root.right);

    // check if current subtree is BST
    if (root.val > leftSubtree[1] && root.val < rightSubtree[0]) {
      int curSum = leftSubtree[2] + rightSubtree[2] + root.val;
      ans = Math.max(ans, curSum);

      int minVal = Math.min(root.val, leftSubtree[0]);
      int maxVal = Math.max(root.val, rightSubtree[1]);
      return new int[] { minVal, maxVal, curSum };
    }

    return new int[] {
        Integer.MIN_VALUE,
        Integer.MAX_VALUE,
        Math.max(leftSubtree[2], rightSubtree[2])
    };
  }
}
