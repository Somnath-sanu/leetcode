package dp;

import java.util.*;

/*
 * Given two strings text1 and text2, return the length of their longest common
 * subsequence. If there is no common subsequence, return 0.
 * 
 * A subsequence of a string is a new string generated from the original string
 * with some characters (can be none) deleted without changing the relative
 * order of the remaining characters.
 * 
 * For example, "ace" is a subsequence of "abcde".
 * A common subsequence of two strings is a subsequence that is common to both
 * strings.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: text1 = "abcde", text2 = "ace"
 * Output: 3
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 * Example 2:
 * 
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 * Example 3:
 * 
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 * 
 * 
 * Constraints:
 * 
 * 1 <= text1.length, text2.length <= 1000
 * text1 and text2 consist of only lowercase English characters.
 */

/*
 * LONGEST COMMON SUBSEQUENCE (LCS) PATTERN
 * 
 * This is THE classic string DP problem!
 * 
 * Key concepts:
 * 1. Subsequence: can delete characters, but maintain order
 *    - "ace" is subsequence of "abcde" (delete 'b' and 'd')
 *    - "aec" is NOT subsequence of "abcde" (order changed)
 * 
 * 2. Common subsequence: appears in BOTH strings
 *    - text1 = "abcde", text2 = "ace"
 *    - "ace" appears in both (as subsequence)
 * 
 * 3. Longest: We want the MAXIMUM length
 */

/*
 * PROBLEM BREAKDOWN
 * 
 * Example: text1 = "abcde", text2 = "ace"
 * 
 * Subsequences of "abcde":
 * "", "a", "b", "c", "d", "e", "ab", "ac", "ad", "ae", "bc", "bd", "be",
 * "cd", "ce", "de", "abc", "abd", "abe", "acd", "ace" ✅, ...
 * 
 * Subsequences of "ace":
 * "", "a", "c", "e", "ac", "ae", "ce", "ace" ✅
 * 
 * Common subsequences:
 * "", "a", "c", "e", "ac", "ae", "ce", "ace" ✅
 * 
 * Longest: "ace" with length 3
 */

/*
 * RECURSIVE APPROACH - THE INSIGHT
 * 
 * For strings text1[0..i] and text2[0..j]:
 * 
 * Case 1: Characters match (text1[i] == text2[j])
 * - We can include this character in LCS
 * - LCS = 1 + LCS(text1[0..i-1], text2[0..j-1])
 * - Example: text1="abc", text2="ac"
 *   - 'c' matches 'c' → 1 + LCS("ab", "a")
 * 
 * Case 2: Characters don't match (text1[i] != text2[j])
 * - We can either:
 *   - Skip character from text1: LCS(text1[0..i-1], text2[0..j])
 *   - Skip character from text2: LCS(text1[0..i], text2[0..j-1])
 * - Take maximum of both options
 * - Example: text1="abc", text2="adc"
 *   - 'c' doesn't match 'd'
 *   - Try: LCS("ab", "adc") or LCS("abc", "ad")
 * 
 * Base case: If either string is empty, LCS = 0
 */

/*
 * WHY 1-BASED INDEXING (dp[n+1][m+1])?
 * 
 * We use dp[n+1][m+1] instead of dp[n][m] to handle base case elegantly.
 * 
 * With 1-based:
 * - dp[0][j] = 0 (text1 is empty, LCS = 0)
 * - dp[i][0] = 0 (text2 is empty, LCS = 0)
 * - dp[i][j] represents LCS of text1[0..i-1] and text2[0..j-1]
 * 
 * Character access:
 * - dp[i][j] uses text1.charAt(i-1) and text2.charAt(j-1)
 * - This shifts: index i in DP corresponds to character at i-1
 * 
 * Example: text1 = "abc", n = 3
 * DP indices:    0   1   2   3
 * Characters:    -   a   b   c
 *                ↑   ↑   ↑   ↑
 *              empty [0] [1] [2]
 * 
 * This avoids index-out-of-bounds when accessing dp[i-1][j-1]
 */

