package dp;

import java.util.*;

/*
Time Complexity: O(n * W)
Space Complexity: O(n * W) */

/*
 * Given two arrays, val[] and wt[], where each element represents the value and
 * weight of an item respectively, and an integer W representing the maximum
 * capacity of the knapsack (the total weight it can hold).
 * 
 * The task is to put the items into the knapsack such that the total value
 * obtained is maximum without exceeding the capacity W.
 * 
 * Note: You can either include an item completely or exclude it entirely —
 * fractional selection of items is not allowed. Each item is available only
 * once.
 * 
 * Examples :
 * 
 * Input: W = 4, val[] = [1, 2, 3], wt[] = [4, 5, 1]
 * Output: 3
 * Explanation: Choose the last item, which weighs 1 unit and has a value of 3.
 */

/*
 * 0-1 KNAPSACK PROBLEM - Classic DP Problem
 * 
 * This is a variation of the subset sum problems we've solved, but with a twist:
 * - Instead of checking if sum exists or counting subsets
 * - We MAXIMIZE the total value while staying within weight capacity
 * 
 * Key Differences from Previous Problems:
 * 1. Two arrays: val[] (values) and wt[] (weights)
 * 2. Constraint: total weight <= W (capacity)
 * 3. Objective: MAXIMIZE total value (not count or check existence)
 * 4. Return: maximum value achievable (not boolean or count)
 */

/*
 * PROBLEM BREAKDOWN:
 * 
 * Given:
 * - val[] = [1, 2, 3]  (values of items)
 * - wt[]  = [4, 5, 1]  (weights of items)
 * - W = 4              (knapsack capacity)
 * 
 * For each item, we have two choices:
 * 1. Pick it: add its value, reduce remaining capacity by its weight
 * 2. Don't pick it: value unchanged, capacity unchanged
 * 
 * Goal: Find the combination that gives maximum value without exceeding capacity
 * 
 * Analysis:
 * - Item 0: wt=4, val=1 → fits in capacity 4, gives value 1
 * - Item 1: wt=5, val=2 → doesn't fit in capacity 4, skip
 * - Item 2: wt=1, val=3 → fits in capacity 4, gives value 3
 * 
 * Best choice: Pick item 2 only → total value = 3 ✅
 */

/*
 * COMPARISON WITH SUBSET SUM PROBLEMS:
 * 
 * Subset Sum (Check Existence):
 * - Constraint: sum == target
 * - Return: boolean (exists or not)
 * - Recurrence: pick || noPick
 * 
 * Perfect Sum (Count Subsets):
 * - Constraint: sum == target
 * - Return: count of valid subsets
 * - Recurrence: pick + noPick
 * 
 * 0-1 Knapsack (Maximize Value):
 * - Constraint: weight <= capacity
 * - Return: maximum value achievable
 * - Recurrence: max(pick, noPick)
 * 
 * Common Pattern:
 * - All use pick/noPick choices
 * - All check constraint before picking
 * - All use memoization with dp[index][constraint]
 */

/*
 * WHY dp[index][capacity] instead of dp[index][weight]?
 * 
 * In subset sum: dp[index][target] tracks "can we achieve sum = target?"
 * In knapsack: dp[index][capacity] tracks "max value with remaining capacity"
 * 
 * The capacity is the REMAINING weight we can still use, not the weight used.
 * 
 * Example: W = 10, we pick item with wt = 3
 * - Remaining capacity = 10 - 3 = 7
 * - Next recursive call: recur(index-1, capacity=7, dp)
 * - dp[index][7] stores: "max value achievable with capacity 7"
 */

/*
 * BASE CASES EXPLAINED:
 * 
 * Base Case 1: capacity == 0
 * - No space left in knapsack
 * - Can't pick any more items
 * - Return 0 (no value can be added)
 * 
 * Base Case 2: index == 0 (first item)
 * - If wt[0] <= capacity: we can pick it → return val[0]
 * - If wt[0] > capacity: can't pick it → return 0
 * 
 * Note: Unlike subset sum, we don't need special handling for zeros
 * because values and weights are separate arrays
 */

