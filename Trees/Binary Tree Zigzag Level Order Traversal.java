package Trees;

/*
 * Given the root of a binary tree, return the zigzag level order traversal of
 * its nodes' values. (i.e., from left to right, then right to left for the next
 * level and alternate between).
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[20,9],[15,7]]
 * Example 2:
 * 
 * Input: root = [1]
 * Output: [[1]]
 * Example 3:
 * 
 * Input: root = []
 * Output: []
 */

/*
 O(K) is shifting time in case of arraylist/array
 at odd level we perform shifting
 so the T.C -> O(N) + O(K) , not  O(N) * O(K)
 becz only at odd level
 O(N) is T.C for BFS
 * For Linked List , insertion at head is constant.
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

class Solution {
  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();

    if (root == null) {
      return res;
    }

    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);

    int dir = 1; // left -> right // -1 -> right to left

    while (!q.isEmpty()) {
      int size = q.size();
      // List<Integer> list = new ArrayList<>();

      LinkedList<Integer> ll = new LinkedList<>();

      for (int i = 0; i < size; i++) {

        TreeNode node = q.poll();

        if (dir == 1) {
          // list.add(node.val);
          ll.offer(node.val); // add to the tails
        } else {
          // list.add(0,node.val);
          ll.offerFirst(node.val); // add to the head
        }

        if (node.left != null) {
          q.offer(node.left);
        }

        if (node.right != null) {
          q.offer(node.right);
        }
      }

      dir *= -1;
      // res.add(list);
      res.add(ll);
    }

    return res;
  }

}