/*
 * DP TABLE VISUALIZATION
 * 
 * Example: text1 = "abcde", text2 = "ace"
 * 
 *       ""  a  c  e
 *    "" 0   0  0  0
 *    a  0   1  1  1
 *    b  0   1  1  1
 *    c  0   1  2  2
 *    d  0   1  2  2
 *    e  0   1  2  3
 * 
 * How to read:
 * - dp[1][1]: LCS of "a" and "a" = 1 (match!)
 * - dp[3][2]: LCS of "abc" and "ac" = 2 ("ac")
 * - dp[5][3]: LCS of "abcde" and "ace" = 3 ("ace")
 * 
 * Building process:
 * - dp[3][2] (c vs c): match! 1 + dp[2][1] = 1 + 1 = 2
 * - dp[2][2] (b vs c): no match, max(dp[1][2], dp[2][1]) = max(1,1) = 1
 */

/*
 * EXAMPLE WALKTHROUGH: text1 = "abc", text2 = "abc"
 * 
 * Building DP table:
 * 
 *       ""  a  b  c
 *    "" 0   0  0  0
 *    a  0   ?  ?  ?
 *    b  0   ?  ?  ?
 *    c  0   ?  ?  ?
 * 
 * Fill row by row:
 * 
 * dp[1][1]: text1[0]='a' vs text2[0]='a'
 * - Match! 1 + dp[0][0] = 1 + 0 = 1
 * 
 * dp[1][2]: text1[0]='a' vs text2[1]='b'
 * - No match. max(dp[0][2], dp[1][1]) = max(0, 1) = 1
 * 
 * dp[1][3]: text1[0]='a' vs text2[2]='c'
 * - No match. max(dp[0][3], dp[1][2]) = max(0, 1) = 1
 * 
 * ... continue ...
 * 
 * Final:
 *       ""  a  b  c
 *    "" 0   0  0  0
 *    a  0   1  1  1
 *    b  0   1  2  2
 *    c  0   1  2  3
 * 
 * Answer: dp[3][3] = 3 ✅
 */

/*
 * EXAMPLE: text1 = "abcde", text2 = "ace"
 * 
 * Key steps:
 * 
 * dp[1][1]: 'a' vs 'a' → match! 1 + dp[0][0] = 1
 * dp[3][2]: 'c' vs 'c' → match! 1 + dp[2][1] = 1 + 1 = 2
 * dp[5][3]: 'e' vs 'e' → match! 1 + dp[4][2] = 1 + 2 = 3
 * 
 * The matching characters 'a', 'c', 'e' form the LCS!
 */

/*
 * WHY charAt(i-1) not charAt(i)?
 * 
 * Because of 1-based indexing:
 * - DP index i=1 represents first character (index 0 in string)
 * - DP index i=2 represents second character (index 1 in string)
 * 
 * Example: text1 = "abc"
 * - dp[1][j] processes text1.charAt(0) = 'a'
 * - dp[2][j] processes text1.charAt(1) = 'b'
 * - dp[3][j] processes text1.charAt(2) = 'c'
 * 
 * General formula: dp[i][j] uses text1.charAt(i-1) and text2.charAt(j-1)
 */

/*
 * SPACE OPTIMIZATION
 * 
 * Original: dp[n+1][m+1] → O(n × m) space
 * 
 * Observation:
 * - To compute dp[i][j], we only need:
 *   - dp[i-1][j-1] (diagonal)
 *   - dp[i-1][j] (above)
 *   - dp[i][j-1] (left)
 * - We only need PREVIOUS ROW to compute CURRENT ROW
 * 
 * Optimized: Use two 1D arrays
 * - prev[m+1] = previous row
 * - curr[m+1] = current row
 * - Space: O(m) instead of O(n × m)
 */

/*
 * COMPARISON WITH OTHER STRING PROBLEMS
 * 
 * LCS (Longest Common Subsequence):
 * - Can skip characters from either string
 * - Order must be maintained
 * - Example: "ace" is LCS of "abcde" and "ace"
 * 
 * Longest Common Substring:
 * - Must be contiguous in BOTH strings
 * - Example: "abc" and "zabc" have LCS substring "abc"
 * 
 * Edit Distance:
 * - Minimum operations to convert one string to another
 * - Similar DP structure but different recurrence
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. LCS is a classic 2D DP problem
 *    → dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]
 * 
 * 2. Two cases based on character match
 *    → Match: 1 + dp[i-1][j-1]
 *    → No match: max(dp[i-1][j], dp[i][j-1])
 * 
 * 3. Use 1-based indexing for clean base case
 *    → dp[0][j] = 0, dp[i][0] = 0
 *    → Access characters at i-1 and j-1
 * 
 * 4. Can optimize space to O(m)
 *    → Only need previous row
 * 
 * 5. Time: O(n × m), Space: O(n × m) or O(m)
 */

