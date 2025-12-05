package dp;

/*
 * Given two strings str1 and str2, return the shortest string that has both
 * str1 and str2 as subsequences. If there are multiple valid strings, return
 * any of them.
 * 
 * A string s is a subsequence of string t if deleting some number of characters
 * from t (possibly 0) results in the string s.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: str1 = "abac", str2 = "cab"
 * Output: "cabac"
 * Explanation:
 * str1 = "abac" is a subsequence of "cabac" because we can delete the first
 * "c".
 * str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
 * The answer provided is the shortest such string that satisfies these
 * properties.
 * Example 2:
 * 
 * Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
 * Output: "aaaaaaaa"
 * 
 * 
 * Constraints:
 * 
 * 1 <= str1.length, str2.length <= 1000
 * str1 and str2 consist of lowercase English letters.
 */

/*
 * SHORTEST COMMON SUPERSEQUENCE - LCS + Backtracking
 * 
 * This problem combines LCS with a clever backtracking algorithm!
 * 
 * Key Insights:
 * 1. If we just concatenate str1 + str2, we get a valid supersequence
 *    but it's NOT the shortest (has repeated characters)
 * 
 * 2. To minimize length, we should merge common characters
 *    Common characters = LCS (Longest Common Subsequence)
 * 
 * 3. Formula for length:
 *    SCS_length = len(str1) + len(str2) - len(LCS)
 * 
 * 4. To construct the actual string:
 *    - Build LCS DP table
 *    - Backtrack to merge characters optimally
 */

/*
 * WHY len(str1) + len(str2) - len(LCS)?
 * 
 * Example: str1 = "abac", str2 = "cab"
 * 
 * Naive concatenation: "abac" + "cab" = "abaccab" (length 7)
 * 
 * But there's common subsequence: LCS("abac", "cab") = "ab" or "ca" (length 2)
 * 
 * If we merge the common characters instead of duplicating:
 * - str1 has: a, b, a, c (4 characters)
 * - str2 has: c, a, b (3 characters)
 * - Common (LCS): a, b (2 characters - counted in both)
 * 
 * Total unique characters = 4 + 3 - 2 = 5
 * 
 * Result: "cabac" (length 5) ✅
 * Verify:
 * - "abac" is subsequence: c[abac] ✓
 * - "cab" is subsequence: [cab]ac ✓
 */

/*
 * BACKTRACKING ALGORITHM - The Core Idea
 * 
 * We traverse the LCS DP table from bottom-right (dp[n][m]) to top-left (dp[0][0])
 * 
 * Three cases during backtracking:
 * 
 * Case 1: Characters match (str1[i-1] == str2[j-1])
 * - This character is in the LCS
 * - Add it ONCE to result (no duplication)
 * - Move diagonally: i--, j--
 * 
 * Case 2: Characters don't match, dp[i-1][j] > dp[i][j-1]
 * - Character from str1 is not in LCS
 * - Add str1[i-1] to result
 * - Move up: i--
 * 
 * Case 3: Characters don't match, dp[i][j-1] >= dp[i-1][j]
 * - Character from str2 is not in LCS
 * - Add str2[j-1] to result
 * - Move left: j--
 */

/*
 * EXAMPLE: str1 = "abac", str2 = "cab"
 * 
 * Step 1: Build LCS DP table
 * 
 *       ""  c  a  b
 *    "" 0   0  0  0
 *    a  0   0  1  1
 *    b  0   0  1  2
 *    a  0   0  1  2
 *    c  0   1  1  2
 * 
 * LCS length = dp[4][3] = 2
 * 
 * Step 2: Backtrack from dp[4][3]
 * 
 * Position (4,3): str1[3]='c', str2[2]='b'
 * - 'c' != 'b'
 * - dp[3][3]=2, dp[4][2]=1
 * - dp[3][3] > dp[4][2] → take from str1
 * - Add 'c', move to (3,3)
 * - Result: "c"
 * 
 * Position (3,3): str1[2]='a', str2[2]='b'
 * - 'a' != 'b'
 * - dp[2][3]=2, dp[3][2]=1
 * - dp[2][3] > dp[3][2] → take from str1
 * - Add 'a', move to (2,3)
 * - Result: "ca"
 * 
 * Position (2,3): str1[1]='b', str2[2]='b'
 * - 'b' == 'b' ✅ (in LCS)
 * - Add 'b' once, move to (1,2)
 * - Result: "cab"
 * 
 * Position (1,2): str1[0]='a', str2[1]='a'
 * - 'a' == 'a' ✅ (in LCS)
 * - Add 'a' once, move to (0,1)
 * - Result: "caba"
 * 
 * Position (0,1): i=0, but j=1
 * - Add remaining from str2: str2[0]='c'
 * - Result: "cabac"
 * 
 * Step 3: Reverse (we built backwards)
 * - Reverse "cabac" = "cabac" (palindrome in this case!)
 * 
 * Final: "cabac" ✅
 */

/*
 * VISUAL TRACE OF BACKTRACKING
 * 
 * LCS DP table with path marked:
 * 
 *       ""  c  a  b
 *    "" 0   0  0  0
 *    a  0   0  1  1
 *    b  0   0  1  2  ←
 *    a  0   0  1↖ 2  ←
 *    c  0   1  1  2  ← start
 * 
 * Path:
 * (4,3) → (3,3) → (2,3) → (1,2) → (0,1) → (0,0)
 *   ↓       ↓       ↓       ↓       ↓
 *  'c'     'a'     'b'     'a'     'c'
 * 
 * Building: c → a → b → a → c (reverse order)
 * Reversed: c a b a c ✅
 */

