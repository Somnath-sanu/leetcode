
/**
 * An integer x is numerically balanced if for every digit d in the number x,
 * there are exactly d occurrences of that digit in x.
 * 
 * Given an integer n, return the smallest numerically balanced number strictly
 * greater than n.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: n = 1
 * Output: 22
 * Explanation:
 * 22 is numerically balanced since:
 * - The digit 2 occurs 2 times.
 * It is also the smallest numerically balanced number strictly greater than 1.
 * Example 2:
 * 
 * Input: n = 1000
 * Output: 1333
 * Explanation:
 * 1333 is numerically balanced since:
 * - The digit 1 occurs 1 time.
 * - The digit 3 occurs 3 times.
 * It is also the smallest numerically balanced number strictly greater than
 * 1000.
 * Note that 1022 cannot be the answer because 0 appeared more than 0 times.
 * Example 3:
 * 
 * Input: n = 3000
 * Output: 3133
 * Explanation:
 * 3133 is numerically balanced since:
 * - The digit 1 occurs 1 time.
 * - The digit 3 occurs 3 times.
 * It is also the smallest numerically balanced number strictly greater than
 * 3000.
 * 
 * 
 * Constraints:
 * 
 * 0 <= n <= 106
 * 
 * 
 */

class Solution {

  int[] balancedList = {
      1, 22, 122, 212, 221, 333, 1333, 3133, 3313,
      3331, 4444, 14444, 22333, 23233, 23323, 23332, 32233, 32323,
      32332, 33223, 33232, 33322, 41444, 44144, 44414, 44441, 55555,
      122333, 123233, 123323, 123332, 132233, 132323, 132332, 133223,
      133232, 133322, 155555, 212333, 213233, 213323, 213332, 221333,
      223133, 223313, 223331, 224444, 231233, 231323, 231332, 232133,
      232313, 232331, 233123, 233132, 233213, 233231, 233312, 233321,
      242444, 244244, 244424, 244442, 312233, 312323, 312332, 313223,
      313232, 313322, 321233, 321323, 321332, 322133, 322313, 322331,
      323123, 323132, 323213, 323231, 323312, 323321, 331223, 331232,
      331322, 332123, 332132, 332213, 332231, 332312, 332321, 333122,
      333212, 333221, 422444, 424244, 424424, 424442, 442244, 442424,
      442442, 444224, 444242, 444422, 515555, 551555, 555155, 555515,
      555551, 666666, 1224444
  };

  public int nextBeautifulNumber(int n) {
    // 0 <= n <= 10^6
    // next balanced number after 10^6 is 1224444

    // for (int num = n + 1; num <= 1224444; num++) {
    // if (isBalanced(num)) {
    // return num;
    // }
    // }

    // return -1;

    // Approach-2 (Binary search on preciomputed list of balanced numbers)
    // T.C : O(1)
    // S.C : O(1)

    // int idx = Arrays.binarySearch(balancedList, n+1); // n+1 (next balanced)
    // if (idx < 0) idx = -idx - 1;
    // return balancedList[idx];

    // for (int num : balancedList) {
    // if (num > n) {
    // return num;
    // }
    // }

    // return -1;

    // Approach-3 (Bakctracking)
    // T.C : O(9^d), d = number of digits of n
    // S.C : O(1)

    int numDigits = String.valueOf(n).length();

    int res = backtrack(n, 0, numDigits); // first try to find in same length

    if (res == 0) {
      res = backtrack(n, 0, numDigits + 1); // then in next length
    }

    return res;

  }

  public boolean isBalanced(int num) {
    // digit and freq should match
    int[] freq = new int[10];

    while (num > 0) {
      int digit = num % 10;
      freq[digit]++;
      num /= 10;
    }

    for (int digit = 0; digit <= 9; digit++) {
      if (freq[digit] > 0 && digit != freq[digit]) {
        return false;
      }
    }

    return true;
  }

  private int[] digitCount = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

  public int backtrack(int n, int res, int count) {
    // base case
    if (count == 0) {
      // 1. check if balanced or not
      for (int d = 1; d <= 9; d++) {
        if (digitCount[d] != 0 && digitCount[d] != d) {
          return 0;
        }
      }

      // 2. check if greater than n or not (strictly greater)
      return res > n ? res : 0;
    }

    int result = 0;

    for (int d = 1; d <= 9; d++) {
      // digitCount[d] <= count becz if I choose digit 3 I have to
      // take it 3 times
      if (digitCount[d] > 0 && digitCount[d] <= count) {
        digitCount[d]--;
        result = backtrack(n, res * 10 + d, count - 1); // count-- = count = count - 1; give wrong ans
        digitCount[d]++;
      }

      if (result != 0)
        break;
    }

    return result;
  }
}