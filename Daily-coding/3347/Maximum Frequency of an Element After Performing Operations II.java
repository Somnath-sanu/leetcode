import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
 * Adding 0 to nums[1], after which nums becomes [1, 4, 5].
 * Adding -1 to nums[2], after which nums becomes [1, 4, 4].
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
 * 1 <= nums[i] <= 109
 * 0 <= k <= 109
 * 0 <= numOperations <= nums.length
 */

class Solution {
  public int maxFrequency(int[] nums, int k, int numOperations) {
    // T.C : O(nlogn)
    // S.C : O(n)

    int maxVal = Arrays.stream(nums).max().getAsInt() + k;

    TreeMap<Integer, Integer> diff = new TreeMap<>();
    HashMap<Integer, Integer> freq = new HashMap<>();

    // O(n log n)
    for (int num : nums) {
      freq.put(num, freq.getOrDefault(num, 0) + 1);

      int l = Math.max(num - k, 0);
      int r = num + k; // Math.min(num + k, maxVal);

      diff.put(l, diff.getOrDefault(l, 0) + 1);
      diff.put(r + 1, diff.getOrDefault(r + 1, 0) - 1);

      // Ensure key exists for later iteration
      diff.putIfAbsent(num, diff.getOrDefault(num, 0));
    }

    int result = 1;
    int cumSum = 0;

    // O(n)

    for (Map.Entry<Integer, Integer> entry : diff.entrySet()) {
      int target = entry.getKey();
      int val = entry.getValue();

      cumSum += val;
      int targetFreq = freq.getOrDefault(target, 0);
      int needConversion = cumSum - targetFreq;

      int maxPossibleFreq = Math.min(needConversion, numOperations);
      result = Math.max(result, targetFreq + maxPossibleFreq);
    }

    return result;
  }
}