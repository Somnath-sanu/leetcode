package subarray;

import java.util.*;

/*
 * Given an unsorted array of integers nums, return the length of the longest
 * consecutive elements sequence.
 * 
 * You must write an algorithm that runs in O(n) time.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
 * Therefore its length is 4.
 * Example 2:
 * 
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * Example 3:
 * 
 * Input: nums = [1,0,1,2]
 * Output: 3
 * 
 * 
 * Constraints:
 * 
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */

class Solution {
  public int longestConsecutive(int[] nums) {

    if (nums.length == 0) {
      return 0;
    }
    // Set code gives TLE

    // HashSet<Integer> set = new HashSet<>();
    // for (int num : nums) {
    // set.add(num);
    // }
    // int max = 0;
    // for (int i = 0; i < nums.length; i++) {
    // int st = nums[i];
    // int count = 1;

    // if (!set.contains(st - 1)) {
    // while (set.contains(st + 1)) {
    // count++;
    // st = st + 1;

    // }
    // }
    // max = Math.max(max, count);
    // }

    // return max;

    // ---------------
    Arrays.sort(nums);

    // [1,2,3,4,100,200];

    int prev = nums[0];
    int count = 1;
    int max = 1;

    for (int i = 1; i < nums.length; i++) {
      if (nums[i] == prev)
        continue; // skipping same digits
      if (nums[i] == prev + 1) {
        count++;
        max = Math.max(max, count);
      }

      else {
        count = 1;
      }
      prev = nums[i];
    }

    return max;

  }
}
