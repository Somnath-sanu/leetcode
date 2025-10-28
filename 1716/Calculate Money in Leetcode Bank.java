/**
 * Hercy wants to save money for his first car. He puts money in the Leetcode
 * bank every day.
 * 
 * He starts by putting in $1 on Monday, the first day. Every day from Tuesday
 * to Sunday, he will put in $1 more than the day before. On every subsequent
 * Monday, he will put in $1 more than the previous Monday.
 * 
 * Given n, return the total amount of money he will have in the Leetcode bank
 * at the end of the nth day.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: n = 4
 * Output: 10
 * Explanation: After the 4th day, the total is 1 + 2 + 3 + 4 = 10.
 * Example 2:
 * 
 * Input: n = 10
 * Output: 37
 * Explanation: After the 10th day, the total is (1 + 2 + 3 + 4 + 5 + 6 + 7) +
 * (2 + 3 + 4) = 37. Notice that on the 2nd Monday, Hercy only puts in $2.
 */

class Solution {
  public int totalMoney(int n) {
    //* */ Time complexity: O(n)
    //* */ Space complexity: O(1)

    // int currStartAmount = 1;

    // int totalMoney = 0;
    // int curr = currStartAmount;
    // int daysCount = 0;

    // for (int i=1; i<=n; i++) {
    // totalMoney += curr;
    // curr++;
    // daysCount++;
    // if (daysCount == 7) {
    // daysCount = 0;
    // currStartAmount++;
    // curr = currStartAmount;
    // }
    // }

    // return totalMoney;

    // ------------------------------------------------------

    //* */ Time complexity: O(1)
    //* */ Space complexity: O(1)

    int totalWeeks = n / 7; // 1 week = 7 days thats wy dividing / 7
    int remainingWeeks = n % 7;

    int firstTerm = 28;
    int lastTerm = firstTerm + (totalWeeks - 1) * 7;

    int sumOfCompleteWeeks = totalWeeks * (firstTerm + lastTerm) / 2;

    int sumOfRemainingWeeks = 0;

    int mondayCost = totalWeeks + 1; // starting 1; then each day +1

    for (int i = 1; i <= remainingWeeks; i++) { // max(O(7))
      sumOfRemainingWeeks += mondayCost;
      mondayCost++;
    }

    return sumOfCompleteWeeks + sumOfRemainingWeeks;
  }
}