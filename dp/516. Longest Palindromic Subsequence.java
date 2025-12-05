package dp;

/*
 * Given a string s, find the longest palindromic subsequence's length in s.
 * 
 * A subsequence is a sequence that can be derived from another sequence by
 * deleting some or no elements without changing the order of the remaining
 * elements.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "bbbab"
 * Output: 4
 * Explanation: One possible longest palindromic subsequence is "bbbb".
 * Example 2:
 * 
 * Input: s = "cbbd"
 * Output: 2
 * Explanation: One possible longest palindromic subsequence is "bb".
 * 
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 1000
 * s consists only of lowercase English letters.
 */

/*
 * LONGEST PALINDROMIC SUBSEQUENCE - Brilliant Reduction to LCS
 * 
 * This problem has an ELEGANT solution using Longest Common Subsequence!
 * 
 * Key Insight:
 * LPS(s) = LCS(s, reverse(s))
 * 
 * Why?
 * - A palindrome reads the same forwards and backwards
 * - If we find the longest common subsequence between s and reverse(s),
 *   it must be a palindrome!
 */

/*
 * WHY LCS(s, reverse(s)) GIVES PALINDROME?
 * 
 * Property of palindromes:
 * - For a string to be palindrome: s = reverse(s)
 * 
 * For subsequences:
 * - If substring appears in BOTH s and reverse(s) in the same order
 * - Then it reads the same forwards and backwards
 * - Therefore, it's a palindrome!
 * 
 * Example: s = "bbbab"
 * - reverse(s) = "babbb"
 * - LCS(s, reverse(s)) finds longest common subsequence
 * - This subsequence must be palindromic!
 */

/*
 * EXAMPLE 1: s = "bbbab"
 * 
 * Step 1: Create reverse
 * - s = "bbbab"
 * - reverse(s) = "babbb"
 * 
 * Step 2: Find LCS("bbbab", "babbb")
 * 
 * Matching subsequences that appear in both:
 * - "b" appears in both ✓
 * - "bb" appears in both ✓
 * - "bbb" appears in both ✓
 * - "bbbb" appears in both ✓
 * - "a" appears in both ✓
 * 
 * LCS = "bbbb" (length 4)
 * 
 * Verify it's palindrome: "bbbb" reads same forwards and backwards ✓
 * 
 * Answer: 4 ✅
 */

/*
 * EXAMPLE 2: s = "cbbd"
 * 
 * Step 1: Create reverse
 * - s = "cbbd"
 * - reverse(s) = "dbbc"
 * 
 * Step 2: Find LCS("cbbd", "dbbc")
 * 
 * Visualizing:
 * s:        c  b  b  d
 * reverse:  d  b  b  c
 * 
 * Common subsequences:
 * - "b" appears in both ✓
 * - "bb" appears in both ✓
 * - "c" appears in both ✓
 * - "d" appears in both ✓
 * 
 * LCS = "bb" (length 2)
 * 
 * Verify it's palindrome: "bb" reads same forwards and backwards ✓
 * 
 * Answer: 2 ✅
 */

/*
 * DETAILED WALKTHROUGH: s = "babad"
 * 
 * Original: "babad"
 * Reverse:  "dabab"
 * 
 * LCS DP Table:
 *       ""  d  a  b  a  b
 *    "" 0   0  0  0  0  0
 *    b  0   0  0  1  1  1
 *    a  0   0  1  1  2  2
 *    b  0   0  1  2  2  3
 *    a  0   0  1  2  3  3
 *    d  0   1  1  2  3  3
 * 
 * Final answer: LCS = 3
 * 
 * One possible LPS: "aba" or "bab" (both length 3)
 * Verify: Both are palindromes ✓
 */

/*
 * WHY THIS WORKS - MATHEMATICAL PROOF
 * 
 * Claim: Any subsequence common to s and reverse(s) is a palindrome
 * 
 * Proof:
 * 1. Let x be a subsequence of s at positions i₁, i₂, ..., iₖ
 *    x = s[i₁] s[i₂] ... s[iₖ]
 * 
 * 2. If x also appears in reverse(s), it appears at positions j₁, j₂, ..., jₖ
 *    where jₘ = n - 1 - iₘ (symmetric positions)
 * 
 * 3. Reading x in reverse(s) from left to right
 *    = Reading x in s from right to left
 * 
 * 4. If x appears in same order in both:
 *    x forwards = x backwards
 *    Therefore, x is a palindrome! ✓
 */

