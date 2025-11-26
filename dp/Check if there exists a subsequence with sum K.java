package dp;

/* GFG
 * Given an array arr and target sum k, check whether there exists a subsequence
 * such that the sum of all elements in the subsequence equals the given target
 * sum(k).
 * 
 * 
 * Example:
 * 
 * Input: arr = [10,1,2,7,6,1,5], k = 8.
 * Output: Yes
 * Explanation: Subsequences like [2, 6], [1, 7] sum upto 8
 * 
 * Input: arr = [2,3,5,7,9], k = 100.
 * Output: No
 * Explanation: No subsequence can sum upto 100
 */

/*
 * CRITICAL INSIGHTS - Why the Wrong Approach Fails
 * 
 * This problem has a subtle but crucial requirement that's easy to miss.
 * The difference between wrong and correct solution lies in TWO key points:
 * 
 * 1. MUST check arr[index] <= k BEFORE picking
 * 2. MUST return immediately if pick succeeds (early termination)
 * 
 * Let's understand why these are essential:
 */

/*
 * PROBLEM 1: Negative k Values Break Memoization
 * 
 * Wrong Approach:
 *   - Pick first: recur(arr, index-1, k - arr[index], dp)
 *   - Then check: if (k < 0) return false
 * 
 * Why it fails:
 *   - If arr[index] = 10 and k = 5, we call recur(arr, index-1, -5, dp)
 *   - Negative k cannot be used as array index: dp[index][-5] → ERROR!
 *   - Even if we handle it, dp[index][k] is designed for k >= 0 only
 * 
 * Correct Approach:
 *   - Check first: if (arr[index] <= k)
 *   - Then pick: recur(arr, index-1, k - arr[index], dp)
 *   - This ensures k - arr[index] >= 0, so memoization works correctly
 * 
 * Example:
 *   arr = [10, 1, 2], k = 5, index = 0
 *   
 *   Wrong: pick = recur(arr, -1, 5-10, dp) = recur(arr, -1, -5, dp) ❌
 *          Cannot access dp[index][-5]
 *   
 *   Correct: if (arr[0] <= 5) → if (10 <= 5) → false ✅
 *            Don't pick, avoid negative k entirely
 */

/*
 * PROBLEM 2: Missing Early Termination Causes Wrong Results
 * 
 * Wrong Approach:
 *   boolean pick = recur(arr, index-1, k - arr[index], dp);
 *   boolean noPick = recur(arr, index-1, k, dp);
 *   return pick || noPick;
 * 
 * Why it fails:
 *   - Even if pick finds a valid subsequence (returns true), we still call noPick
 *   - The noPick call might overwrite dp[index][k] with wrong value
 *   - This causes incorrect memoization for future calls
 * 
 * Correct Approach:
 *   if (arr[index] <= k) {
 *     pick = recur(arr, k - arr[index], index-1, dp);
 *     if (pick) {
 *       dp[index][k] = 1;
 *       return true;  // Early termination! Don't call noPick
 *     }
 *   }
 *   boolean noPick = recur(arr, k, index-1, dp);
 * 
 * Example: arr = [3, 2, 5], k = 5, index = 2
 * 
 *   Wrong approach:
 *     pick = recur(arr, 1, 0, dp)  → finds [5], returns true ✅
 *     noPick = recur(arr, 1, 5, dp) → explores [3,2], returns false ❌
 *     dp[2][5] might get set incorrectly depending on order
 *   
 *   Correct approach:
 *     pick = recur(arr, 1, 0, dp)  → finds [5], returns true ✅
 *     if (pick) return true immediately → dp[2][5] = 1 ✅
 *     noPick is never called, no chance of corruption
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Always validate arr[index] <= k before picking
 *    → Prevents negative k values
 *    → Ensures valid array indexing for dp[index][k]
 * 
 * 2. Return immediately when pick succeeds
 *    → Prevents unnecessary computation
 *    → Avoids memoization corruption
 *    → Optimization: if we found answer, no need to explore other paths
 * 
 * 3. Only call noPick if pick fails or is not attempted
 *    → Ensures correct memoization
 *    → Maintains logical consistency
 */

