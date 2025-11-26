package dp;

/*
 * You are a professional robber planning to rob houses along a street. Each
 * house has a certain amount of money stashed. All houses at this place are
 * arranged in a circle. That means the first house is the neighbor of the last
 * one. Meanwhile, adjacent houses have a security system connected, and it will
 * automatically contact the police if two adjacent houses were broken into on
 * the same night.
 * 
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the
 * police.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money =
 * 2), because they are adjacent houses.
 * Example 2:
 * 
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 3:
 * 
 * Input: nums = [1,2,3]
 * Output: 3
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 */

/*
 * CIRCULAR CONSTRAINT - Why can't we rob both first and last house?
 * 
 * In House Robber I, houses are in a line: [A] - [B] - [C] - [D]
 * In House Robber II, houses form a circle: [A] - [B] - [C] - [D] - [A]
 *                                             ^_______________________|
 * 
 * This means: First house (A) and Last house (D) are NEIGHBORS!
 * If we rob both A and D, the alarm will trigger.
 */

/*
 * SOLUTION APPROACH: Two Scenarios
 * 
 * Since we can't rob both first and last house, we have 3 possibilities:
 * 1. Rob first house (A) → Can't rob last house (D)
 * 2. Rob last house (D) → Can't rob first house (A)
 * 3. Rob neither first nor last house
 * 
 * Observation: Case 3 is automatically covered by Cases 1 and 2!
 * 
 * Strategy: Run House Robber I twice:
 * - Scenario 1: Consider houses [0 to n-2] (exclude last house)
 *   → This allows robbing first house if optimal
 * - Scenario 2: Consider houses [1 to n-1] (exclude first house)
 *   → This allows robbing last house if optimal
 * 
 * Return: max(Scenario 1, Scenario 2)
 */

/*
 * EXAMPLE: nums = [2, 3, 2]
 * 
 * Original array (circular):
 *     [2] - [3] - [2]
 *      ^___________|  (2 and 2 are neighbors!)
 * 
 * Scenario 1: num2 = [2, 3] (indices 0 to n-2)
 *   - Can rob first house (2) if optimal
 *   - Cannot rob last house (2)
 *   - Best: rob 3 → result = 3
 * 
 * Scenario 2: num1 = [3, 2] (indices 1 to n-1)
 *   - Cannot rob first house (2)
 *   - Can rob last house (2) if optimal
 *   - Best: rob 3 → result = 3
 * 
 * Final answer: max(3, 3) = 3
 */

/*
 * EXAMPLE: nums = [1, 2, 3, 1]
 * 
 * Original array (circular):
 *     [1] - [2] - [3] - [1]
 *      ^_________________|  (1 and 1 are neighbors!)
 * 
 * Scenario 1: num2 = [1, 2, 3] (indices 0 to n-2)
 *   - Can rob: 1 and 3 → result = 4
 *   - Or rob: just 3 → result = 3
 *   - Best: 4
 * 
 * Scenario 2: num1 = [2, 3, 1] (indices 1 to n-1)
 *   - Can rob: 2 and 1 → result = 3
 *   - Or rob: just 3 → result = 3
 *   - Best: 3
 * 
 * Final answer: max(4, 3) = 4
 */

/*
 * ARRAY SPLITTING LOGIC:
 * 
 * For nums = [A, B, C, D] (n=4):
 * 
 * num1 (exclude first, i != 0):
 *   i=0: skip (i == 0)
 *   i=1: add B → num1[0] = B
 *   i=2: add C → num1[1] = C
 *   i=3: add D → num1[2] = D
 *   Result: num1 = [B, C, D]
 * 
 * num2 (exclude last, i != n-1):
 *   i=0: add A → num2[0] = A
 *   i=1: add B → num2[1] = B
 *   i=2: add C → num2[2] = C
 *   i=3: skip (i == n-1)
 *   Result: num2 = [A, B, C]
 * 
 * This ensures:
 * - num1 can rob D but not A
 * - num2 can rob A but not D
 * - Together they cover all valid robbing patterns
 */

class Solution {
  /*
   * Helper method: House Robber I (Space Optimized)
   * 
   * This is the standard House Robber solution for a linear array.
   * Uses space optimization: O(1) space instead of O(n) DP array.
   * 
   * Same index shifting as House Robber I:
   * - state represents idx+1 (shifted index)
   * - state=1 represents nums[0], state=2 represents nums[1], etc.
   * - This avoids negative index issues when accessing state-2
   */
  public int rob2(int[] nums) {
    int len = nums.length;

    // Edge case: single house, just rob it
    if (len == 1)
      return nums[0];

    // prev1: max money from 2 houses ago (dp[state-2])
    // prev2: max money from 1 house ago (dp[state-1])
    // ans: max money at current state (dp[state])
    int prev1 = 0; // Represents dp[0] = 0 (before first house)
    int prev2 = nums[0]; // Represents dp[1] = nums[0] (first house)
    int ans = 0;

    // Iterate from state=2 to state=len
    // state=2 represents nums[1], state=3 represents nums[2], etc.
    for (int state = 2; state <= len; state++) {
      // Recurrence relation:
      // pick: rob current house nums[state-1] + max money from state-2
      // noPick: skip current house + max money from state-1
      int pick = nums[state - 1] + prev1;
      int noPick = 0 + prev2;
      ans = Math.max(pick, noPick);

      // Shift the window: move forward one house
      prev1 = prev2; // What was 1 house ago is now 2 houses ago
      prev2 = ans; // Current state becomes 1 house ago for next iteration
    }
    return ans;

  }

  public int rob(int[] nums) {
    int n = nums.length;

    // Edge case: Only one house, no circular constraint
    // We can simply rob it without worrying about neighbors
    if (n == 1)
      return nums[0];

    // Create two arrays for two scenarios
    int[] num1 = new int[n - 1]; // Scenario 1: exclude first house (indices 1 to n-1)
    int[] num2 = new int[n - 1]; // Scenario 2: exclude last house (indices 0 to n-2)
    int j = 0; // Index pointer for num1
    int k = 0; // Index pointer for num2

    // Split the original array into two scenarios
    for (int i = 0; i < n; i++) {
      // Scenario 1: Skip first house (i=0), include all others
      // This allows us to potentially rob the last house
      if (i != 0) {
        num1[j++] = nums[i];
      }

      // Scenario 2: Skip last house (i=n-1), include all others
      // This allows us to potentially rob the first house
      if (i != n - 1) {
        num2[k++] = nums[i];
      }
    }

    // Run House Robber I on both scenarios and return the maximum
    // rob2(num1): max money when we can rob last house but not first
    // rob2(num2): max money when we can rob first house but not last
    return Math.max(rob2(num1), rob2(num2));
  }
}
