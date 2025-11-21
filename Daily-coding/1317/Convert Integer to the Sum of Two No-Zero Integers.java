/**
 * No-Zero integer is a positive integer that does not contain any 0 in its
 * decimal representation.
 * 
 * Given an integer n, return a list of two integers [a, b] where:
 * 
 * a and b are No-Zero integers.
 * a + b = n
 * The test cases are generated so that there is at least one valid solution. If
 * there are many valid solutions, you can return any of them.
 * 
 * Example 1:
 * 
 * Input: n = 2
 * Output: [1,1]
 * Explanation: Let a = 1 and b = 1.
 * Both a and b are no-zero integers, and a + b = 2 = n.
 * Example 2:
 * 
 * Input: n = 11
 * Output: [2,9]
 * Explanation: Let a = 2 and b = 9.
 * Both a and b are no-zero integers, and a + b = 11 = n.
 * Note that there are other valid answers as [8, 3] that can be accepted.
 */

class Solution {
  public int[] getNoZeroIntegers(int n) {
    /**
     * While loop runtime is less 37%
     * for loop gives 100%
     */
    for (int num = 1; num < n; num++) {
      int s_num = n - num;
      String s1 = String.valueOf(s_num);
      String s2 = String.valueOf(num);
      // if (!s1.contains("0") && !s2.contains("0")) {
      // return new int[]{num,s_num};
      // }
      if (!solve(num) && !solve(n - num)) {
        return new int[] { num, s_num };
      }
      num++;
    }
    return new int[0];
  }

  public boolean solve(int x) {
    while (x > 0) {
      if (x % 10 == 0)
        return true;
      x /= 10;
    }
    return false;
  }
}