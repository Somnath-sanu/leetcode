package Trees;

/*
 * You are given the root of a binary tree, and your task is to return its top
 * view. The top view of a binary tree is the set of nodes visible when the tree
 * is viewed from the top.
 * 
 * Note:
 * 
 * Return the nodes from the leftmost node to the rightmost node.
 * If multiple nodes overlap at the same horizontal position, only the topmost
 * (closest to the root) node is included in the view.
 */

/**
 * TreeMap stores the keys in sorted order. (T.C -> O(log n) for put and get
 * operations)
 * 
 * We cannot solve it via DFS because we need to ensure that the topmost node at each
 * horizontal distance is selected. DFS may go deep into one side of the tree, and
 * miss nodes that are higher up on the other side but at the same horizontal distance.
 */

/*
* Same for Bottom View of Binary Tree problem but in that code
* update the map value at each horizontal distance instead of putIfAbsent.

*and also we can put right child first then left child in queue
* because we need the bottommost node at each horizontal distance.
*/

import java.util.*;

class Node {
  int data;
  Node left, right;

  Node(int val) {
    this.data = val;
    this.left = null;
    this.right = null;
  }
}

class CustomNode {
  Node node;
  int col;

  CustomNode(Node node, int col) {
    this.node = node;
    this.col = col;
  }
}

class Solution {
  public ArrayList<Integer> topView(Node root) {
    // code here
    if (root == null) {
      return new ArrayList<>();
    }
    ArrayList<Integer> ans = new ArrayList<>();
    TreeMap<Integer, Integer> map = new TreeMap<>();

    Queue<CustomNode> q = new LinkedList<>();

    q.offer(new CustomNode(root, 0));

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        CustomNode p = q.poll();
        Node node = p.node;
        int col = p.col;

        map.putIfAbsent(col, node.data);

        if (node.left != null) {
          q.offer(new CustomNode(node.left, col - 1));
        }

        if (node.right != null) {
          q.offer(new CustomNode(node.right, col + 1));
        }

      }
    }

    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
      ans.add(entry.getValue());
    }

    return ans;

  }
}