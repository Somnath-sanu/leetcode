package subarray;

import java.util.*;

/*
 * Given an integer array nums and an integer k, return true if nums has a good
 * subarray or false otherwise.
 * 
 * A good subarray is a subarray where:
 * 
 * its length is at least two, and
 * the sum of the elements of the subarray is a multiple of k.
 * Note that:
 * 
 * A subarray is a contiguous part of the array.
 * An integer x is a multiple of k if there exists an integer n such that x = n
 * * k. 0 is always a multiple of k.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [23,2,4,6,7], k = 6
 * Output: true
 * Explanation: [2, 4] is a continuous subarray of size 2 whose elements sum up
 * to 6.
 */

class Solution {
  public boolean checkSubarraySum(int[] nums, int k) {
    int n = nums.length;
    HashMap<Integer, Integer> map = new HashMap<>();
    // remainder -> index

    map.put(0, -1); // if k starts from beginning
    // [23,2,4] k=25

    int sum = 0;

    for (int i = 0; i < n; i++) {
      sum += nums[i];

      int rem = sum % k;

      if (map.containsKey(rem)) {
        // check whether size is 2 or not
        int size = i - map.get(rem);
        if (size >= 2) {
          return true;
        }
      }

      // map.put(rem,i);

      // 👆 this will overwrite the index if
      // same rem come again
      // we have to store first occurance only

      else {
        map.put(rem, i);
      }
    }

    return false;
  }

  /*
   * Constraints:
   * 
   * 1 <= nums.length <= 10^5
   * 0 <= nums[i] <= 10^9
   * 0 <= sum(nums[i]) <= 2^31 - 1
   * 1 <= k <= 2^31 - 1
   */

  // One approach you could think of but it will give MLE

  public boolean checkSubarraySumMLE(int[] nums, int k) {
    int n = nums.length;
    // since we are storing the remainder
    // that will never exceed k
    // only safe when k is small (dont overflow int range), positive (so that we
    // dont get -ve index error).

    // In this problem
    // Memory Limit Exceeded Error

    int[] map = new int[k];
    // remainder -> index

    Arrays.fill(map, -2); // // so that value -1 remains unused

    map[0] = -1; // if k starts from beginning
    // [23,2,4] k=25

    int sum = 0;

    for (int i = 0; i < n; i++) {
      sum += nums[i];

      int rem = sum % k;

      if (rem < 0) {
        rem = (rem % k) + k;
      }

      if (map[rem] != -2) {
        // check whether size is 2 or not
        int size = i - map[rem];
        if (size >= 2) {
          return true;
        }
      }

      // map.put(rem,i);

      // 👆 this will overwrite the index if
      // same rem come again
      // we have to store first occurance only

      else {
        map[rem] = i;
      }
    }

    return false;
  }
}
