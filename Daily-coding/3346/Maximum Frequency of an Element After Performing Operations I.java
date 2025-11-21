import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * You are given an integer array nums and two integers k and numOperations.
 * 
 * You must perform an operation numOperations times on nums, where in each
 * operation you:
 * 
 * Select an index i that was not selected in any previous operations.
 * Add an integer in the range [-k, k] to nums[i].
 * Return the maximum possible frequency of any element in nums after performing
 * the operations.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,4,5], k = 1, numOperations = 2
 * 
 * Output: 2
 * 
 * Explanation:
 * 
 * We can achieve a maximum frequency of two by:
 * 
 * Adding 0 to nums[1]. nums becomes [1, 4, 5].
 * Adding -1 to nums[2]. nums becomes [1, 4, 4].
 * Example 2:
 * 
 * Input: nums = [5,11,20,20], k = 5, numOperations = 1
 * 
 * Output: 2
 * 
 * Explanation:
 * 
 * We can achieve a maximum frequency of two by:
 * 
 * Adding 0 to nums[1].
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 105
 * 0 <= k <= 105
 * 0 <= numOperations <= nums.length
 */

class Solution {
  public int maxFrequency(int[] nums, int k, int numOperations) {

    // prefix sum approach

    // int maxEl = Arrays.stream(nums).max().getAsInt() + k;

    // int[] freq = new int[maxEl + 1];

    // for(int num : nums) {
    // freq[num]++;
    // }

    // cumulative/prefix sum of frequencies

    // for(int i=1; i<= maxEl; i++) {
    // freq[i] = freq[i] + freq[i-1];
    // }

    // int res = 0;

    // IF I Use here num : nums , failing this [84,57] , k = 24
    // because some element that is not is array must have higher
    // frequency
    //

    // for (int target = 0; target <= maxEl; target++) {

    // if (freq[target] == 0) {
    // continue;
    // }

    // int l = Math.max(0, target-k);
    // int r = Math.min(maxEl, target+k);

    // totalCount of elements tht will lie in range (l,r);

    // int totalCount = freq[r] - (l-1 < 0 ? 0 : freq[l-1]);
    // int targetCount = freq[target] - (target-1 < 0 ? 0 : freq[target-1]);

    // int needConversion = totalCount - targetCount;

    // int possibleConversion = Math.min(needConversion, numOperations);

    // int maxPossibleFreq = targetCount + possibleConversion;

    // res = Math.max(res , maxPossibleFreq);
    // }

    // return res;

    // difference array approach

    int maxElement = Arrays.stream(nums).max().getAsInt();

    int maxVal = maxElement + k;

    int[] diff = new int[maxVal + 2]; // so that I can use diff[maxVal+1]

    Map<Integer, Integer> freq = new HashMap<>();

    for (int num : nums) {
      freq.put(num, freq.getOrDefault(num, 0) + 1);

      int l = Math.max(0, num - k);
      int r = num + k; // Math.min(num + k, maxVal);

      diff[l]++;
      diff[r + 1]--;
    }

    int result = 1; // min freq will be 1

    for (int target = 1; target <= maxVal; target++) {
      diff[target] += diff[target - 1];

      int targetFreq = freq.getOrDefault(target, 0);
      // diff[target] directly tells how many element lie in range
      int needConversion = diff[target] - targetFreq;

      int possibleConversion = Math.min(needConversion, numOperations);

      int maxPossibleFreq = targetFreq + possibleConversion;

      result = Math.max(result, maxPossibleFreq);
    }

    return result;
  }
}