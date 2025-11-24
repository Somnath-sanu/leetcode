package subarray;

import java.util.*;

/*
 * You are visiting a farm that has a single row of fruit trees arranged from
 * left to right. The trees are represented by an integer array fruits where
 * fruits[i] is the type of fruit the ith tree produces.
 * 
 * You want to collect as much fruit as possible. However, the owner has some
 * strict rules that you must follow:
 * 
 * You only have two baskets, and each basket can only hold a single type of
 * fruit. There is no limit on the amount of fruit each basket can hold.
 * Starting from any tree of your choice, you must pick exactly one fruit from
 * every tree (including the start tree) while moving to the right. The picked
 * fruits must fit in one of your baskets.
 * Once you reach a tree with fruit that cannot fit in your baskets, you must
 * stop.
 * Given the integer array fruits, return the maximum number of fruits you can
 * pick.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: fruits = [1,2,1]
 * Output: 3
 * Explanation: We can pick from all 3 trees.
 * Example 2:
 * 
 * Input: fruits = [0,1,2,2]
 * Output: 3
 * Explanation: We can pick from trees [1,2,2].
 * If we had started at the first tree, we would only pick from trees [0,1].
 * Example 3:
 * 
 * Input: fruits = [1,2,3,2,2]
 * Output: 4
 * Explanation: We can pick from trees [2,3,2,2].
 * If we had started at the first tree, we would only pick from trees [1,2].
 * 
 * 
 * Constraints:
 * 
 * 1 <= fruits.length <= 105
 * 0 <= fruits[i] < fruits.length
 */

class Solution {
  // Other way to put this question is:
  // Find the longest continuous sub array that has AT MOST 2 distinct elements.
  // NOT exactly 2, but AT MOST 2 (meaning 1 or 2 distinct elements are both
  // valid)

  // IMPORTANT: Why "AT MOST 2" and not "EXACTLY 2"?
  // Because the problem allows picking from trees with 1 or 2 types of fruits.
  // Example: [1,1] has only 1 distinct element, but it's a valid answer (output:
  // 2)
  // If we check for map.size() == 2, we would miss this case and return 0 instead
  // of 2!

  // WHY THIS COMMON MISTAKE FAILS:
  // ❌ if (map.size() == 2) { max = Math.max(max, end - st + 1); }
  // This fails for [1,1] because map.size() is always 1, never 2
  // Expected output: 2, but this would give: 0 or 1

  // ✓ CORRECT: Calculate max OUTSIDE the size check
  // This way we count subarrays with 1 OR 2 distinct elements (at most 2)

  public int totalFruit(int[] fruits) {
    int n = fruits.length;
    HashMap<Integer, Integer> map = new HashMap<>();

    int st = 0;
    int max = 0;

    for (int end = 0; end < n; end++) {
      // expand: add current fruit to the window
      map.put(fruits[end], map.getOrDefault(fruits[end], 0) + 1);

      // shrink: if we have MORE than 2 distinct fruits, remove from left
      while (map.size() > 2) {
        map.put(fruits[st], map.get(fruits[st]) - 1);
        if (map.get(fruits[st]) == 0) {
          map.remove(fruits[st]);
        }
        st++;
      }

      // CRITICAL: Update max for ANY valid window (1 or 2 distinct elements)
      // This must be OUTSIDE the while loop and WITHOUT checking map.size() == 2
      max = Math.max(max, end - st + 1);
    }

    return max;
  }
}
