/**
 * You are given a 0-indexed integer array nums of even length consisting of an
 * equal number of positive and negative integers.
 * 
 * You should return the array of nums such that the array follows the given
 * conditions:
 * 
 * Every consecutive pair of integers have opposite signs.
 * For all integers with the same sign, the order in which they were present in
 * nums is preserved.
 * The rearranged array begins with a positive integer.
 * Return the modified array after rearranging the elements to satisfy the
 * aforementioned conditions.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,1,-2,-5,2,-4]
 * Output: [3,-2,1,-5,2,-4]
 * Explanation:
 * The positive integers in nums are [3,1,2]. The negative integers are
 * [-2,-5,-4].
 * The only possible way to rearrange them such that they satisfy all conditions
 * is [3,-2,1,-5,2,-4].
 * Other ways such as [1,-2,2,-5,3,-4], [3,1,2,-2,-5,-4], [-2,3,-5,1,-4,2] are
 * incorrect because they do not satisfy one or more conditions.
 */

class Solution {
  public int[] rearrangeArray(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];

    int odd_idx = 1; // -ve numbers
    int even_idx = 0; // +ve numbers

    for (int num : nums) {
      if (num < 0) { // -ve
        res[odd_idx] = num;
        odd_idx += 2;
      } else { // +ve
        res[even_idx] = num;
        even_idx += 2;
      }
    }

    return res;
  }
}