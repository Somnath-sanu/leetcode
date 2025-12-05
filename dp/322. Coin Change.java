package dp;

import java.util.*;

/*
 * You are given an integer array coins representing coins of different
 * denominations and an integer amount representing a total amount of money.
 * 
 * Return the fewest number of coins that you need to make up that amount. If
 * that amount of money cannot be made up by any combination of the coins,
 * return -1.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 * Example 2:
 * 
 * Input: coins = [2], amount = 3
 * Output: -1
 * Example 3:
 * 
 * Input: coins = [1], amount = 0
 * Output: 0
 * 
 * 
 * Constraints:
 * 
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 2^31 - 1
 * 0 <= amount <= 10^4
 */

/*
 * COIN CHANGE PATTERN - Unbounded Knapsack Variant
 * 
 * This is a classic DP problem with unlimited supply (unbounded knapsack).
 * 
 * Key characteristics:
 * 1. We can use each coin UNLIMITED times (unbounded)
 * 2. We want MINIMUM number of coins (not maximum, not count)
 * 3. We need to make EXACTLY the target amount
 * 
 * Similar to 0-1 Knapsack, but with one critical difference:
 * - 0-1 Knapsack: can use each item ONCE
 * - Unbounded Knapsack: can use each item UNLIMITED times
 */

/*
 * WHY BASE CASE CHECKS amount % coins[0] == 0
 * 
 * When index = 0, we ONLY have coins[0] available (no other coins).
 * We need to make EXACTLY the amount using ONLY this one coin.
 * 
 * Example 1: amount = 8, coins[0] = 2
 * - Can we make 8 using only coin 2?
 * - 2 + 2 + 2 + 2 = 8 ✅
 * - 8 % 2 == 0 → YES!
 * - Number of coins = 8 / 2 = 4
 * 
 * Example 2: amount = 7, coins[0] = 2
 * - Can we make 7 using only coin 2?
 * - 2 + 2 + 2 = 6 (but we need 7, not 6!)
 * - We're left with 1, but we have NO other coins
 * - 7 % 2 == 1 (not 0) → NO!
 * - Return 1e9 (infinity = impossible)
 * 
 * YES, we CAN take coin 2 three times to get 6.
 * But that doesn't help us make EXACTLY 7!
 * We'd still be short by 1, and we have no other coins.
 * 
 * The base case asks: "Using ONLY coins[0], can we make EXACTLY amount?"
 * - If amount is divisible by coins[0]: YES
 * - Otherwise: NO (impossible with only this coin)
 */

/*
 * DETAILED EXAMPLE: coins = [2], amount = 7
 * 
 * We ONLY have coin 2 available.
 * 
 * Options:
 * - Take 0 coins: total = 0 (need 7) ❌
 * - Take 1 coin: total = 2 (need 7) ❌
 * - Take 2 coins: total = 4 (need 7) ❌
 * - Take 3 coins: total = 6 (need 7) ❌
 * - Take 4 coins: total = 8 (too much!) ❌
 * 
 * There's NO way to make EXACTLY 7 using only coin 2.
 * We can get close (6 or 8), but not exactly 7.
 * 
 * Hence: 7 % 2 != 0, return 1e9 (impossible)
 */

/*
 * BASE CASE BREAKDOWN
 * 
 * if (index == 0) {
 *   // We're at the first coin, ONLY coins[0] is available
 *   
 *   if (amount % coins[0] == 0) {
 *     // Amount is perfectly divisible by coins[0]
 *     // We can make EXACTLY amount using multiple coins[0]
 *     int val = amount / coins[0];  // Number of coins needed
 *     return val;
 *   } else {
 *     // Amount is NOT divisible by coins[0]
 *     // IMPOSSIBLE to make this amount with only coins[0]
 *     return 1e9;  // Infinity (impossible)
 *   }
 * }
 * 
 * Key point: We need EXACTLY amount, not approximately amount!
 */

