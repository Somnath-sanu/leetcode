/**
 * You are given a 0-indexed integer array nums, where nums[i] is a digit
 * between 0 and 9 (inclusive).
 * 
 * The triangular sum of nums is the value of the only element present in nums
 * after the following process terminates:
 * 
 * Let nums comprise of n elements. If n == 1, end the process. Otherwise,
 * create a new 0-indexed integer array newNums of length n - 1.
 * 
 * For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as
 * (nums[i] + nums[i+1]) % 10, where % denotes modulo operator.
 * 
 * Replace the array nums with newNums.
 * Repeat the entire process starting from step 1.
 * Return the triangular sum of nums.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: nums = [1,2,3,4,5]
 * Output: 8
 * Explanation:
 * The above diagram depicts the process from which we obtain the triangular sum
 * of the array.
 * Example 2:
 * 
 * Input: nums = [5]
 * Output: 5
 * Explanation:
 * Since there is only one element in nums, the triangular sum is the value of
 * that element itself.
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 9
 */

class Solution {
  public int triangularSum(int[] nums) {
    // simulation
    // T.C -> O(n^2)
    // S.C -> O(n^2)

    // int n = nums.length;
    // if (n == 1) {
    // return nums[0];
    // }

    // int[] newNum = new int[n-1];
    // for (int i=0; i<n-1; i++) {
    // newNum[i] = (nums[i] + nums[i+1]) % 10;
    // }

    // int ans = triangularSum(newNum);
    // return ans;

    // -----------------------
    // T.C : O(n^2)
    // S.C : O(1)

    int n = nums.length;
    for (int size = n - 1; size >= 1; size--) {
      for (int i = 0; i < size; i++) {
        nums[i] = (nums[i] + nums[i + 1]) % 10;
      }
    }
    return nums[0];
  }
}