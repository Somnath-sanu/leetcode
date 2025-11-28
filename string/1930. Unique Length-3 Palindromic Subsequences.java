package string;

import java.util.HashSet;
import java.util.Set;

/*
 * Given a string s, return the number of unique palindromes of length three
 * that are a subsequence of s.
 * 
 * Note that even if there are multiple ways to obtain the same subsequence, it
 * is still only counted once.
 * 
 * A palindrome is a string that reads the same forwards and backwards.
 * 
 * A subsequence of a string is a new string generated from the original string
 * with some characters (can be none) deleted without changing the relative
 * order of the remaining characters.
 * 
 * For example, "ace" is a subsequence of "abcde".
 * 
 * 
 * Example 1:
 * 
 * Input: s = "aabca"
 * Output: 3
 * Explanation: The 3 palindromic subsequences of length 3 are:
 * - "aba" (subsequence of "aabca")
 * - "aaa" (subsequence of "aabca")
 * - "aca" (subsequence of "aabca")
 * Example 2:
 * 
 * Input: s = "adc"
 * Output: 0
 * Explanation: There are no palindromic subsequences of length 3 in "adc".
 * Example 3:
 * 
 * Input: s = "bbcbaba"
 * Output: 4
 * Explanation: The 4 palindromic subsequences of length 3 are:
 * - "bbb" (subsequence of "bbcbaba")
 * - "bcb" (subsequence of "bbcbaba")
 * - "bab" (subsequence of "bbcbaba")
 * - "aba" (subsequence of "bbcbaba")
 * 
 * 
 * Constraints:
 * 
 * 3 <= s.length <= 10^5
 * s consists of only lowercase English letters.
 */

/*
 * KEY INSIGHT: Length-3 Palindrome Pattern
 * 
 * A palindrome of length 3 has the form: XYX
 * - First character = Last character (both are X)
 * - Middle character can be anything (Y)
 * 
 * Examples:
 * - "aba": X=a, Y=b
 * - "aaa": X=a, Y=a
 * - "cdc": X=c, Y=d
 * 
 * To count unique palindromes:
 * 1. For each character X that appears at least twice in s
 * 2. Find the first and last occurrence of X
 * 3. Count unique characters Y between first and last occurrence
 * 4. Each unique Y forms a unique palindrome XYX
 */

/*
 * EXAMPLE: s = "aabca"
 * 
 * Character 'a' (appears 3 times):
 * - First occurrence: index 0
 * - Last occurrence: index 4
 * - Characters between [1, 3]: 'a', 'b', 'c'
 * - Unique characters: {a, b, c} → 3 palindromes
 *   - "aaa" (a_a_a)
 *   - "aba" (a_b_a)
 *   - "aca" (a_c_a)
 * 
 * Character 'b' (appears 1 time):
 * - First = Last → skip (need at least 2 occurrences)
 * 
 * Character 'c' (appears 1 time):
 * - First = Last → skip
 * 
 * Total: 3 unique palindromes ✅
 */

/*
 * EXAMPLE: s = "bbcbaba"
 * 
 * Character 'b' (appears 4 times):
 * - First occurrence: index 0
 * - Last occurrence: index 5
 * - Characters between [1, 4]: 'b', 'c', 'b', 'a'
 * - Unique characters: {b, c, a} → 3 palindromes
 *   - "bbb" (b_b_b)
 *   - "bcb" (b_c_b)
 *   - "bab" (b_a_b)
 * 
 * Character 'a' (appears 2 times):
 * - First occurrence: index 4
 * - Last occurrence: index 6
 * - Characters between [5]: 'b'
 * - Unique characters: {b} → 1 palindrome
 *   - "aba" (a_b_a)
 * 
 * Character 'c' (appears 1 time):
 * - First = Last → skip
 * 
 * Total: 3 + 1 = 4 unique palindromes ✅
 */

/*
 * WHY WE NEED TO COUNT UNIQUE CHARACTERS?
 * 
 * Consider s = "aabaa"
 * For character 'a':
 * - First: index 0, Last: index 4
 * - Between: 'a', 'b', 'a' (indices 1, 2, 3)
 * 
 * If we count all characters: 3 characters
 * But unique characters: {a, b} → only 2 palindromes
 * - "aaa" (appears multiple times but counted once)
 * - "aba" (appears multiple times but counted once)
 * 
 * This is why we use a Set to track unique middle characters!
 */

/*
 * APPROACH 1: Simple HashSet Solution
 * 
 * Algorithm:
 * 1. Find all unique characters in s (outer characters X)
 * 2. For each unique character X:
 *    a. Find first and last occurrence of X
 *    b. If first == last, skip (need at least 2 occurrences)
 *    c. Use HashSet to collect unique characters between first and last
 *    d. Add set.size() to count (each unique char forms one palindrome)
 * 
 * Time Complexity: O(26 * n) = O(n)
 * - At most 26 unique characters (lowercase English letters)
 * - For each character, scan string once: O(n)
 * 
 * Space Complexity: O(26) = O(1)
 * - HashSet for unique characters (at most 26)
 */

