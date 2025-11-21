package Trees;

/*
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes
 * in the tree.
 * 
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor
 * is defined between two nodes p and q as the lowest node in T that has both p
 * and q as descendants (where we allow a node to be a descendant of itself).”
 */

/**
 * 1. Apply post order traversal (DFS)
 * 2. if you find any one of the nodes p or q return that node
 * 3. if any one of the subtree returns non-null node , then return that node
 * 4. if both left and right are non-null , then current root is LCA , return
 * root
 * 5. if both are null , return null
 */

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;

  TreeNode(int x) {
    val = x;
  }
}

class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) {
      return root;
    }

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    // left can be null or p or q or LCA

    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left == null) {
      return right;
    }

    if (right == null) {
      return left;
    }

    return root; // both left and right are not null , so root is LCA

  }
}