/*
 * EXAMPLE SHOWING THE INSIGHT: s = "racecar"
 * 
 * Original: "racecar"
 * Reverse:  "racecar" (same! whole string is palindrome)
 * 
 * LCS("racecar", "racecar") = "racecar" (length 7)
 * 
 * This is correct! The entire string is the longest palindromic subsequence.
 * 
 * For any palindrome string:
 * - s == reverse(s)
 * - LCS(s, reverse(s)) = s (entire string)
 * - Length = length of s
 */

/*
 * ALTERNATIVE APPROACH (Not Used Here)
 * 
 * There's also a direct DP approach without using LCS:
 * 
 * dp[i][j] = LPS of substring s[i..j]
 * 
 * if (s[i] == s[j]):
 *   dp[i][j] = 2 + dp[i+1][j-1]  // Include both characters
 * else:
 *   dp[i][j] = max(dp[i+1][j], dp[i][j-1])  // Skip one character
 * 
 * This works but is more complex to implement.
 * Using LCS(s, reverse(s)) is much simpler!
 */

/*
 * COMPARISON
 * 
 * LCS Approach (This Solution):
 * - Simple: Just reverse string and call LCS
 * - Easy to understand
 * - Reuses existing LCS code
 * - Time: O(n²), Space: O(n) with optimization
 * 
 * Direct DP Approach:
 * - More complex implementation
 * - Requires understanding interval DP
 * - Same time complexity O(n²)
 * - Same space complexity O(n²) or O(n)
 * 
 * LCS approach is preferred for simplicity!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Brilliant reduction: LPS(s) = LCS(s, reverse(s))
 *    → Palindrome property: reads same both ways
 * 
 * 2. Why it works: Common subsequence of s and reverse(s)
 *    → Must read same forwards and backwards
 *    → Therefore must be palindrome
 * 
 * 3. Simple implementation
 *    → Reverse string using StringBuilder
 *    → Call existing LCS function
 * 
 * 4. Reuses LCS pattern
 *    → Don't reinvent the wheel
 *    → Reduce new problem to solved problem
 * 
 * 5. Time: O(n²), Space: O(n) with optimization
 *    → Same as LCS complexity
 */

// Longest Common Subsequence

class Solution {
  public int longestPalindromeSubseq(String s) {
    // BRILLIANT INSIGHT: Longest Palindromic Subsequence
    // = Longest Common Subsequence of s and reverse(s)
    //
    // Why?
    // - A palindrome reads the same forwards and backwards
    // - Any subsequence that appears in BOTH s and reverse(s)
    // must be a palindrome (reads same both directions)
    // - The longest such subsequence is our answer!

    // Step 1: Create reversed string
    StringBuilder sb = new StringBuilder(s);
    String s2 = sb.reverse().toString();

    // Step 2: Find LCS of original and reversed
    // This LCS will be the longest palindromic subsequence!
    return longestCommonSubsequence(s, s2);
  }

  /**
   * Standard LCS implementation with space optimization
   * 
   * Finds longest common subsequence between text1 and text2
   * Time: O(n × m), Space: O(m)
   */
  public int longestCommonSubsequence(String text1, String text2) {
    // Space optimized LCS
    // Uses two 1D arrays instead of 2D array

    int n = text1.length();
    int m = text2.length();
    int[] prev = new int[m + 1]; // Previous row

    // Process each character of text1
    for (int i = 1; i <= n; i++) {
      int[] curr = new int[m + 1]; // Current row

      // Process each character of text2
      for (int j = 1; j <= m; j++) {
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          // Characters match: include in LCS
          curr[j] = 1 + prev[j - 1];
        } else {
          // Characters don't match: take max of skipping from either string
          int case1 = prev[j]; // Skip from text1
          int case2 = curr[j - 1]; // Skip from text2
          curr[j] = Math.max(case1, case2);
        }
      }

      prev = curr; // Current becomes previous for next iteration
    }

    return prev[m];
  }
}
