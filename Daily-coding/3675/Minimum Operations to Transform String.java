/**
 * You are given a string s consisting only of lowercase English letters.
 * 
 * You can perform the following operation any number of times (including zero):
 * 
 * Choose any character c in the string and replace every occurrence of c with
 * the next lowercase letter in the English alphabet.
 * 
 * Return the minimum number of operations required to transform s into a string
 * consisting of only 'a' characters.
 * 
 * Note: Consider the alphabet as circular, thus 'a' comes after 'z'.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "yz"
 * 
 * Output: 2
 * 
 * Explanation:
 * 
 * Change 'y' to 'z' to get "zz".
 * Change 'z' to 'a' to get "aa".
 * Thus, the answer is 2.
 * Example 2:
 * 
 * Input: s = "a"
 * 
 * Output: 0
 * 
 * Explanation:
 * 
 * The string "a" only consists of 'a'​​​​​​​ characters. Thus, the answer is 0.
 */

class Solution {
  // cd -> c -> d -> 1 operation
  // then dd -> then same numbrr of operation for both
  // so max here is c

  public int minOperations(String s) {
    int ans = 0;

    for (char c : s.toCharArray()) {
      // idx
      int idx = c - 'a'; // 0 to 25

      // edge case idx == 0; thats why mod with 26

      int forwardDist = (26 - idx) % 26;

      ans = Math.max(ans, forwardDist);
    }

    return ans;
  }
}