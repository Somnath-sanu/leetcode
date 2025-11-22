package array;

import java.util.*;

/*
 * Given an array of strings strs, group the anagrams together. You can return
 * the answer in any order.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * 
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 
 * Explanation:
 * 
 * There is no string in strs that can be rearranged to form "bat".
 * The strings "nat" and "tan" are anagrams as they can be rearranged to form
 * each other.
 * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to
 * form each other.
 */

class Solution {
  public List<List<String>> groupAnagrams(String[] strs) {
    // Time complexity: O(n * k)
    // Space complexity: O(n * k)
    Map<String, List<String>> map = new HashMap<>();

    for (String s : strs) { // O(n)
      // char[] c = s.toCharArray();
      // Arrays.sort(c); // n log(n)
      // String newStr = new String(c);

      int[] count = new int[26];
      for (char c : s.toCharArray()) { // O(k)
        count[c - 'a']++;
      }
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < 26; i++) { // O(1)
        if (count[i] != 0) {
          sb.append((char) i).append(count[i]);
        }
      }
      String newStr = sb.toString();

      if (!map.containsKey(newStr)) {
        map.put(newStr, new ArrayList<>());
      }

      map.get(newStr).add(s);
    }

    return new ArrayList<>(map.values());
  }
}

// ["ddddddddddg","dgggggggggg"]
// key -> "dg" , "dg"
// so -> "d10g1" , d1g10 -> now seperate keys
