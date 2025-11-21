package Trees;


class Node {
  int data;
  Node left, right;

  Node(int item) {
    data = item;
    left = right = null;
  }
}

class Solution {
  // Function to return sum of all nodes of a binary tree
  static int sumBT(Node root) {
    // Your code here
    return dfs(root); 
  }

  private static int dfs(Node root) {
    if (root == null) {
      return 0;
    }

    int left_node_sum = dfs(root.left);
    int right_node_sum = dfs(root.right);

    return root.data + left_node_sum + right_node_sum;
  }
}