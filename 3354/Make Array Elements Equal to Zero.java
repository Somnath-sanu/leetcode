import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * You are given an integer array nums.
 * 
 * Start by selecting a starting position curr such that nums[curr] == 0, and
 * choose a movement direction of either left or right.
 * 
 * After that, you repeat the following process:
 * 
 * If curr is out of the range [0, n - 1], this process ends.
 * If nums[curr] == 0, move in the current direction by incrementing curr if you
 * are moving right, or decrementing curr if you are moving left.
 * Else if nums[curr] > 0:
 * Decrement nums[curr] by 1.
 * Reverse your movement direction (left becomes right and vice versa).
 * Take a step in your new direction.
 * A selection of the initial position curr and movement direction is considered
 * valid if every element in nums becomes 0 by the end of the process.
 * 
 * Return the number of possible valid selections.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,0,2,0,3]
 * 
 * Output: 2
 * 
 * Explanation:
 * 
 * The only possible valid selections are the following:
 * 
 * Choose curr = 3, and a movement direction to the left.
 * [1,0,2,0,3] -> [1,0,2,0,3] -> [1,0,1,0,3] -> [1,0,1,0,3] -> [1,0,1,0,2] ->
 * [1,0,1,0,2] -> [1,0,0,0,2] -> [1,0,0,0,2] -> [1,0,0,0,1] -> [1,0,0,0,1] ->
 * [1,0,0,0,1] -> [1,0,0,0,1] -> [0,0,0,0,1] -> [0,0,0,0,1] -> [0,0,0,0,1] ->
 * [0,0,0,0,1] -> [0,0,0,0,0].
 * Choose curr = 3, and a movement direction to the right.
 * [1,0,2,0,3] -> [1,0,2,0,3] -> [1,0,2,0,2] -> [1,0,2,0,2] -> [1,0,1,0,2] ->
 * [1,0,1,0,2] -> [1,0,1,0,1] -> [1,0,1,0,1] -> [1,0,0,0,1] -> [1,0,0,0,1] ->
 * [1,0,0,0,0] -> [1,0,0,0,0] -> [1,0,0,0,0] -> [1,0,0,0,0] -> [0,0,0,0,0].
 */

class Solution {
  public int countValidSelections(int[] nums) {

    // T.C -> O(n^2)
    // S.C -> O(n)

    int n = nums.length;
    List<Integer> idx = new ArrayList<>();
    for(int i=0; i<n; i++) {
    if (nums[i] == 0) {
    idx.add(i);
    }
    }

    int ans = 0;
    int dirCount = 2;

    for (int index : idx) {
    int mainIndex = index;
    while (dirCount > 0) {
    int[] temp = Arrays.copyOf(nums , n);
    int dir = dirCount == 2 ? 1 : -1; // 1 -> left; -1 -> right
    while (index >= 0 && index < n) {

    if (temp[index] > 0) {
    temp[index]--;
    dir *= -1;
    }
    index = (dir == 1) ? index - 1 : index + 1;

    }

    if (isAllZero(temp)) {
    ans++;
    }
    dirCount--;
    index = mainIndex; // restoring index
    }
    dirCount = 2; // restoring dir for new index
    }

    return ans;

    // -------------------------------------
    // Comulative Sum
    // T.C -> O(n)
    // S.C -> O(1)

    /**
     * 1. if at index i num[i] == 0; then if
     * left sum of i and right sum of i is same then
     * result += 2; // 2 becz whether we go right or left ans will come
     * 
     * 2. if absolute diff of left sum and right sum is 1 then also ans
     * will come but in one dir... it could either be left or right
     * so result += 1;
     */

    int n = nums.length;
    int res = 0;
    int currSum = 0;

    int S = 0; // Total Sum

    for (int num : nums) { // O(n)
      S += num;
    }

    for (int i = 0; i < n; i++) { // O(n)
      currSum += nums[i];

      int left = currSum;
      int right = S - currSum;

      if (nums[i] > 0) {
        continue;
      }

      if (left == right) {
        res += 2;
      } else if (Math.abs(left - right) == 1) {
        res += 1;
      }
    }

    return res;
  }

  private boolean isAllZero(int[] temp) {
    boolean bool = true;
    for (int num : temp) {
      if (num > 0) {
        bool = false;
        break;
      }
    }

    return bool;
  }
}