/*
 * EXAMPLE TRACE: val = [1, 2, 3], wt = [4, 5, 1], W = 4
 * 
 * recur(index=2, capacity=4):
 *   wt[2]=1 <= 4 ✅
 *   pick = val[2] + recur(1, 4-1) = 3 + recur(1, 3)
 *     recur(1, 3):
 *       wt[1]=5 > 3 ❌ can't pick
 *       noPick = recur(0, 3)
 *         recur(0, 3):
 *           wt[0]=4 > 3 ❌ can't pick
 *           return 0
 *       return 0
 *   pick = 3 + 0 = 3
 *   
 *   noPick = recur(1, 4)
 *     recur(1, 4):
 *       wt[1]=5 > 4 ❌ can't pick
 *       noPick = recur(0, 4)
 *         recur(0, 4):
 *           wt[0]=4 <= 4 ✅
 *           return val[0] = 1
 *       return 1
 *   noPick = 1
 *   
 *   return max(3, 1) = 3 ✅
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Check wt[index] <= capacity before picking
 *    → Ensures we don't exceed knapsack capacity
 *    → Prevents negative capacity (similar to negative target in subset sum)
 * 
 * 2. Maximize value: return max(pick, noPick)
 *    → Different from subset sum (|| or +)
 *    → We want the best choice, not all choices
 * 
 * 3. When picking: add value, subtract weight
 *    → pick = val[index] + recur(index-1, capacity - wt[index])
 *    → Value accumulates, capacity decreases
 * 
 * 4. DP dimensions: dp[n][W+1]
 *    → n items, W+1 possible capacities (0 to W)
 *    → Similar structure to subset sum problems
 */

class Solution {
  public int knapsack(int W, int val[], int wt[]) {
    int n = val.length;

    // DP array to memoize maximum values
    // dp[index][capacity] = max value achievable using items 0..index
    // with remaining capacity = capacity
    // -1 means not computed yet
    int[][] dp = new int[n][W + 1];

    for (int[] d : dp) {
      Arrays.fill(d, -1);
    }

    return recur(val, wt, n - 1, W, dp);
  }

  private int recur(int[] val, int[] wt, int index, int capacity, int[][] dp) {
    // Base case 1: No capacity left
    // Can't add any more items, value = 0
    // This can happen at any index when capacity is exhausted
    if (capacity == 0) {
      dp[index][capacity] = 0;
      return 0;
    }

    // Base case 2: Reached first item (index == 0)
    // Only one decision to make: can we fit item 0?
    if (index == 0) {
      // If item 0 fits in remaining capacity, take it
      if (wt[index] <= capacity) {
        dp[index][capacity] = val[index];
        return val[index];
      }
      // If item 0 doesn't fit, we get 0 value
      dp[index][capacity] = 0;
      return 0;
    }

    // Memoization check: return cached result if available
    if (dp[index][capacity] != -1) {
      return dp[index][capacity];
    }

    // Option 1: Pick current item (if it fits)
    int pick = 0;

    // CRITICAL: Only pick if item's weight <= remaining capacity
    // This ensures we don't exceed knapsack capacity
    // Similar to arr[index] <= target check in subset sum
    if (wt[index] <= capacity) {
      // Add current item's value + max value from remaining items
      // with reduced capacity (capacity - wt[index])
      pick = val[index] + recur(val, wt, index - 1, capacity - wt[index], dp);
    }

    // Option 2: Don't pick current item
    // Value unchanged, capacity unchanged
    // Move to next item with same capacity
    int noPick = 0 + recur(val, wt, index - 1, capacity, dp);

    // Return maximum of both choices
    // We want the choice that gives us the highest total value
    // This is different from:
    // - Subset sum existence: pick || noPick (any valid path)
    // - Perfect sum count: pick + noPick (count all paths)
    // - Knapsack: max(pick, noPick) (best path)
    return dp[index][capacity] = Math.max(pick, noPick);
  }
}