/*
 * PICK VS NO-PICK DECISION
 * 
 * For each coin at index, we have two choices:
 * 
 * 1. PICK this coin:
 *    - We use one coin, so count increases: 1 + ...
 *    - Amount reduces: amount - coins[index]
 *    - CRITICAL: index stays SAME (unbounded!)
 *    - We can pick this coin again in future
 *    - pick = 1 + recur(amount - coins[index], index)
 * 
 * 2. NO-PICK this coin:
 *    - We don't use this coin at all
 *    - Count stays same: 0 + ...
 *    - Amount stays same
 *    - Move to previous coin: index - 1
 *    - noPick = 0 + recur(amount, index - 1)
 * 
 * Return: min(pick, noPick) → minimize coin count
 */

/*
 * UNBOUNDED vs 0-1 KNAPSACK
 * 
 * The ONLY difference:
 * 
 * 0-1 Knapsack (use each item once):
 * pick = ... + recur(index - 1, remaining)
 *                     ↑ move to previous item
 * 
 * Unbounded Knapsack (use each item unlimited times):
 * pick = ... + recur(index, remaining)
 *                     ↑ STAY at same item
 * 
 * This allows us to pick the same coin multiple times!
 */

/*
 * WHY USE 1e9 (not Integer.MAX_VALUE)?
 * 
 * We use 1e9 as "infinity" instead of Integer.MAX_VALUE because:
 * 
 * When we do: pick = 1 + recur(...)
 * - If recur returns Integer.MAX_VALUE
 * - 1 + Integer.MAX_VALUE = Integer.MIN_VALUE (overflow!)
 * - This breaks our min() comparison
 * 
 * With 1e9:
 * - 1 + 1e9 = 1000000001 (no overflow)
 * - Still large enough to represent "impossible"
 * - Amount max is 10^4, so 1e9 is safely "infinity"
 */

/*
 * EXAMPLE: coins = [1, 2, 5], amount = 11
 * 
 * Optimal solution: 5 + 5 + 1 = 11 (3 coins)
 * 
 * How recursion finds it:
 * 
 * recur(amount=11, index=2) [coin 5]:
 * - pick: 1 + recur(11-5=6, index=2)
 *   - recur(6, 2): 1 + recur(6-5=1, 2)
 *     - recur(1, 2): 1e9 (5 > 1, can't pick)
 *     - noPick: recur(1, 1) [coin 2]
 *       - recur(1, 1): 1e9 (2 > 1, can't pick)
 *       - noPick: recur(1, 0) [coin 1]
 *         - 1 % 1 == 0 → return 1 ✅
 *       - Returns 1
 *     - min(1e9, 1) = 1
 *   - Returns 1 + 1 = 2 (for amount 6)
 * - Returns 1 + 2 = 3 (for amount 11) ✅
 * 
 * Final answer: 3 coins
 */

/*
 * SPACE OPTIMIZATION TRICK
 * 
 * Original DP: dp[n][amount+1] → O(n * amount) space
 * 
 * Optimized: prev[amount+1], curr[amount+1] → O(2 * amount) = O(amount)
 * 
 * Why it works:
 * - We only need previous row (index-1) to compute current row (index)
 * - Keep two 1D arrays instead of 2D array
 * - After processing each coin, curr becomes prev
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Unbounded Knapsack pattern
 *    → Each coin can be used unlimited times
 *    → pick: stay at same index
 * 
 * 2. Base case at index 0
 *    → Check if amount % coins[0] == 0
 *    → We need EXACTLY amount, not approximately
 * 
 * 3. Minimize coin count
 *    → min(pick, noPick)
 *    → Not max, not sum
 * 
 * 4. Use 1e9 not MAX_VALUE
 *    → Prevents overflow when doing 1 + result
 * 
 * 5. Return -1 if impossible
 *    → If result == 1e9, return -1
 */

