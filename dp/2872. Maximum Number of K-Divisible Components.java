package dp;

import java.util.*;

/*
 * There is an undirected tree with n nodes labeled from 0 to n - 1. You are
 * given the integer n and a 2D integer array edges of length n - 1, where
 * edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi
 * in the tree.
 * 
 * You are also given a 0-indexed integer array values of length n, where
 * values[i] is the value associated with the ith node, and an integer k.
 * 
 * A valid split of the tree is obtained by removing any set of edges, possibly
 * empty, from the tree such that the resulting components all have values that
 * are divisible by k, where the value of a connected component is the sum of
 * the values of its nodes.
 * 
 * Return the maximum number of components in any valid split.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: n = 5, edges = [[0,2],[1,2],[1,3],[2,4]], values = [1,8,1,4,4], k = 6
 * Output: 2
 * Explanation: We remove the edge connecting node 1 with 2. The resulting split
 * is valid because:
 * - The value of the component containing nodes 1 and 3 is values[1] +
 * values[3] = 12.
 * - The value of the component containing nodes 0, 2, and 4 is values[0] +
 * values[2] + values[4] = 6.
 * It can be shown that no other valid split has more than 2 connected
 * components.
 * 
 * Constraints:
 * 
 * 1 <= n <= 3 * 10^4
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * values.length == n
 * 0 <= values[i] <= 10^9
 * 1 <= k <= 10^9
 * Sum of values is divisible by k.
 * The input is generated such that edges represents a valid tree.
 */

/*
 * TREE DP PATTERN - Key Insight from Notes
 * 
 * Problem: Split tree into maximum components where each component's sum is divisible by k
 * 
 * Key Observation (from your notes):
 * - We can BREAK an edge if the subtree below has sum divisible by k
 * - We DON'T BREAK an edge if the subtree sum is NOT divisible by k
 * 
 * Example from notes:
 *         0
 *        / \
 *       1   2
 *      / \
 *     3   4
 * 
 * If subtree rooted at node 1 has sum divisible by k:
 * → We can break edge (0,1) and create a separate component
 * → count++
 * → Return 0 to parent (because we "cut off" this subtree)
 * 
 * If subtree sum is NOT divisible by k:
 * → We DON'T break the edge
 * → Return (sum % k) to parent (parent needs to know the remainder)
 */

/*
 * FORMULA FROM NOTES:
 * 
 * For a node with children a, b, c:
 * sum = (a + b + c + current_value) % k
 * 
 * If sum % k == 0:
 *   - This subtree is divisible by k
 *   - We can break the edge to parent
 *   - count++ (new component created)
 *   - Return 0 to parent (we cut this off)
 * 
 * If sum % k != 0:
 *   - This subtree is NOT divisible by k
 *   - We DON'T break the edge
 *   - Return (sum % k) to parent
 */

/*
 * EXAMPLE FROM PROBLEM: 
 * Tree structure:
 *         2 (val=1)
 *        /|\
 *       / | \
 *      0  1  4
 *    (1) (8)(4)
 *         |
 *         3
 *        (4)
 * 
 * k = 6
 * 
 * DFS Post-order traversal (process children first, then parent):
 * 
 * 1. Visit node 0 (leaf):
 *    - No children
 *    - sum = 0 + values[0] = 0 + 1 = 1
 *    - sum % 6 = 1 (not divisible)
 *    - Return 1 to parent (node 2)
 * 
 * 2. Visit node 3 (leaf):
 *    - No children
 *    - sum = 0 + values[3] = 0 + 4 = 4
 *    - sum % 6 = 4 (not divisible)
 *    - Return 4 to parent (node 1)
 * 
 * 3. Visit node 1:
 *    - Child 3 returned 4
 *    - sum = 4 + values[1] = 4 + 8 = 12
 *    - sum % 6 = 0 ✅ (divisible!)
 *    - count++ (component: nodes 1,3 with sum 12)
 *    - Return 0 to parent (we break this edge)
 * 
 * 4. Visit node 4 (leaf):
 *    - No children
 *    - sum = 0 + values[4] = 0 + 4 = 4
 *    - sum % 6 = 4 (not divisible)
 *    - Return 4 to parent (node 2)
 * 
 * 5. Visit node 2 (root):
 *    - Child 0 returned 1
 *    - Child 1 returned 0 (was cut off)
 *    - Child 4 returned 4
 *    - sum = 1 + 0 + 4 + values[2] = 1 + 0 + 4 + 1 = 6
 *    - sum % 6 = 0 ✅ (divisible!)
 *    - count++ (component: nodes 0,2,4 with sum 6)
 * 
 * Total components: 2 ✅
 */

/*
 * WHY USE MODULO k?
 * 
 * Problem: Values can be up to 10^9, and we might have 30,000 nodes
 * - Direct sum could be: 10^9 * 30,000 = 3 * 10^13 (overflow!)
 * 
 * Solution: Use modular arithmetic
 * - (a + b) % k = ((a % k) + (b % k)) % k
 * - We only care if sum is divisible by k, not the actual sum
 * - Keep sum % k at each step to avoid overflow
 * 
 * Example from notes:
 * sum = (a + b + c + d + e) % k
 * 
 * Instead of computing full sum then modulo:
 * sum = a + b + c + d + e  // Could overflow!
 * sum % k
 * 
 * We do incremental modulo:
 * sum = 0
 * sum = (sum + a) % k
 * sum = (sum + b) % k
 * sum = (sum + c) % k
 * ...
 */

