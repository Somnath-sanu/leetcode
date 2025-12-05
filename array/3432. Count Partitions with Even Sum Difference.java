package array;

/*
 * You are given an integer array nums of length n.
 * 
 * A partition is defined as an index i where 0 <= i < n - 1, splitting the
 * array into two non-empty subarrays such that:
 * 
 * Left subarray contains indices [0, i].
 * Right subarray contains indices [i + 1, n - 1].
 * Return the number of partitions where the difference between the sum of the
 * left and right subarrays is even.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [10,10,3,7,6]
 * 
 * Output: 4
 * 
 * Explanation:
 * 
 * The 4 partitions are:
 * 
 * [10], [10, 3, 7, 6] with a sum difference of 10 - 26 = -16, which is even.
 * [10, 10], [3, 7, 6] with a sum difference of 20 - 16 = 4, which is even.
 * [10, 10, 3], [7, 6] with a sum difference of 23 - 13 = 10, which is even.
 * [10, 10, 3, 7], [6] with a sum difference of 30 - 6 = 24, which is even.
 * Example 2:
 * 
 * Input: nums = [1,2,2]
 * 
 * Output: 0
 * 
 * Explanation:
 * 
 * No partition results in an even sum difference.
 * 
 * Example 3:
 * 
 * Input: nums = [2,4,6,8]
 * 
 * Output: 3
 * 
 * Explanation:
 * 
 * All partitions result in an even sum difference.
 * 
 * 
 * 
 * Constraints:
 * 
 * 2 <= n == nums.length <= 100
 * 1 <= nums[i] <= 100
 */

/*
 * COUNT PARTITIONS WITH EVEN SUM DIFFERENCE - Mathematical Insight
 * 
 * This problem has a BRILLIANT one-liner solution based on mathematical insight!
 * 
 * Key observation:
 * - If total sum is EVEN: ALL partitions have even difference
 * - If total sum is ODD: NO partition has even difference
 * 
 * Therefore:
 * - If totalSum % 2 == 0: return (n - 1) partitions
 * - If totalSum % 2 == 1: return 0 partitions
 */

/*
 * MATHEMATICAL PROOF - Why does this work?
 * 
 * For any partition at index i:
 * - leftSum = sum of nums[0..i]
 * - rightSum = sum of nums[i+1..n-1]
 * - diff = leftSum - rightSum
 * 
 * We know:
 * 1) leftSum + rightSum = totalSum
 * 2) leftSum - rightSum = diff
 * 
 * Adding equations (1) and (2):
 * leftSum + rightSum + leftSum - rightSum = totalSum + diff
 * 2 * leftSum = totalSum + diff
 * 
 * Rearranging:
 * diff = 2 * leftSum - totalSum
 * 
 * For diff to be EVEN:
 * - 2 * leftSum is ALWAYS even (2 times any number is even)
 * - For diff to be even: totalSum must also be EVEN
 * - If totalSum is even: diff is even (even - even = even)
 * - If totalSum is odd: diff is odd (even - odd = odd)
 * 
 * Conclusion: The parity of diff is SAME as parity of totalSum!
 */

/*
 * VISUAL PROOF: nums = [10, 10, 3, 7, 6], totalSum = 36 (even)
 * 
 * Partition 1: [10] | [10, 3, 7, 6]
 * - leftSum = 10
 * - rightSum = 26
 * - diff = 10 - 26 = -16 (even ✅)
 * - Check: 2*10 - 36 = 20 - 36 = -16 ✅
 * 
 * Partition 2: [10, 10] | [3, 7, 6]
 * - leftSum = 20
 * - rightSum = 16
 * - diff = 20 - 16 = 4 (even ✅)
 * - Check: 2*20 - 36 = 40 - 36 = 4 ✅
 * 
 * Partition 3: [10, 10, 3] | [7, 6]
 * - leftSum = 23
 * - rightSum = 13
 * - diff = 23 - 13 = 10 (even ✅)
 * - Check: 2*23 - 36 = 46 - 36 = 10 ✅
 * 
 * Partition 4: [10, 10, 3, 7] | [6]
 * - leftSum = 30
 * - rightSum = 6
 * - diff = 30 - 6 = 24 (even ✅)
 * - Check: 2*30 - 36 = 60 - 36 = 24 ✅
 * 
 * All 4 partitions have even diff because totalSum (36) is even!
 */

/*
 * VISUAL PROOF: nums = [1, 2, 2], totalSum = 5 (odd)
 * 
 * Partition 1: [1] | [2, 2]
 * - leftSum = 1
 * - rightSum = 4
 * - diff = 1 - 4 = -3 (odd ❌)
 * - Check: 2*1 - 5 = 2 - 5 = -3 ✅
 * 
 * Partition 2: [1, 2] | [2]
 * - leftSum = 3
 * - rightSum = 2
 * - diff = 3 - 2 = 1 (odd ❌)
 * - Check: 2*3 - 5 = 6 - 5 = 1 ✅
 * 
 * No partition has even diff because totalSum (5) is odd!
 * Answer: 0 ✅
 */