class Solution {
  public int countPalindromicSubsequence(String s) {
    // Step 1: Find all unique characters in the string
    // These will be our outer characters (X in XYX pattern)
    Set<Character> set = new HashSet<>();
    for (char c : s.toCharArray()) {
      set.add(c);
    }

    int n = s.length();
    int count = 0;

    // Step 2: For each unique character, find palindromes with it as outer char
    for (char c : set) {
      int f = -1; // first occurrence of character c
      int l = -1; // last occurrence of character c

      // Find first and last occurrence of character c
      for (int i = 0; i < n; i++) {
        if (s.charAt(i) == c) {
          if (f == -1) {
            f = i; // First occurrence
          }
          l = i; // Keep updating last occurrence
        }
      }

      // If first == last, character appears only once
      // Need at least 2 occurrences to form palindrome XYX
      if (f == l) {
        continue;
      }

      // Step 3: Collect unique characters between first and last occurrence
      // Each unique character forms one unique palindrome
      Set<Character> set2 = new HashSet<>();
      for (int i = f + 1; i < l; i++) {
        set2.add(s.charAt(i));
      }

      // Each unique middle character creates one unique palindrome
      count += set2.size();
    }

    return count;
  }
}

/*
 * APPROACH 2: Optimized Frequency Array with Negative Marking
 * 
 * Key Optimization: Instead of creating new HashSet for each character,
 * use a single frequency array and mark visited characters as negative.
 * 
 * Clever Trick: Negative Marking
 * - freq[i] > 0: character exists and not yet counted for current X
 * - freq[i] < 0: character exists but already counted for current X
 * 
 * After processing each X, flip all negative values back to positive
 * for the next iteration.
 * 
 * Why this works:
 * - Avoids creating O(26) HashSets
 * - Reuses same frequency array
 * - Negative marking prevents double-counting
 * 
 * Time Complexity: O(26 * n) = O(n)
 * Space Complexity: O(1) - only freq[26] array
 */

/*
 * NEGATIVE MARKING EXAMPLE: s = "aabca"
 * 
 * Initial freq: [3, 1, 1, 0, 0, ...] (a=3, b=1, c=1)
 * 
 * Processing 'a':
 * - First: 0, Last: 4
 * - Between indices [1, 3]: 'a', 'b', 'c'
 * 
 * At index 1 (char 'a'):
 * freq[0] = 3 > 0 → mark negative: freq[0] = -3, count++
 * 
 * At index 2 (char 'b'):
 * freq[1] = 1 > 0 → mark negative: freq[1] = -1, count++
 * 
 * At index 3 (char 'c'):
 * freq[2] = 1 > 0 → mark negative: freq[2] = -1, count++
 * 
 * After processing 'a': freq = [-3, -1, -1, 0, ...]
 * Flip back to positive: freq = [3, 1, 1, 0, ...]
 * 
 * This prevents counting the same middle character twice for 'a'!
 */

class Solution2 {
  public int countPalindromicSubsequence(String s) {
    // Frequency array to track character occurrences
    // Also used for negative marking to avoid re-counting
    int[] freq = new int[26]; // O(1) space (constant size 26)
    int count = 0;

    // Build frequency array
    for (char ch : s.toCharArray()) {
      freq[ch - 'a']++;
    }

    // Process each character (a to z)
    for (int i = 0; i < freq.length; i++) { // O(26) iterations
      // Skip if character doesn't exist in string
      if (freq[i] == 0)
        continue;

      // Convert index back to character
      char c = (char) (i + 'a');

      // Find first and last occurrence of character c
      int first = -1;
      int last = -1;
      for (int j = 0; j < s.length(); j++) { // O(n)
        // Overall: O(26 * n) = O(n) since 26 is constant
        char ch = s.charAt(j);
        if (ch == c) {
          if (first == -1) {
            first = j;
          }
          last = j;
        }
      }

      // Skip if character appears only once
      if (first == last)
        continue;

      // Count unique characters between first and last occurrence
      // Use negative marking to track which characters we've counted
      for (int k = first + 1; k < last; k++) {
        char chh = s.charAt(k);
        int ascii = chh - 'a';

        // If freq[ascii] > 0, we haven't counted this character yet
        if (freq[ascii] > 0) {
          // Mark as counted by making it negative
          freq[ascii] *= -1;
          count++;
        }
        // If freq[ascii] < 0, already counted for this outer character
        // If freq[ascii] == 0, character doesn't exist in string
      }

      // Reset all negative values back to positive for next iteration
      // This allows us to reuse the frequency array
      for (int m = 0; m < freq.length; m++) {
        if (freq[m] < 0) {
          freq[m] *= -1;
        }
      }
    }

    return count;
  }
}