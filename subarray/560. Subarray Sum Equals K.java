package subarray;

import java.util.*;

/*
 * Given an array of integers nums and an integer k, return the total number of
 * subarrays whose sum equals to k.
 * 
 * A subarray is a contiguous non-empty sequence of elements within an array.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 * Example 2:
 * 
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 */

class Solution {
  public int subarraySum(int[] nums, int k) {
    // HashSet<Integer> set = new HashSet<>();

    // set.add(0);

    // int sum = 0;
    // int count = 0;

    // for(int i=0; i<nums.length; i++) {
    // sum += nums[i];

    // int t = sum - k;

    // if (set.contains(t)) {
    // count++;
    // }

    // set.add(sum);
    // }

    // return count;

    // 👆 the above code fails for the testcase
    // [1,-1,0]
    // We can use above code if we are asked to find subarray with sum = k, return true if present else false

    

    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // sum -> count

    int sum = 0;
    int count = 0;

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];

      int t = sum - k;

      if (map.containsKey(t)) {
        count += map.get(t);
      }

      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
  }
}