class Solution {
  public int longestCommonSubsequence(String text1, String text2) {

    // APPROACH 1: Space Optimized (O(m) space) - COMMENTED OUT
    //
    // int n = text1.length();
    // int m = text2.length();
    // int[] prev = new int[m+1]; // Previous row
    //
    // for(int i=1; i<=n; i++){
    // int[] curr = new int[m+1]; // Current row
    //
    // for(int j=1; j<=m; j++){
    // if(text1.charAt(i-1) == text2.charAt(j-1)){
    // // Characters match: include in LCS
    // curr[j] = 1 + prev[j-1]; // prev[j-1] is dp[i-1][j-1]
    // }
    // else{
    // // Characters don't match: take max of skipping from either string
    // int case1 = prev[j]; // Skip from text1 (dp[i-1][j])
    // int case2 = curr[j-1]; // Skip from text2 (dp[i][j-1])
    // curr[j] = Math.max(case1, case2);
    // }
    // }
    // prev = curr; // Current becomes previous for next iteration
    // }
    //
    // return prev[m];

    // APPROACH 2: Tabulation (O(n × m) space)

    int n = text1.length();
    int m = text2.length();

    // Create DP table with size (n+1) × (m+1)
    // +1 to handle empty string base case
    int[][] dp = new int[n + 1][m + 1];

    // Base case is already initialized to 0
    // dp[0][j] = 0 for all j (text1 is empty)
    // dp[i][0] = 0 for all i (text2 is empty)

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        // Compare characters at position i-1 in text1 and j-1 in text2
        // (i-1 and j-1 because we're using 1-based indexing in DP)
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          // Case 1: Characters MATCH
          // We can include this character in the LCS
          // Add 1 to the LCS of strings without these characters
          dp[i][j] = 1 + dp[i - 1][j - 1];

        } else {
          // Case 2: Characters DON'T MATCH
          // We have two options:

          // Option A: Skip character from text1
          // LCS of text1[0..i-2] and text2[0..j-1]
          int case1 = dp[i - 1][j];

          // Option B: Skip character from text2
          // LCS of text1[0..i-1] and text2[0..j-2]
          int case2 = dp[i][j - 1];

          // Take the maximum of both options
          dp[i][j] = Math.max(case1, case2);
        }
      }
    }

    // Debug: Print DP table (optional)
    print(dp);

    // Answer is in bottom-right cell
    // LCS of entire text1 and entire text2
    return dp[n][m];

    // APPROACH 3: Memoization (O(n × m) space) - COMMENTED OUT
    //
    // int n = text1.length();
    // int m = text2.length();
    // int[][] dp = new int[n+1][m+1];
    //
    // // Initialize with -1 (unvisited)
    // for(int i=0; i<=n; i++){
    // Arrays.fill(dp[i], -1);
    // }
    //
    // return recur(n, m, text1, text2, dp);
  }

  /**
   * Recursive helper with memoization
   * 
   * Returns LCS length of text1[0..i-1] and text2[0..j-1]
   * 
   * @param i     Length of text1 substring to consider (1-based)
   * @param j     Length of text2 substring to consider (1-based)
   * @param text1 First string
   * @param text2 Second string
   * @param dp    Memoization table
   * @return Length of LCS
   */
  private int recur(int i, int j, String text1, String text2, int[][] dp) {
    // BASE CASE: One of the strings is empty
    if (i == 0 || j == 0) {
      dp[i][j] = 0;
      return 0;
    }

    // Check memoization
    if (dp[i][j] != -1) {
      return dp[i][j];
    }

    // Compare characters at position i-1 and j-1
    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
      // Characters match: include in LCS
      dp[i][j] = 1 + recur(i - 1, j - 1, text1, text2, dp);
      return dp[i][j];

    } else {
      // Characters don't match: try both options

      // Option 1: Skip character from text1
      int case1 = recur(i - 1, j, text1, text2, dp);

      // Option 2: Skip character from text2
      int case2 = recur(i, j - 1, text1, text2, dp);

      // Take maximum
      dp[i][j] = Math.max(case1, case2);
      return dp[i][j];
    }
  }

  /**
   * Helper function to print DP table for debugging
   * 
   * Visualizes how the DP table is filled
   */
  private void print(int[][] dp) {
    int n = dp.length;
    int m = dp[0].length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        System.out.print(dp[i][j] + " ,");
      }
      System.out.println();
    }
  }
}