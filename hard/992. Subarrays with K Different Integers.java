package hard;

import java.util.*;

/*
 * Given an integer array nums and an integer k, return the number of good
 * subarrays of nums.
 * 
 * A good array is an array where the number of different integers in that array
 * is exactly k.
 * 
 * For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
 * A subarray is a contiguous part of an array.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2],
 * [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 * Example 2:
 * 
 * Input: nums = [1,2,1,3,4], k = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3],
 * [2,1,3], [1,3,4].
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 2 * 104
 * 1 <= nums[i], k <= nums.length
 */



/**
 * This problem is a variation of 904. Fruit Into Baskets
 */

/**
 * In This problem Its difficult to decide whether to shrink the window or
 * expand the window
 * No matter what we do we will miss out on some subarrays
 * 
 * To solve such problems
 * Don't try to find out exactly K different integers
 * Try to find out atmost K different integers because atmost K is easier to
 * find
 * and atmost K - 1 is also easier to find
 * 
 * atmost K - atmost K - 1 = exactly K
 * 
 * Example: nums = [1,2,1,2,3], k = 2
 * index: 0 1 2 3 4
 * Expected answer = 7 subarrays with exactly 2 distinct integers
 * 
 * ============================================================================
 * STEP 1: Calculate atMost(2) - subarrays with AT MOST 2 distinct integers
 * ============================================================================
 * Key: At each position i, count = (i - left + 1) gives number of valid
 * subarrays ENDING at i
 * 
 * i=0, nums[0]=1, window=[1], distinct={1}, left=0
 * count += (0-0+1) = 1 → subarrays: [1]
 * Total = 1
 * 
 * i=1, nums[1]=2, window=[1,2], distinct={1,2}, left=0
 * count += (1-0+1) = 2 → subarrays: [2], [1,2] (ENDING at i)
 * Total = 3
 * 
 * i=2, nums[2]=1, window=[1,2,1], distinct={1,2}, left=0
 * count += (2-0+1) = 3 → subarrays: [1], [2,1], [1,2,1]
 * Total = 6
 * 
 * i=3, nums[3]=2, window=[1,2,1,2], distinct={1,2}, left=0
 * count += (3-0+1) = 4 → subarrays: [2], [1,2], [2,1,2], [1,2,1,2]
 * Total = 10
 * 
 * i=4, nums[4]=3, window=[1,2,1,2,3], distinct={1,2,3} → TOO MANY! Shrink
 * window
 * Remove nums[0]=1, window=[2,1,2,3], distinct={1,2,3} → still 3, shrink more
 * Remove nums[1]=2, window=[1,2,3], distinct={1,2,3} → still 3, shrink more
 * Remove nums[2]=1, window=[2,3], distinct={2,3}, left=3 → NOW OK (2 distinct)
 * count += (4-3+1) = 2 → subarrays: [3], [2,3]
 * Total = 12
 * 
 * atMost(2) = 12
 * 
 * ============================================================================
 * STEP 2: Calculate atMost(1) - subarrays with AT MOST 1 distinct integer
 * ============================================================================
 * 
 * i=0, nums[0]=1, window=[1], distinct={1}, left=0
 * count += (0-0+1) = 1 → subarrays: [1]
 * Total = 1
 * 
 * i=1, nums[1]=2, window=[1,2], distinct={1,2} → TOO MANY! Shrink window
 * Remove nums[0]=1, window=[2], distinct={2}, left=1 → NOW OK (1 distinct)
 * count += (1-1+1) = 1 → subarrays: [2]
 * Total = 2
 * 
 * i=2, nums[2]=1, window=[2,1], distinct={1,2} → TOO MANY! Shrink window
 * Remove nums[1]=2, window=[1], distinct={1}, left=2 → NOW OK (1 distinct)
 * count += (2-2+1) = 1 → subarrays: [1]
 * Total = 3
 * 
 * i=3, nums[3]=2, window=[1,2], distinct={1,2} → TOO MANY! Shrink window
 * Remove nums[2]=1, window=[2], distinct={2}, left=3 → NOW OK (1 distinct)
 * count += (3-3+1) = 1 → subarrays: [2]
 * Total = 4
 * 
 * i=4, nums[4]=3, window=[2,3], distinct={2,3} → TOO MANY! Shrink window
 * Remove nums[3]=2, window=[3], distinct={3}, left=4 → NOW OK (1 distinct)
 * count += (4-4+1) = 1 → subarrays: [3]
 * Total = 5
 * 
 * atMost(1) = 5
 * 
 * ============================================================================
 * STEP 3: Calculate exactly K = atMost(2) - atMost(1)
 * ============================================================================
 * 
 * atMost(2) counts: [1], [2], [1], [2], [3], [1,2], [2,1], [1,2], [2,3],
 * [1,2,1], [2,1,2], [1,2,1,2] (12 subarrays)
 * 
 * atMost(1) counts: [1], [2], [1], [2], [3] (5 subarrays - all single elements)
 * 
 * Exactly 2 = atMost(2) - atMost(1) = 12 - 5 = 7
 * 
 * These 7 subarrays are: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2],
 * [1,2,1,2]
 * 
 * ✓ This matches the expected answer!
 * 
 * WHY THIS WORKS:
 * - atMost(2) includes subarrays with 1 OR 2 distinct integers
 * - atMost(1) includes subarrays with only 1 distinct integer
 * - Subtracting removes all single-distinct subarrays, leaving EXACTLY 2
 * distinct
 * 
 */

class Solution {
  public int subarraysWithKDistinct(int[] nums, int k) {
    return atMost(nums, k) - atMost(nums, k - 1);
  }

  public int atMost(int[] nums, int k) {
    int n = nums.length;
    int count = 0;
    int st = 0;
    HashMap<Integer, Integer> map = new HashMap<>();

    for (int end = 0; end < n; end++) {
      int val = nums[end];
      map.put(val, map.getOrDefault(val, 0) + 1);

      while (map.size() > k) {
        int ele = nums[st];
        map.put(ele, map.get(ele) - 1);
        if (map.get(ele) == 0) {
          map.remove(ele);
        }
        st++;

      }

      count += (end - st + 1);
    }

    return count;
  }
}