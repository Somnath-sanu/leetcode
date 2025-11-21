package Trees;

/*
 * Implement the BSTIterator class that represents an iterator over the in-order
 * traversal of a binary search tree (BST):
 * 
 * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
 * The root of the BST is given as part of the constructor. The pointer should
 * be initialized to a non-existent number smaller than any element in the BST.
 * boolean hasNext() Returns true if there exists a number in the traversal to
 * the right of the pointer, otherwise returns false.
 * int next() Moves the pointer to the right, then returns the number at the
 * pointer.
 * Notice that by initializing the pointer to a non-existent smallest number,
 * the first call to next() will return the smallest element in the BST.
 * 
 * You may assume that next() calls will always be valid. That is, there will be
 * at least a next number in the in-order traversal when next() is called.
 * 
 */

/**
 * Inorder traversal of BST gives sorted order
 * So we can store inorder traversal in an arraylist
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

// Time Complexity: O(1) for next() and hasNext() and O(N) for constructing the
// iterator
// Space Complexity: O(N) for storing inorder traversal

class BSTIterator {
  int index;
  List<Integer> inOrder;

  public void inorder(TreeNode root) {
    if (root == null) {
      return;
    }
    inorder(root.left);
    inOrder.add(root.val);
    inorder(root.right);
  }

  public BSTIterator(TreeNode root) {
    index = -1;
    /*
     * We have to initialize index with -1 becz before first call to next() ,
     * pointer should be initialized to a non-existent number smaller than any
     * element in the BST.
     * Else if we initialize index with 0 , hasNext() will return true only if
     * there are more than 1 elements in the BST
     * 
     * [1]
     * hasNext() should return false here , but if index = 0
     * hasNext() will return true , becz index < inOrder.size() - 1
     */
    inOrder = new ArrayList<>();
    inorder(root);
  }

  public int next() {
    index++;
    return inOrder.get(index);
  }

  public boolean hasNext() {
    return index < (inOrder.size() - 1);
  }
}

// Time Complexity: O(1) for next() and hasNext() and O(h) for constructing the iterator
// Space Complexity: O(h) for stack , h -> height of tree

//* Stack is the iterative way to do inorder traversal

class BSTIterator2 {
  int index;
  // List<Integer> inOrder; // T.C O(n)

  Stack<TreeNode> stack; // T.C O(h) // h -> height

  // public void inorder(TreeNode root) {
  // if (root == null) {
  // return;
  // }
  // inorder(root.left);
  // inOrder.add(root.val);
  // inorder(root.right);
  // }

  public BSTIterator2(TreeNode root) {
    // index = -1;
    // inOrder = new ArrayList<>();
    // inorder(root);
    stack = new Stack<>();
    // insert left boundary
    TreeNode node = root;
    while (node != null) {
      stack.push(node);
      node = node.left;
    }
  }

  public int next() {
    // index++;
    // return inOrder.get(index);
    TreeNode node = stack.pop();
    int val = node.val;

    node = node.right; // one right
    while (node != null) {
      stack.push(node);
      node = node.left;
    }

    return val;
  }

  public boolean hasNext() {
    // return index < inOrder.size() - 1;
    return !stack.isEmpty();
  }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */