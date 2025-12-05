package dp;

import java.util.*;

/*
 * Given an integer array nums, return the maximum possible sum of elements of
 * the array such that it is divisible by three.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,6,5,1,8]
 * Output: 18
 * Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum
 * divisible by 3).
 * Example 2:
 * 
 * Input: nums = [4]
 * Output: 0
 * Explanation: Since 4 is not divisible by 3, do not pick any number.
 */

/*
 * GREATEST SUM DIVISIBLE BY THREE PATTERN
 * 
 * Two approaches to solve this problem:
 * 
 * APPROACH 1: Greedy with Remainder Lists (O(n log n))
 * - Calculate total sum of all elements
 * - If sum % 3 == 0, we're done!
 * - Otherwise, remove minimum elements to make sum divisible by 3
 * 
 * APPROACH 2: DP with Remainder Tracking (O(n))
 * - Track maximum sum for each possible remainder (0, 1, 2)
 * - For each element, update all remainder states
 * - Return maximum sum with remainder 0
 */

/*
 * MODULAR ARITHMETIC KEY INSIGHT
 * 
 * For any number n:
 * - n % 3 can only be 0, 1, or 2
 * 
 * Sum properties:
 * - (a + b) % 3 = ((a % 3) + (b % 3)) % 3
 * 
 * To make sum divisible by 3:
 * - If sum % 3 == 0: Already divisible, keep all
 * - If sum % 3 == 1: Remove numbers with total remainder 1
 *   → Option A: Remove one number with remainder 1
 *   → Option B: Remove two numbers with remainder 2 (2+2=4, 4%3=1)
 * - If sum % 3 == 2: Remove numbers with total remainder 2
 *   → Option A: Remove one number with remainder 2
 *   → Option B: Remove two numbers with remainder 1 (1+1=2)
 */

/*
 * GREEDY APPROACH EXPLANATION
 * 
 * Strategy: Take everything, then remove minimum to fix remainder
 * 
 * Step 1: Calculate sum of ALL elements
 * Step 2: Classify elements by remainder:
 *   - r1: elements with remainder 1 (e.g., 1, 4, 7, 10...)
 *   - r2: elements with remainder 2 (e.g., 2, 5, 8, 11...)
 * Step 3: Sort both lists (to find minimum elements easily)
 * Step 4: Based on sum % 3, decide what to remove:
 *   - sum % 3 == 1: Remove min(one r1 element, two r2 elements)
 *   - sum % 3 == 2: Remove min(one r2 element, two r1 elements)
 */

/*
 * EXAMPLE: nums = [3, 6, 5, 1, 8]
 * 
 * Step 1: Calculate sum
 * - sum = 3 + 6 + 5 + 1 + 8 = 23
 * 
 * Step 2: Classify by remainder
 * - 3 % 3 = 0 → neither list
 * - 6 % 3 = 0 → neither list
 * - 5 % 3 = 2 → r2 = [5]
 * - 1 % 3 = 1 → r1 = [1]
 * - 8 % 3 = 2 → r2 = [5, 8]
 * 
 * After sorting:
 * - r1 = [1]
 * - r2 = [5, 8]
 * 
 * Step 3: sum % 3 = 23 % 3 = 2
 * - Need to remove remainder 2
 * - Option A: Remove one r2 element → remove 5 (smallest)
 * - Option B: Remove two r1 elements → only have 1, can't do this
 * - Choose option A: remove 5
 * 
 * Result: sum - 5 = 23 - 5 = 18 ✅
 * Verify: 3 + 6 + 1 + 8 = 18, 18 % 3 = 0 ✅
 */

/*
 * EXAMPLE: nums = [4]
 * 
 * Step 1: sum = 4
 * Step 2: 4 % 3 = 1 → r1 = [4]
 * Step 3: sum % 3 = 1
 * - Option A: Remove one r1 element → remove 4
 * - Option B: Remove two r2 elements → r2 is empty
 * - Choose option A: remove 4
 * 
 * Result: 4 - 4 = 0 ✅
 * (We can't make any positive sum divisible by 3)
 */

/*
 * WHY SORT THE LISTS?
 * 
 * We want to MAXIMIZE the final sum.
 * To maximize, we should MINIMIZE what we remove.
 * 
 * By sorting:
 * - r1.get(0) = smallest element with remainder 1
 * - r2.get(0) = smallest element with remainder 2
 * 
 * When choosing what to remove, we pick the smallest!
 * 
 * Example: r1 = [10, 1, 7, 4]
 * After sorting: r1 = [1, 4, 7, 10]
 * If we need to remove one r1 element, remove 1 (not 10!)
 */

/*
 * WHY USE Integer.MAX_VALUE?
 * 
 * When we don't have enough elements to remove:
 * 
 * Example: sum % 3 == 1
 * - Need to remove either: one r1 OR two r2
 * - If r1 is empty: remove1 = MAX_VALUE (impossible)
 * - If r2 has < 2 elements: remove2 = MAX_VALUE (impossible)
 * 
 * Then: min(remove1, remove2) picks the valid option
 * 
 * If BOTH are MAX_VALUE:
 * - result = sum - MAX_VALUE = huge negative
 * - That's why we return Math.max(result, 0)
 * - Ensures we never return negative
 */

/*
 * DP APPROACH EXPLANATION
 * 
 * State: dp[i][r] = maximum sum achievable using elements [0..i]
 *                   such that sum % 3 == r
 * 
 * Base case:
 * - When i >= n (no more elements):
 *   - If remainder == 0: return 0 (valid, empty sum)
 *   - Else: return MIN_VALUE (invalid state)
 * 
 * Transition:
 * - Pick: add nums[i], remainder becomes (r + nums[i]) % 3
 * - No-pick: don't add nums[i], remainder stays r
 * 
 * Answer: dp[0][0] = max sum with remainder 0 starting from index 0
 */

/*
 * DP EXAMPLE: nums = [3, 6, 5, 1, 8]
 * 
 * We want: solve(0, 0) = max sum starting at index 0 with target remainder 0
 * 
 * solve(0, 0):
 *   pick 3: 3 + solve(1, (0+3)%3=0)
 *   no-pick: 0 + solve(1, 0)
 * 
 * solve(1, 0):
 *   pick 6: 6 + solve(2, (0+6)%3=0)
 *   no-pick: 0 + solve(2, 0)
 * 
 * ... (continues recursively)
 * 
 * The DP automatically finds the best combination!
 */

/*
 * WHY USE Integer.MIN_VALUE IN DP?
 * 
 * When we reach the end with wrong remainder:
 * - Base case: if (r != 0) return MIN_VALUE
 * 
 * This marks this path as "invalid"
 * When doing Math.max(pick, noPick), invalid paths get ignored
 * 
 * Example:
 * - pick = 10 + MIN_VALUE = huge negative
 * - noPick = 5 + 0 = 5
 * - max(huge negative, 5) = 5 ✅
 */

/*
 * COMPARISON: Greedy vs DP
 * 
 * Greedy (O(n log n)):
 * - Pros: Intuitive, easy to understand
 * - Cons: Requires sorting, slightly slower
 * - Approach: Add all, then remove minimum
 * 
 * DP (O(n)):
 * - Pros: Faster, no sorting needed
 * - Cons: More complex to understand
 * - Approach: Track all remainder states
 * 
 * Both are correct! Choose based on preference.
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Modular arithmetic properties
 *    → (a + b) % 3 = ((a % 3) + (b % 3)) % 3
 * 
 * 2. Greedy: Add all, remove minimum
 *    → If sum % 3 == 1: remove min(1 r1, 2 r2)
 *    → If sum % 3 == 2: remove min(1 r2, 2 r1)
 * 
 * 3. DP: Track remainder states
 *    → dp[i][r] = max sum with remainder r from index i
 * 
 * 4. Use MIN/MAX_VALUE for impossible states
 *    → Helps with Math.max/min comparisons
 * 
 * 5. Both approaches work
 *    → Greedy O(n log n), DP O(n)
 */

class Solution {
  // APPROACH 1: GREEDY WITH REMAINDER LISTS

  public int maxSumDivThree(int[] nums) {
    // Time complexity: O(n log n) - due to sorting
    // Space complexity: O(n) - for r1 and r2 lists

    int sum = 0;
    List<Integer> r1 = new ArrayList<>(); // Elements with remainder 1
    List<Integer> r2 = new ArrayList<>(); // Elements with remainder 2

    // Step 1: Calculate total sum and classify elements by remainder
    for (int num : nums) {
      sum += num;

      // Classify by remainder when divided by 3
      if (num % 3 == 1) {
        r1.add(num); // Remainder 1: 1, 4, 7, 10, 13...
      } else if (num % 3 == 2) {
        r2.add(num); // Remainder 2: 2, 5, 8, 11, 14...
      }
      // Elements divisible by 3 (remainder 0) don't need tracking
      // They don't affect the remainder of the sum
    }

    // Step 2: If sum is already divisible by 3, we're done!
    if (sum % 3 == 0) {
      return sum;
    }

    // Step 3: Sort both lists to identify smallest elements
    // We want to remove MINIMUM to maximize the final sum
    Collections.sort(r1); // Sort ascending
    r2.sort((a, b) -> a - b); // Alternative sorting syntax

    int result = 0;

    // Step 4: Decide what to remove based on sum's remainder
    if (sum % 3 == 1) {
      // Sum has remainder 1, need to remove remainder 1
      // Option A: Remove one smallest element with remainder 1
      // Example: [1] → remove 1
      int remove1 = r1.size() >= 1 ? r1.get(0) : Integer.MAX_VALUE;

      // Option B: Remove two smallest elements with remainder 2
      // Example: [2, 5] → remove 2+5=7, but 7%3=1 ✅
      // Why? 2%3=2, 5%3=2, (2+2)%3=1
      int remove2 = r2.size() >= 2 ? r2.get(0) + r2.get(1) : Integer.MAX_VALUE;

      // Choose option that removes LESS (to maximize result)
      result = sum - Math.min(remove1, remove2);

    } else { // sum % 3 == 2
      // Sum has remainder 2, need to remove remainder 2
      // Option A: Remove one smallest element with remainder 2
      int remove1 = r2.size() >= 1 ? r2.get(0) : Integer.MAX_VALUE;

      // Option B: Remove two smallest elements with remainder 1
      // Why? 1%3=1, 1%3=1, (1+1)%3=2
      int remove2 = r1.size() >= 2 ? r1.get(0) + r1.get(1) : Integer.MAX_VALUE;

      // Choose option that removes LESS
      result = sum - Math.min(remove1, remove2);
    }

    // Return max(result, 0) to handle edge case:
    // If both remove1 and remove2 are MAX_VALUE,
    // result would be negative (sum - MAX_VALUE)
    // We can't have negative sum, so return 0
    return Math.max(result, 0);
  }

  // APPROACH 2: DP WITH REMAINDER TRACKING

  public int maxSumDivThreeDP(int[] nums) {
    // T.C = O(n) - single pass with memoization
    // S.C = O(n) - dp array of size n × 3

    // DP state: dp[i][r]
    // i = current index in array
    // r = current remainder (0, 1, or 2)
    // Value = maximum sum achievable from index i with target remainder r
    int[][] dp = new int[nums.length][3];

    // Initialize with -1 (unvisited state)
    for (int[] d : dp) {
      Arrays.fill(d, -1);
    }

    // Start from index 0 with target remainder 0
    // We want maximum sum that is divisible by 3 (remainder 0)
    return solve(0, 0, nums, dp);
  }

  /**
   * DP recursive function with memoization
   * 
   * @param i    Current index in nums array
   * @param r    Current remainder we're tracking (0, 1, or 2)
   * @param nums Input array
   * @param dp   Memoization table
   * @return Maximum sum achievable from index i with remainder r
   */
  private int solve(int i, int r, int[] nums, int[][] dp) {
    // BASE CASE: Reached end of array
    if (i >= nums.length) {
      // If we end with remainder 0, this is a valid path
      // Return 0 (empty sum contributes 0)
      if (r == 0) {
        return 0;
      }
      // If we end with remainder 1 or 2, this is invalid
      // Return MIN_VALUE to mark this path as "bad"
      return Integer.MIN_VALUE;
    }

    // Check memoization
    if (dp[i][r] != -1) {
      return dp[i][r];
    }

    // Option 1: PICK current element
    // Add nums[i] to sum, remainder becomes (r + nums[i]) % 3
    // Example: r=1, nums[i]=5 (5%3=2) → new r = (1+2)%3 = 0
    int pick = nums[i] + solve(i + 1, (r + nums[i]) % 3, nums, dp);

    // Option 2: NO-PICK current element
    // Don't add nums[i], remainder stays the same
    int noPick = 0 + solve(i + 1, r, nums, dp);

    // Take MAXIMUM (we want largest sum)
    // If one path is invalid (MIN_VALUE), max chooses the valid path
    return dp[i][r] = Math.max(pick, noPick);
  }
}