/* 
 * WRONG CODE - Annotated to show mistakes
 * 
 * This code has two critical bugs:
 * 1. Checks k < 0 AFTER making recursive call (too late!)
 * 2. Calls both pick and noPick without early termination
 */

// class Solution {
// public static boolean checkSubsequenceSum(int N, int[] arr, int K) {

// int[][] dp = new int[N][K + 1]; // default 0

// return recur(arr, N - 1, K, dp);
// }

// private static boolean recur(int[] arr, int index, int K, int[][] dp) {

// // ❌ BUG 1: Checking k < 0 here is TOO LATE!
// // By the time we reach here, we've already called recur() with negative k
// // This means we tried to access dp[index][negative_k] → crash!
// if (K < 0)
// return false;

// if (index == 0) {
// if (arr[index] == K) {
// dp[index][K] = 1;
// return true;
// }
// dp[index][K] = 2;
// return false;
// }

// if (K == 0) {
// dp[index][K] = 1;
// return true;
// }

// if (dp[index][K] != 0) {
// return dp[index][K] == 1;
// }

// // ❌ BUG 2: No check before picking!
// // If arr[index] > K, then K - arr[index] is negative
// // Next recursive call will try to access dp[index-1][negative_value] → crash!
// boolean pick = recur(arr, index - 1, K - arr[index], dp);

// // ❌ BUG 3: No early termination!
// // Even if pick is true, we still call noPick
// // This can corrupt memoization and waste computation
// boolean noPick = recur(arr, index - 1, K, dp);

// if (pick || noPick) {
// dp[index][K] = 1;
// return true;
// }

// dp[index][K] = 2;
// return false;
// }
// }

// User function Template for Java

class Solution {
  public static boolean checkSubsequenceSum(int N, int[] arr, int K) {
    // DP array to memoize results
    // dp[index][k] stores whether subsequence with sum k exists using arr[0..index]
    int[][] dp = new int[N][K + 1];

    // Encoding scheme for boolean values in dp array:
    // 0 → not visited (uncomputed state)
    // 1 → true (subsequence with sum k exists)
    // 2 → false (no subsequence with sum k exists)
    //
    // Why not use boolean[][]? Because we need to distinguish between:
    // - "not computed yet" (0) vs "computed and result is false" (2)
    // - Default boolean value is false, can't tell if it's computed or not

    return recur(arr, K, N - 1, dp);
  }

  private static boolean recur(int[] arr, int k, int index, int[][] dp) {
    // Base case 1: Target sum reached
    // If k becomes 0, we've found a valid subsequence
    // This can happen at any index (even index > 0)
    if (k == 0) {
      dp[index][k] = 1; // Mark as true
      return true;
    }

    // Base case 2: Reached first element
    // Only one choice: include arr[0] or not
    if (index == 0) {
      if (arr[index] == k) {
        dp[index][k] = 1; // arr[0] alone equals k
        return true;
      }
      dp[index][k] = 2; // arr[0] doesn't equal k, no subsequence possible
      return false;
    }

    // Memoization check: return cached result if available
    if (dp[index][k] != 0) {
      return (dp[index][k] == 1); // Convert 1→true, 2→false
    }

    // Option 1: Pick current element (if valid)
    boolean pick = false;

    // CRITICAL: Only pick if arr[index] <= k
    // This prevents negative k values which would:
    // 1. Cause ArrayIndexOutOfBoundsException (dp[index][-k])
    // 2. Break the memoization logic
    if (arr[index] <= k) {
      pick = recur(arr, k - arr[index], index - 1, dp);

      // CRITICAL: Early termination if pick succeeds
      // If we found a valid subsequence by picking, return immediately
      // Don't call noPick - it might corrupt memoization
      if (pick) {
        dp[index][k] = 1;
        return true;
      }
    }

    // Option 2: Don't pick current element
    // Only reached if:
    // - arr[index] > k (can't pick), OR
    // - arr[index] <= k but picking didn't lead to solution
    boolean noPick = recur(arr, k, index - 1, dp);

    // Store result in dp array
    dp[index][k] = (noPick == true) ? 1 : 2;

    return noPick;
  }
}