/*
 * DFS POST-ORDER TRAVERSAL PATTERN
 * 
 * Why post-order? We need to process children BEFORE parent
 * 
 * Pattern:
 * 1. Mark current node as visited
 * 2. Recursively visit all unvisited neighbors (children in tree)
 * 3. Collect results from children (sum of their remainders)
 * 4. Add current node's value
 * 5. Check if total sum % k == 0
 * 6. Return remainder to parent
 * 
 * This is classic Tree DP:
 * - Each node computes answer based on children's answers
 * - Bottom-up computation (leaves → root)
 */

/*
 * EDGE BREAKING DECISION (from notes):
 * 
 * At each node, we ask: "Should I break the edge to my parent?"
 * 
 * Decision criteria:
 * - If (subtree_sum % k == 0): YES, break the edge
 *   → This subtree forms a valid component
 *   → count++
 *   → Return 0 to parent (subtree is "cut off")
 * 
 * - If (subtree_sum % k != 0): NO, don't break
 *   → This subtree needs to merge with parent
 *   → Return (subtree_sum % k) to parent
 *   → Parent will include this remainder in its calculation
 * 
 * Visual from notes:
 *     Parent
 *       |
 *     Child (sum % k == 0)
 *      / \
 *     a   b
 * 
 * We "break" the edge between Parent and Child
 * Child becomes a separate component
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Tree DP with post-order DFS
 *    → Process children first, then parent
 * 
 * 2. Each node returns (subtree_sum % k) to parent
 *    → Parent uses this to compute its own sum
 * 
 * 3. Break edge when subtree_sum % k == 0
 *    → Creates a new component
 *    → count++
 *    → Return 0 to parent
 * 
 * 4. Use modulo to prevent overflow
 *    → Values can be very large (10^9)
 *    → sum %= k at each step
 * 
 * 5. Visited array prevents revisiting in undirected tree
 *    → Tree is undirected, so we need to track visited nodes
 *    → Prevents infinite loops
 */

class Solution {
  int count; // Global counter for number of valid components

  public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
    // Step 1: Build adjacency list representation of tree
    Map<Integer, List<Integer>> adjList = new HashMap<>();

    // Initialize empty adjacency lists for all nodes
    for (int i = 0; i < n; i++) {
      adjList.put(i, new ArrayList<>());
    }

    // Add edges (undirected, so add both directions)
    for (int[] edge : edges) {
      int u = edge[0];
      int v = edge[1];

      adjList.get(u).add(v);
      adjList.get(v).add(u);
    }

    // Step 2: Initialize visited array to track processed nodes
    boolean[] visited = new boolean[n]; // default → false

    // Step 3: Initialize component counter
    count = 0;

    // Step 4: Start DFS from node 0 (can start from any node in a tree)
    dfs(visited, 0, adjList, values, k);

    // Step 5: Return total number of components found
    return count;
  }

  /**
   * DFS function that computes subtree sum modulo k
   * 
   * Returns: (subtree_sum % k) to parent
   * Side effect: Increments count when subtree_sum % k == 0
   * 
   * Pattern from notes:
   * sum = (child1 + child2 + ... + current_value) % k
   */
  private int dfs(boolean[] visited, int curr, Map<Integer, List<Integer>> adjList, int[] values, int k) {
    // Initialize sum for current subtree
    int sum = 0;

    // Mark current node as visited (prevents revisiting in undirected tree)
    visited[curr] = true;

    // Step 1: Process all children (post-order traversal)
    // Collect remainders from all child subtrees
    for (int neighbour : adjList.get(curr)) {
      if (!visited[neighbour]) {
        // Recursive call returns (child_subtree_sum % k)
        // If child subtree was divisible by k, it returns 0 (was cut off)
        // Otherwise, it returns the remainder to include in parent's sum
        sum += dfs(visited, neighbour, adjList, values, k);
      }
    }

    // Step 2: Add current node's value to sum
    int val = values[curr];
    // Note: val can be very large (up to 10^9)
    // Example: [1000000000, 1000000000, ...] could overflow without modulo

    sum += val;

    // Step 3: Apply modulo to prevent overflow and check divisibility
    // This keeps sum manageable and gives us the remainder
    sum %= k;

    // Step 4: Check if current subtree sum is divisible by k
    if (sum == 0) {
      // Subtree sum is divisible by k!
      // Decision: BREAK the edge to parent
      // This subtree becomes a separate component
      count++;

      // Return 0 to parent (this subtree is "cut off")
      // Parent won't include this subtree's sum
    }

    // Step 5: Return remainder to parent
    // - If sum == 0: we return 0 (subtree was cut off)
    // - If sum != 0: we return the remainder (parent includes it)
    return sum;
  }
}