/*
 * WHY BUILD BACKWARDS THEN REVERSE?
 * 
 * We backtrack from (n,m) to (0,0)
 * - Processing characters from end to beginning
 * - StringBuilder appends in reverse order
 * - Final step: reverse to get correct order
 * 
 * Alternative: Could use StringBuilder with insert(0, char)
 * - But that's O(n) for each insertion
 * - append + reverse is more efficient
 */

/*
 * HANDLING REMAINING CHARACTERS
 * 
 * After main backtracking, we might have:
 * - i > 0: str1 has leftover characters
 * - j > 0: str2 has leftover characters
 * 
 * Example: str1 = "abc", str2 = "xyz"
 * - LCS is empty (no common characters)
 * - After backtracking: need to add all of str1 and str2
 * 
 * We add remaining characters:
 * - while (j > 0): add str2[j-1], j--
 * - while (i > 0): add str1[i-1], i--
 */

/*
 * EXAMPLE 2: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
 * 
 * LCS = "aaaaaaaa" (entire string, length 8)
 * 
 * Length = 8 + 8 - 8 = 8
 * 
 * During backtracking:
 * - All positions: characters match
 * - Add 'a' once for each position
 * - Result: "aaaaaaaa" (8 a's)
 * 
 * This makes sense: when strings are identical,
 * supersequence is just the string itself!
 */

/*
 * COMPARISON WITH NAIVE APPROACH
 * 
 * Naive: str1 + str2
 * - Example: "abac" + "cab" = "abaccab" (7 chars)
 * - Always valid but not minimal
 * 
 * Optimal: Use LCS backtracking
 * - Example: "cabac" (5 chars)
 * - Merges common characters
 * - Shortest possible
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Length formula: len(str1) + len(str2) - len(LCS)
 *    → Merging common characters reduces duplication
 * 
 * 2. Build LCS DP table first
 *    → Need it for backtracking
 * 
 * 3. Backtrack from (n,m) to (0,0)
 *    → Match: add once, go diagonal
 *    → No match: add from higher DP value, go up/left
 * 
 * 4. Handle remaining characters
 *    → After backtracking, add leftovers from either string
 * 
 * 5. Build backwards, then reverse
 *    → More efficient than inserting at front
 * 
 * 6. Time: O(n × m), Space: O(n × m)
 *    → Same as LCS complexity
 */

class Solution {
  public String shortestCommonSupersequence(String str1, String str2) {
    // KEY INSIGHT: If we just concatenate str1 + str2
    // we get a valid supersequence but NOT the shortest
    // because there may be repeated/common characters
    //
    // To minimize length, we should merge common characters
    // Common characters = LCS (Longest Common Subsequence)
    //
    // Length formula (if we just needed length):
    // SCS_length = len(str1) + len(str2) - len(LCS(str1, str2))
    //
    // But we need to construct the actual string,
    // so we use LCS DP table + backtracking

    // Step 1: Build LCS DP table
    // We need the full table (not just the length) for backtracking
    int[][] dp = longestCommonSubsequence(str1, str2);

    int n = dp.length;
    int m = dp[0].length;

    // Start backtracking from bottom-right
    int i = n - 1;
    int j = m - 1;

    StringBuilder sb = new StringBuilder();

    // Step 2: Backtrack through DP table
    // Build the supersequence in REVERSE order
    while (i > 0 && j > 0) {
      if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
        // Case 1: Characters MATCH (part of LCS)
        // Add this character ONCE (no duplication)
        // Move diagonally up-left
        sb.append(str1.charAt(i - 1));
        i--;
        j--;

      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        // Case 2: Characters don't match
        // Character from str1 has higher DP value
        // This means str1[i-1] is NOT in the LCS at this position
        // Add it to result and move up
        sb.append(str1.charAt(i - 1));
        i--;

      } else {
        // Case 3: Characters don't match
        // Character from str2 has higher (or equal) DP value
        // This means str2[j-1] is NOT in the LCS at this position
        // Add it to result and move left
        sb.append(str2.charAt(j - 1));
        j--;
      }
    }

    // Step 3: Add remaining characters from str2 (if any)
    // If j > 0, we still have characters left in str2
    // These are at the beginning of str2 and weren't processed
    while (j > 0) {
      sb.append(str2.charAt(j - 1));
      j--;
    }

    // Step 4: Add remaining characters from str1 (if any)
    // If i > 0, we still have characters left in str1
    // These are at the beginning of str1 and weren't processed
    while (i > 0) {
      sb.append(str1.charAt(i - 1));
      i--;
    }

    // Step 5: Reverse the result
    // We built the string backwards (from end to start)
    // Reverse to get the correct order
    String res = sb.reverse().toString();
    return res;
  }

  /**
   * Build LCS DP table
   * 
   * Returns the full DP table (not just the length)
   * We need it for backtracking in the main function
   * 
   * @param text1 First string
   * @param text2 Second string
   * @return DP table where dp[i][j] = LCS length of text1[0..i-1] and
   *         text2[0..j-1]
   */
  public int[][] longestCommonSubsequence(String text1, String text2) {
    int n = text1.length();
    int m = text2.length();

    // Create DP table with size (n+1) × (m+1)
    int[][] dp = new int[n + 1][m + 1];

    // Fill the DP table using standard LCS recurrence
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          // Characters match: include in LCS
          dp[i][j] = 1 + dp[i - 1][j - 1];
        } else {
          // Characters don't match: take max of skipping from either string
          int case1 = dp[i][j - 1]; // Skip from text2
          int case2 = dp[i - 1][j]; // Skip from text1
          dp[i][j] = Math.max(case1, case2);
        }
      }
    }

    // Return full table (not just dp[n][m])
    // We need this for backtracking
    return dp;
  }
}