class Solution {
  public int coinChange(int[] coins, int amount) {

    // APPROACH 1: Space Optimized (O(amount) space)
    int n = coins.length;
    int[] prev = new int[amount + 1];

    // Base case: Fill first row (only coins[0] available)
    for (int a = 0; a <= amount; a++) {
      if (a % coins[0] == 0) {
        // Can make amount 'a' using only coins[0]
        // Example: a=8, coins[0]=2 → need 4 coins
        int val = a / coins[0];
        prev[a] = val;
      } else {
        // Cannot make amount 'a' using only coins[0]
        // Example: a=7, coins[0]=2 → impossible (7%2=1)
        prev[a] = (int) 1e9; // Infinity (impossible)
      }
    }

    // Process each coin starting from index 1
    for (int i = 1; i < n; i++) {
      int[] curr = new int[amount + 1];

      for (int a = 0; a <= amount; a++) {
        // Option 1: PICK this coin
        int pick = (int) 1e9;
        if (a >= coins[i]) {
          // Can pick this coin (have enough amount)
          // CRITICAL: use curr[a - coins[i]], not prev
          // This allows picking same coin multiple times (unbounded)
          pick = 1 + curr[a - coins[i]];
        }

        // Option 2: NO-PICK this coin
        int noPick = 0 + prev[a];

        // Take minimum (we want fewest coins)
        curr[a] = Math.min(pick, noPick);
      }

      prev = curr; // Current becomes previous for next iteration
    }

    int res = prev[amount];
    return (res == (int) 1e9) ? -1 : res;

    // APPROACH 2: Tabulation (O(n * amount) space) - COMMENTED OUT
    //
    // int n = coins.length;
    // int[][] dp = new int[n][amount+1];
    //
    // // Base case: Fill first row
    // for(int a=0; a<=amount; a++){
    // if(a % coins[0] == 0){
    // dp[0][a] = a / coins[0];
    // }else {
    // dp[0][a] = (int)1e9;
    // }
    // }
    //
    // // Fill remaining rows
    // for(int i=1; i<n; i++){
    // for(int a=0; a<=amount; a++){
    // int pick = (int)1e9;
    // if(a >= coins[i]) {
    // pick = 1 + dp[i][a-coins[i]]; // Stay at same row
    // }
    // int noPick = 0 + dp[i-1][a];
    // dp[i][a] = Math.min(pick,noPick);
    // }
    // }
    //
    // int res = dp[n-1][amount];
    // return (res == (int)1e9) ? -1 : res;

    // APPROACH 3: Memoization (O(n * amount) space) - COMMENTED OUT
    //
    // int n = coins.length;
    // int[][] dp = new int[n][amount+1];
    // for(int i=0; i<n; i++){
    // Arrays.fill(dp[i] , -1);
    // }
    // int ans = recur(coins,amount,n-1,dp);
    // return (ans == (int)1e9) ? -1 : ans;
  }

  /**
   * Recursive helper with memoization
   * 
   * Returns: minimum number of coins needed to make 'amount'
   * using coins from index 0 to 'index'
   * Returns 1e9 if impossible
   */
  private int recur(int[] coins, int amount, int index, int[][] dp) {
    // BASE CASE: Only first coin (coins[0]) available
    if (index == 0) {
      // Question: Can we make EXACTLY 'amount' using ONLY coins[0]?

      if (amount % coins[0] == 0) {
        // YES! Amount is perfectly divisible by coins[0]
        // Example: amount=8, coins[0]=2
        // → 2+2+2+2 = 8 (4 coins)
        int val = amount / coins[0];
        dp[index][amount] = val;
        return val;
      }

      // NO! Amount is NOT divisible by coins[0]
      // Example: amount=7, coins[0]=2
      // → 2+2+2 = 6 (we can get close but not EXACTLY 7)
      // → We're short by 1, and we have no other coins
      // → IMPOSSIBLE!
      dp[index][amount] = (int) 1e9;
      return (int) 1e9;
    }

    // Check memoization
    if (dp[index][amount] != -1) {
      return dp[index][amount];
    }

    // Option 1: PICK current coin
    int pick = (int) 1e9;
    if (amount >= coins[index]) {
      // CRITICAL: Stay at same index (unbounded knapsack)
      // This allows picking same coin multiple times
      pick = 1 + recur(coins, amount - coins[index], index, dp);
    }

    // Option 2: NO-PICK current coin
    int noPick = 0 + recur(coins, amount, index - 1, dp);

    // Take minimum (we want fewest coins)
    dp[index][amount] = Math.min(pick, noPick);
    return dp[index][amount];
  }
}