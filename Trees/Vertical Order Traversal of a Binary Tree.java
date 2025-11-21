package Trees;

/*
 * Given the root of a binary tree, calculate the vertical order traversal of
 * the binary tree.
 * 
 * For each node at position (row, col), its left and right children will be at
 * positions (row + 1, col - 1) and (row + 1, col + 1) respectively. The root of
 * the tree is at (0, 0).
 * 
 * The vertical order traversal of a binary tree is a list of top-to-bottom
 * orderings for each column index starting from the leftmost column and ending
 * on the rightmost column. There may be multiple nodes in the same row and same
 * column. In such a case, sort these nodes by their values.
 * 
 * Return the vertical order traversal of the binary tree.
*/

/**
 * Used addAll() to add elements of one list to another list.
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

class CustomNode {
  TreeNode node;
  int col;

  CustomNode(TreeNode node, int col) {
    this.node = node;
    this.col = col;
  }
}

class Solution {
  public List<List<Integer>> verticalTraversal(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();

    if (root == null) {
      return res;
    }

    TreeMap<Integer, List<Integer>> map = new TreeMap<>();

    Queue<CustomNode> q = new LinkedList<>();

    q.offer(new CustomNode(root, 0));

    while (!q.isEmpty()) {
      int size = q.size();
      HashMap<Integer, List<Integer>> temp = new HashMap<>();

      for (int i = 0; i < size; i++) { // level wise
        CustomNode p = q.poll();
        TreeNode node = p.node;
        int col = p.col;

        List<Integer> list = temp.getOrDefault(col, new ArrayList<>());
        list.add(node.val);
        Collections.sort(list);
        temp.put(col, list);

        if (node.left != null) {
          q.offer(new CustomNode(node.left, col - 1));
        }
        if (node.right != null) {
          q.offer(new CustomNode(node.right, col + 1));
        }
      }
      for (Map.Entry<Integer, List<Integer>> entry : temp.entrySet()) {
        int key = entry.getKey();
        List<Integer> temp_list = entry.getValue();

        List<Integer> list = map.getOrDefault(key, new ArrayList<>());

        list.addAll(temp_list);

        map.put(key, list);

      }

    }

    for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
      List<Integer> list = entry.getValue();
      res.add(list);
    }

    return res;
  }
}