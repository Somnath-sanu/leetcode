package Trees;

/*
543. Diameter of Binary Tree

Given the root of a binary tree, return the length of the diameter of the tree.

The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

The length of a path between two nodes is represented by the number of edges between them.
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

public class TreeNode {
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
    public int diameterOfBinaryTree(TreeNode root) {
        int[] max = new int[1]; // pass by reference

        dfs(root, max);

        return max[0];
    }

    private int dfs(TreeNode root, int[] max) {
        if (root == null) {
            return 0;
        }

        int left = dfs(root.left, max);
        int right = dfs(root.right, max);

        max[0] = Math.max(max[0], left + right);
        // left+right -> longest path of a particular node

        return 1 + Math.max(left, right); // height formula

        /**
         * Diameter calculation: left + right = 1 + 1 = 2 → this is the number of edges
         * in the path (root → left leaf → root → right leaf = 2 edges).
         * 
         * 
         * The return value 1 + max(left, right) calculates the height in nodes (not
         * edges). This is needed for parent nodes to compute their own height
         * correctly.
         * The diameter (left + right) uses the node-based heights because:
         * The path between two leaves through the root has (left_height_nodes) +
         * (right_height_nodes) = 2 edges.
         * Example: left_height_nodes = 1 (root → left leaf = 1 edge),
         * right_height_nodes = 1 (root → right leaf = 1 edge), so total edges = 1 + 1 =
         * 2.
         * 
         */
    }
}
