package Trees;


import java.util.LinkedList;
import java.util.Queue;

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
  public boolean isCousins(TreeNode root, int x, int y) {
    Queue<TreeNode> q = new LinkedList<>();

    q.offer(root);

    while (!q.isEmpty()) {
      int size = q.size();

      int foundSameParent = 0;
      int childFound = 0;

      for (int i = 0; i < size; i++) {
        TreeNode r = q.poll();
        if (r.left != null) {
          q.offer(r.left);
          if (r.left.val == x || r.left.val == y) {
            foundSameParent++;
            childFound++;
          }
        }

        if (r.right != null) {
          q.offer(r.right);
          if (r.right.val == x || r.right.val == y) {
            foundSameParent++;
            childFound++;
          }
        }

        if (foundSameParent == 2) {
          return false;
        } else {
          foundSameParent = 0;
        }

      }

      if (childFound == 1) {
        return false;
      }

      if (childFound == 2) {
        return true;
      }

    }

    return false;

  }
}