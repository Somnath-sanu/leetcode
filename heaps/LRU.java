package heaps;

import java.util.*;

/*
 * An LRU (Least Recently Used) cache is a memory management technique that
 * replaces the least recently used data item when the cache is full to make
 * space for new data.
 */

/**
 * 1. Use double linked list to maintain the order
 * 2. Use HashMap to store key and node
 */

/**
 * In Java, class fields are automatically initialized to default values if you
 * don't initialize them in the constructor.
 * Reference types (like Node) → null
 * int → 0
 * boolean → false
 * etc.
 * 
 * So when you create a Node with new Node(key, val), the constructor sets key and val, but prev and next
 * are automatically initialized to null by Java.
 */

// double linked list
class Node {
  int key;
  int val;
  Node prev;
  Node next;

  Node(int key, int val) {
    this.key = key;
    this.val = val;
  }
}

class LRUCache {

  // in java hashmap doesn't store key by its insertion order like JS
  // The order can vary, because HashMap organizes entries by hash buckets, not by
  // insertion time.

  Node head = new Node(-1, -1);
  Node tail = new Node(-1, -1);

  int capacity;
  HashMap<Integer, Node> m;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    m = new HashMap<>();

    head.next = tail;
    tail.prev = head;

    head.prev = null;
    tail.next = null;
  }

  // custom methods
  // 1. addNode
  // 2. deleteNode

  private void addNode(Node newNode) {
    // tail is fixed
    // newNode.next should attach head.next not tail directly
    // becz tail is fixed means it will always be at the end

    Node temp = head.next;

    newNode.next = temp;
    newNode.prev = head;

    head.next = newNode;
    temp.prev = newNode;
  }

  private void deleteNode(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;

    node.prev = null;
    node.next = null;

    // now java garbage collector will remove this node
  }

  public int get(int key) {
    if (!m.containsKey(key)) {
      return -1;
    }

    Node node = m.get(key);
    int ans = node.val;

    // m.remove(key);

    /**
     * No need to remove node from map or put it again
     * because we are using double linked list
     * to maintain the order
     */

    deleteNode(node);
    addNode(node); // this node will be added now most recent i.e next to head

    // m.put(key, head.next);

    return ans;
  }

  public void put(int key, int value) {
    if (m.containsKey(key)) {
      Node node = m.get(key);
      /*
       * Here removing node from map is important
       * as we are updating the value of node
       */

      m.remove(key);
      deleteNode(node);
    }

    if (this.capacity == m.size()) {
      // remove the least used node
      Node leastUsedNode = tail.prev;
      m.remove(leastUsedNode.key);
      deleteNode(leastUsedNode);
    }

    Node newNode = new Node(key, value);
    addNode(newNode);
    m.put(key, newNode);
  }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */