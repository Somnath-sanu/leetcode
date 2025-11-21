package Trees;

/*
 * You are given the root of a complete binary tree. Your task is to find the
 * count of nodes.
 * 
 * A complete binary tree is a binary tree whose, all levels except the last one
 * are completely filled, the last level may or may not be completely filled and
 * Nodes in the last level are as left as possible.
 * 
 * Design an algorithm that runs better than O(n).
 * 
 * Example:
 * 
 * Input:
 * root = [1,2,3,4,5,6]
 * Output:
 * 6
 * Explanation:
 * There are a total of 6 nodes in the given tree.
 */

// O(n) solution

class Node {
  int data;
  Node left, right;

  Node(int item) {
    data = item;
    left = right = null;
  }
}

class Solution {

  public static int countNodes(Node root) {
    // Code here
    // return dfs(root);

    if (root == null) {
      return 0;
    }

    int left_height = getLeftHeight(root);
    int right_height = getRightHeight(root);

    if (left_height == right_height) {
      return (int)Math.pow(2,left_height) - 1;
    }

    return 1 + countNodes(root.left) + countNodes(root.right);
  }

  private static int getLeftHeight(Node root) {
    int height = 0;

    while (root != null) {
      height++;
      root = root.left;
    }

    return height;
  }

  private static int getRightHeight(Node root) {
    int height = 0;

    while (root != null) {
      height++;
      root = root.right;
    }

    return height;
  }

  private static int dfs(Node root) {
    if (root == null) {
      return 0;
    }

    int left_node = dfs(root.left);
    int right_node = dfs(root.right);

    return 1 + left_node + right_node;
  }
}

// o(log n * log n) solution

// Time complexity for finding height is O(log n) and we are calling it for log n times ( not n times as we are finding height for each level not for each node) so overall O(log n * log n)

