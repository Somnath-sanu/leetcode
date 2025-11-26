package dp;

import java.util.*;

/*
 * Given an array arr of non-negative integers and an integer target, the task
 * is to count all subsets of the array whose sum is equal to the given target.
 * 
 * Examples:
 * 
 * Input: arr[] = [5, 2, 3, 10, 6, 8], target = 10
 * Output: 3
 * Explanation: The subsets {5, 2, 3}, {2, 8}, and {10} sum up to the target 10.
 */

/*
 * PERFECT SUM PROBLEM - Counting ALL Subsets (Not Just Finding One)
 * 
 * This problem is similar to "Check if subsequence with sum K exists" but with
 * a CRITICAL difference: we need to COUNT all possible subsets, not just find one.
 * 
 * Key Differences from Previous Problem:
 * 1. Return count (integer) instead of boolean
 * 2. NO early termination - must explore ALL paths
 * 3. Combine results: pick + noPick (not pick || noPick)
 * 4. Special handling for zeros in the array
 */

/*
 * WHY NO EARLY TERMINATION?
 * 
 * Previous Problem (Subsequence Exists):
 *   if (pick) return true;  // Found one, stop searching
 * 
 * This Problem (Count All Subsets):
 *   int pick = recur(...);
 *   int noPick = recur(...);
 *   return pick + noPick;  // Must count ALL possibilities
 * 
 * Example: arr = [2, 3, 5], target = 5
 * 
 * Possible subsets:
 * 1. [5] → sum = 5 ✅
 * 2. [2, 3] → sum = 5 ✅
 * 
 * If we used early termination:
 *   - Find [5], return 1 immediately
 *   - Miss [2, 3], wrong answer!
 * 
 * Correct approach:
 *   - Explore picking 5: finds [5] → count = 1
 *   - Explore not picking 5: finds [2, 3] → count = 1
 *   - Total: 1 + 1 = 2 ✅
 */

/*
 * CRITICAL: Handling Zeros in the Array
 * 
 * Zeros create a unique challenge at the base case (index == 0).
 * 
 * Problem: When arr[0] = 0 and target = 0, we have TWO valid choices:
 * 1. Pick the zero: subset = {0}, sum = 0 ✅
 * 2. Don't pick the zero: subset = {}, sum = 0 ✅
 * 
 * Both are valid subsets! So we must return 2, not 1.
 */

/*
 * BASE CASE ANALYSIS (index == 0)
 * 
 * Case 1: arr[0] = 0 AND target = 0
 *   - Pick 0: {0} sums to 0 ✅
 *   - Don't pick: {} sums to 0 ✅
 *   - Return 2 (both choices valid)
 * 
 * Case 2: arr[0] = x (x > 0) AND target = x
 *   - Pick x: {x} sums to x ✅
 *   - Don't pick: {} sums to 0 ≠ x ❌
 *   - Return 1 (only pick is valid)
 * 
 * Case 3: arr[0] = anything AND target = 0 (but arr[0] ≠ 0)
 *   - Pick arr[0]: {arr[0]} sums to arr[0] ≠ 0 ❌
 *   - Don't pick: {} sums to 0 ✅
 *   - Return 1 (only don't pick is valid)
 * 
 * Case 4: arr[0] ≠ target AND target ≠ 0
 *   - No valid subset
 *   - Return 0
 */

/*
 * EXAMPLE: arr = [3, 0, 2, 4], target = 0
 * 
 * Valid subsets that sum to 0:
 * 1. {} (empty set)
 * 2. {0}
 * 
 * Recursion trace (simplified):
 * recur(arr, 3, 0):
 *   pick (4): recur(arr, 2, -4) → invalid, skip
 *   noPick: recur(arr, 2, 0)
 *     pick (2): recur(arr, 1, -2) → invalid, skip
 *     noPick: recur(arr, 1, 0)
 *       pick (0): recur(arr, 0, 0)
 *         Base case: arr[0]=3, target=0 → return 1 (don't pick 3)
 *       noPick: recur(arr, 0, 0)
 *         Base case: arr[0]=3, target=0 → return 1 (don't pick 3)
 *       Total: 1 + 1 = 2 ✅
 * 
 * Wait, this seems wrong! Let me reconsider...
 * 
 * Actually, when we reach index=0 with target=0:
 * - We can form {} by not picking arr[0]=3 → 1 way
 * 
 * When we reach index=1 (arr[1]=0) with target=0:
 * - Pick 0: leads to target=0 at index=0 → 1 way (subset {0})
 * - Don't pick 0: leads to target=0 at index=0 → 1 way (subset {})
 * - Total: 2 ways ✅
 */

