package dp;

import java.util.*;

/*
 * You are a professional robber planning to rob houses along a street. Each
 * house has a certain amount of money stashed, the only constraint stopping you
 * from robbing each of them is that adjacent houses have security systems
 * connected and it will automatically contact the police if two adjacent houses
 * were broken into on the same night.
 * 
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the
 * police.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 2:
 * 
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5
 * (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 */

/*
 * Important test case to look otherwise its seems like
 * odd even index problem
 * 
 * [2,1,1,2]
 * 
 */

/*
 * HANDLING NEGATIVE INDICES - WHY dp[n+1] instead of dp[n]?
 * 
 * Problem: In the recursive solution, when we're at index 0, we need to check idx-2,
 * which would be -1. This causes ArrayIndexOutOfBoundsException with dp[n].
 * 
 * Solution: Use dp[n+1] and shift all indices by +1
 * 
 * INDEX MAPPING:
 * ┌─────────────────┬──────────────────┬─────────────────┐
 * │ Actual Array    │ Recursion Index  │ DP Array Index  │
 * │ (nums)          │ (logical)        │ (shifted by +1) │
 * ├─────────────────┼──────────────────┼─────────────────┤
 * │ N/A             │ -1 (invalid)     │ 0 (base case)   │
 * │ nums[0]         │ 0                │ 1               │
 * │ nums[1]         │ 1                │ 2               │
 * │ nums[2]         │ 2                │ 3               │
 * │ ...             │ ...              │ ...             │
 * │ nums[n-1]       │ n-1              │ n               │
 * └─────────────────┴──────────────────┴─────────────────┘
 * 
 * EXAMPLE: nums = [2, 7, 9, 3, 1] (n=5)
 * 
 * When we call recur(nums, 5, dp):
 *   - idx = 5 represents nums[4] (last house with value 1)
 *   - pick = nums[5-1] + recur(nums, 5-2, dp) = nums[4] + recur(nums, 3, dp)
 *   - noPick = recur(nums, 5-1, dp) = recur(nums, 4, dp)
 * 
 * When we call recur(nums, 2, dp):
 *   - idx = 2 represents nums[1] (second house with value 7)
 *   - pick = nums[2-1] + recur(nums, 2-2, dp) = nums[1] + recur(nums, 0, dp)
 *   - recur(nums, 0, dp) hits base case and returns 0 ✅
 *   - This represents: "rob nums[1], skip nums[0], nothing before that"
 * 
 * When we call recur(nums, 1, dp):
 *   - idx = 1 represents nums[0] (first house with value 2)
 *   - We handle this with base case: if (idx == 1) return nums[0]
 *   - If we tried: pick = nums[0] + recur(nums, -1, dp) ❌ This would fail!
 *   - But base case catches it before we make that call
 * 
 * KEY INSIGHT:
 * - dp[0] represents the state when idx = -1 (before any house) → return 0
 * - dp[1] represents the state when idx = 0 (first house) → return nums[0]
 * - dp[i] represents max money robbing from houses 0 to i-1
 * 
 * Without this shift (using dp[n]):
 * - When at nums[1], calling idx-2 = -1 would crash
 * - We'd need messy if-checks everywhere: if (idx-2 >= 0) dp[idx-2] else 0
 * 
 * With this shift (using dp[n+1]):
 * - Clean base cases handle edge conditions
 * - No negative array access
 * - Code is more readable and maintainable
 */

class Solution {
  public int rob(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n + 1]; // Size n+1 to handle negative index scenario
    Arrays.fill(dp, -1);

    // return recur(nums,n-1,dp); // ❌ Old approach: would need dp[n]
    return recur(nums, n, dp); // ✅ New approach: start with n (represents nums[n-1])

  }

  private int recur(int[] nums, int idx, int[] dp) {
    // Base case 1: idx = 0 represents logical index -1 (before first house)
    if (idx == 0) { // Equivalent to: if (actual_idx == -1)
      dp[idx] = 0; // No houses to rob, return 0
      return 0;
    }

    // Base case 2: idx = 1 represents logical index 0 (first house)
    if (idx == 1) { // Equivalent to: if (actual_idx == 0)
      dp[idx] = nums[idx - 1]; // Rob first house: nums[0]
      return dp[idx];
    }

    // Memoization check
    if (dp[idx] != -1) {
      return dp[idx];
    }

    // Recurrence relation:
    // pick: rob current house (nums[idx-1]) + max money from houses up to idx-2
    // noPick: skip current house + max money from houses up to idx-1
    int pick = nums[idx - 1] + recur(nums, idx - 2, dp);
    int noPick = 0 + recur(nums, idx - 1, dp);

    return dp[idx] = Math.max(pick, noPick);
  }

}

// ------------tabular

// public int rob(int[] nums) {
// int len = nums.length;
// int[] dp = new int[len+1];
// Arrays.fill(dp,-1);
// dp[0] = 0;
// dp[1] = nums[0];
// for(int state = 2; state <= len; state++){
// int pick = nums[state-1] + dp[state-2];
// int noPick = 0 + dp[state-1];
// dp[state] = Math.max(pick,noPick);
// }
// return dp[len];

// }

// -----------space optimization

// public int rob(int[] nums) {
// int len = nums.length;
// if(len == 1) return nums[0];
// int prev1 = 0;
// int prev2 = nums[0];
// int ans = 0;
// for(int state = 2; state <= len; state++){
// int pick = nums[state-1] + prev1;
// int noPick = 0 + prev2;
// ans = Math.max(pick,noPick);
// prev1 = prev2;
// prev2 = ans;
// }
// return ans;

// }