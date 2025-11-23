package dp;

import java.util.*;

/*
 * Given an integer array nums, return the maximum possible sum of elements of
 * the array such that it is divisible by three.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,6,5,1,8]
 * Output: 18
 * Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum
 * divisible by 3).
 * Example 2:
 * 
 * Input: nums = [4]
 * Output: 0
 * Explanation: Since 4 is not divisible by 3, do not pick any number.
 */

class Solution {
  public int maxSumDivThree(int[] nums) {

    // Time complexity: O(n log n)
    // Space complexity: O(n)

    int sum = 0;
    List<Integer> r1 = new ArrayList<>();
    List<Integer> r2 = new ArrayList<>();

    for (int num : nums) {
      sum += num;

      if (num % 3 == 1) {
        r1.add(num);
      } else if (num % 3 == 2) {
        r2.add(num);
      }
    }

    if (sum % 3 == 0) {
      return sum;
    }

    Collections.sort(r1);
    r2.sort((a, b) -> a - b);

    int result = 0;

    if (sum % 3 == 1) {
      int remove1 = r1.size() >= 1 ? r1.get(0) : Integer.MAX_VALUE;
      int remove2 = r2.size() >= 2 ? r2.get(0) + r2.get(1) : Integer.MAX_VALUE;
      result = sum - Math.min(remove1, remove2);
    } else { // sum % 3 == 2
      int remove1 = r2.size() >= 1 ? r2.get(0) : Integer.MAX_VALUE;
      int remove2 = r1.size() >= 2 ? r1.get(0) + r1.get(1) : Integer.MAX_VALUE;
      result = sum - Math.min(remove1, remove2);
    }

    return Math.max(result, 0);
    /*
     * Returning result will also works, but
     * if remove1 and remove2 both gives Integer.MAX_VALUE then result would be -ve
     * so, best practice
     */

  }

  public int maxSumDivThreeDP(int[] nums) {
    // T.C = O(n)
    // S.C = O(n)

    // Memoization
    // 2-d array ( index , remainder)
    int[][] dp = new int[nums.length][3];
    for (int[] d : dp) {
      Arrays.fill(d, -1);
    }

    return solve(0, 0, nums, dp);
  }

  private int solve(int i, int r, int[] nums, int[][] dp) {
    // base case
    if (i >= nums.length) {
      return (r == 0) ? 0 : Integer.MIN_VALUE;
    }

    if (dp[i][r] != -1) {
      return dp[i][r];
    }

    int pick = nums[i] + solve(i + 1, (r + nums[i]) % 3, nums, dp);
    int noPick = 0 + solve(i + 1, r, nums, dp);

    return dp[i][r] = Math.max(pick, noPick);
  }
}