/*
 * EXAMPLE: arr = [0, 0, 1], target = 1
 * 
 * Valid subsets:
 * 1. {1}
 * 2. {0, 1} (first zero)
 * 3. {0, 1} (second zero)
 * 4. {0, 0, 1} (both zeros)
 * 
 * Total: 4 subsets
 * 
 * This shows why zeros are special - each zero doubles the count!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Count ALL paths, not just find one
 *    → No early termination
 *    → return pick + noPick
 * 
 * 2. Base case at index=0 must handle zeros specially
 *    → if (arr[0] == 0 && target == 0) return 2
 *    → This accounts for both picking and not picking the zero
 * 
 * 3. Still need arr[index] <= target check before picking
 *    → Prevents negative target values
 *    → Ensures valid memoization
 */

class Solution {
  // Function to calculate the number of subsets with a given sum
  public int perfectSum(int[] nums, int target) {
    int n = nums.length;

    // DP array to memoize counts
    // dp[index][t] = number of subsets using arr[0..index] that sum to t
    // -1 means not computed yet
    int[][] dp = new int[n][target + 1];
    for (int[] d : dp) {
      Arrays.fill(d, -1);
    }

    return recur(nums, n - 1, target, dp);
  }

  private int recur(int[] nums, int index, int t, int[][] dp) {
    // Base case: reached first element (index == 0)
    // Need to carefully handle all scenarios, especially zeros
    if (index == 0) {
      // Case 1: CRITICAL - arr[0] is 0 AND target is 0
      // Two valid subsets: {0} and {}
      // Both sum to 0, so return 2
      if (nums[index] == t && t == 0) {
        dp[index][t] = 2;
        return 2;
      }

      // Case 2: arr[0] equals target (but arr[0] ≠ 0)
      // Only one valid subset: {arr[0]}
      // Example: arr[0] = 5, target = 5 → subset {5}
      if (nums[index] == t) {
        dp[index][t] = 1;
        return 1;
      }

      // Case 3: target is 0 but arr[0] ≠ 0
      // Only one valid subset: {} (empty set)
      // Example: arr[0] = 5, target = 0 → subset {}
      if (t == 0) {
        dp[index][t] = 1;
        return 1;
      }

      // Case 4: arr[0] ≠ target and target ≠ 0
      // No valid subset
      // Example: arr[0] = 3, target = 5 → no subset possible
      dp[index][t] = 0;
      return 0;
    }

    // Memoization check: return cached result if available
    if (dp[index][t] != -1) {
      return dp[index][t];
    }

    // Option 1: Pick current element (if valid)
    int pick = 0;

    // Only pick if nums[index] <= t to avoid negative target
    // This prevents ArrayIndexOutOfBoundsException on dp[index][negative]
    if (nums[index] <= t) {
      pick = recur(nums, index - 1, t - nums[index], dp);

      // NOTE: NO early termination here!
      // Unlike the "exists" problem, we don't return immediately
      // We need to count ALL possible subsets, not just find one
    }

    // Option 2: Don't pick current element
    // Always explore this path to count all possibilities
    int noPick = recur(nums, index - 1, t, dp);

    // Combine results: total count = subsets with pick + subsets without pick
    // This is different from the "exists" problem where we used: pick || noPick
    return dp[index][t] = pick + noPick;
  }
}

class TabularApproach {
  public int perfectSum(int[] nums, int target) {
    int n = nums.length;
    int[][] dp = new int[n][target + 1];

    for (int t = 0; t <= target; t++) {
      if (nums[0] == t && t == 0) {
        dp[0][t] = 2;
      } else if (nums[0] == t) {
        dp[0][t] = 1;
      } else if (t == 0) {
        dp[0][t] = 1;
      } else {
        dp[0][t] = 0;
      }
    }

    for (int i = 1; i < n; i++) {
      for (int t = 0; t <= target; t++) {
        int pick = 0;
        if (nums[i] <= t) {
          pick = dp[i - 1][t - nums[i]];
        }
        int noPick = dp[i - 1][t];
        dp[i][t] = pick + noPick;
      }
    }
    return dp[n - 1][target];

  }

}