/*
 * WHY (n - 1) PARTITIONS?
 * 
 * A partition at index i splits array into [0..i] and [i+1..n-1]
 * 
 * Valid partition indices: 0, 1, 2, ..., n-2
 * - i = 0: [nums[0]] | [nums[1]..nums[n-1]]
 * - i = 1: [nums[0], nums[1]] | [nums[2]..nums[n-1]]
 * - ...
 * - i = n-2: [nums[0]..nums[n-2]] | [nums[n-1]]
 * 
 * We CANNOT use i = n-1 because:
 * - Right subarray would be empty: [i+1..n-1] = [n..n-1] = empty
 * - Problem requires BOTH subarrays to be non-empty
 * 
 * Total valid partitions: 0 to (n-2) = n - 1 partitions
 */

/*
 * EXAMPLE: nums = [2, 4, 6, 8], n = 4
 * 
 * Possible partitions: i = 0, 1, 2 (total = 4 - 1 = 3)
 * 
 * i = 0: [2] | [4, 6, 8]
 * i = 1: [2, 4] | [6, 8]
 * i = 2: [2, 4, 6] | [8]
 * 
 * totalSum = 2 + 4 + 6 + 8 = 20 (even)
 * → All 3 partitions have even difference
 * Answer: 3 ✅
 */

/*
 * PREFIX SUM APPROACH (O(n) time, O(n) space) - COMMENTED OUT
 * 
 * This is the "brute force" approach:
 * 1. Build prefix sum array
 * 2. For each partition, calculate leftSum and rightSum
 * 3. Check if (leftSum - rightSum) % 2 == 0
 * 
 * Why it's less optimal:
 * - Requires O(n) space for prefix sum
 * - Requires O(n) time to check all partitions
 * - Doesn't use the mathematical insight
 * 
 * Prefix sum calculation:
 * - ps[i] = sum of nums[0..i]
 * - leftSum = ps[i]
 * - rightSum = ps[n-1] - ps[i] = totalSum - leftSum
 * - diff = leftSum - rightSum = ps[i] - (ps[n-1] - ps[i])
 *        = 2*ps[i] - ps[n-1]
 *        = 2*leftSum - totalSum
 * 
 * This confirms our formula!
 */

/*
 * INSIGHT COMPARISON
 * 
 * Brute Force (Prefix Sum):
 * - Build prefix sum: O(n)
 * - Check each partition: O(n)
 * - Total: O(n) time, O(n) space
 * 
 * Mathematical Insight:
 * - Calculate total sum: O(n)
 * - Check if even: O(1)
 * - Total: O(n) time, O(1) space
 * - Much cleaner code!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Mathematical insight simplifies the problem
 *    → diff = 2*leftSum - totalSum
 *    → diff is even ⟺ totalSum is even
 * 
 * 2. All-or-nothing result
 *    → If totalSum even: ALL (n-1) partitions work
 *    → If totalSum odd: NO partition works
 * 
 * 3. Number of valid partitions = n - 1
 *    → Can partition at indices 0 to n-2
 *    → Cannot use i = n-1 (right would be empty)
 * 
 * 4. One-liner solution possible
 *    → return totalSum % 2 == 0 ? n - 1 : 0;
 * 
 * 5. Always look for mathematical patterns
 *    → Sometimes avoid simulation entirely!
 */

class Solution {
  public int countPartitions(int[] nums) {
    // APPROACH 1: PREFIX SUM (O(n) time, O(n) space) - COMMENTED OUT
    //
    // Build prefix sum array
    // int n = nums.length;
    // int[] ps = new int[n];
    // ps[0] = nums[0];
    // for (int i = 1; i < n; i++) {
    // ps[i] = nums[i] + ps[i-1];
    // }
    //
    // // Count partitions with even difference
    // int even = 0;
    // for(int i = 0; i < n - 1; i++) { // i from 0 to n-2 (n-1 partitions)
    // // leftSum = ps[i]
    // // rightSum = ps[n-1] - ps[i]
    // // diff = leftSum - rightSum = ps[i] - (ps[n-1] - ps[i])
    // int val = ps[i] - (ps[n-1] - ps[i]);
    // if (val % 2 == 0) {
    // even++;
    // }
    // }
    //
    // return even;

    // APPROACH 2: MATHEMATICAL INSIGHT (O(n) time, O(1) space)

    // Calculate total sum of array
    int totalSum = 0;
    for (int num : nums) {
      totalSum += num;
    }

    // Mathematical insight:
    // - diff = 2*leftSum - totalSum
    // - For diff to be even, totalSum must be even
    // - If totalSum is even: ALL (n-1) partitions have even diff
    // - If totalSum is odd: NO partition has even diff
    //
    // Why n-1? We can partition at indices 0 to n-2
    // - i = 0: [nums[0]] | [nums[1]..nums[n-1]]
    // - i = n-2: [nums[0]..nums[n-2]] | [nums[n-1]]
    // - Cannot use i = n-1 (right subarray would be empty)
    return totalSum % 2 == 0 ? nums.length - 1 : 0;
  }
}
