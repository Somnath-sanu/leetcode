package Trees;

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

class Solution {
  public List<Integer> rightSideView(TreeNode root) {

    // T.C -> O(n)
    // S.C -> max number of nodes at any level

    if (root == null) {
      return Arrays.asList(); // []
    }

    List<Integer> ans = new ArrayList<>();
    Queue<TreeNode> q = new LinkedList<>();

    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 1; i <= size; i++) {
        TreeNode node = q.poll();

        if (node.left != null) {
          q.offer(node.left);
        }

        if (node.right != null) {
          q.offer(node.right);
        }

        if (i == size) { // right most node only
          ans.add(node.val);
        }
      }
    }

    return ans;
  }
}