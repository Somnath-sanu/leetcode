package array;

/*
 * Given an integer array nums, rotate the array to the right by k steps, where
 * k is non-negative.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * Example 2:
 * 
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 * 
 * 
 * Follow up:
 * 
 * Try to come up with as many solutions as you can. There are at least three
 * different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */

/*
 * ROTATE ARRAY PATTERN - Three Reversal Technique
 * 
 * This is a clever O(1) space solution using THREE reversals:
 * 1. Reverse the ENTIRE array
 * 2. Reverse the FIRST k elements
 * 3. Reverse the REMAINING (n-k) elements
 * 
 * Why does this work? Let's understand the transformation!
 */

/*
 * INTUITION: What does rotation mean?
 * 
 * Rotating right by k means:
 * - The last k elements move to the front
 * - The first (n-k) elements shift right by k positions
 * 
 * Example: [1,2,3,4,5,6,7], k = 3
 * - Last 3 elements: [5,6,7] → move to front
 * - First 4 elements: [1,2,3,4] → shift to back
 * - Result: [5,6,7,1,2,3,4]
 * 
 * We can split the array into two parts:
 * - Part A: [1,2,3,4] (first n-k elements)
 * - Part B: [5,6,7] (last k elements)
 * - After rotation: [B, A] = [5,6,7,1,2,3,4]
 */

/*
 * THE THREE REVERSAL TRICK
 * 
 * To transform [A, B] into [B, A]:
 * 
 * Step 1: Reverse entire array [A, B]
 *         → [B_reversed, A_reversed]
 *         Example: [1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]
 * 
 * Step 2: Reverse first k elements (B_reversed)
 *         → [B, A_reversed]
 *         Example: [7,6,5,4,3,2,1] → [5,6,7,4,3,2,1]
 * 
 * Step 3: Reverse remaining n-k elements (A_reversed)
 *         → [B, A]
 *         Example: [5,6,7,4,3,2,1] → [5,6,7,1,2,3,4]
 * 
 * Mathematical proof:
 * - reverse(A, B) = (B^R, A^R)  where ^R means reversed
 * - reverse(B^R) = B
 * - reverse(A^R) = A
 * - Final result: (B, A) ✅
 */

/*
 * DETAILED EXAMPLE: nums = [1,2,3,4,5,6,7], k = 3
 * 
 * Initial array: [1, 2, 3, 4, 5, 6, 7]
 *                 \_______/  \______/
 *                  Part A     Part B
 *                (n-k=4)      (k=3)
 * 
 * Step 1: Reverse entire array
 * - Input:  [1, 2, 3, 4, 5, 6, 7]
 * - Output: [7, 6, 5, 4, 3, 2, 1]
 * - What happened? Both parts reversed and swapped positions
 *   [7,6,5] = B reversed, [4,3,2,1] = A reversed
 * 
 * Step 2: Reverse first k=3 elements
 * - Input:  [7, 6, 5, 4, 3, 2, 1]
 *            \_____/
 *          reverse this
 * - Output: [5, 6, 7, 4, 3, 2, 1]
 * - What happened? B is now in correct order!
 * 
 * Step 3: Reverse remaining n-k=4 elements
 * - Input:  [5, 6, 7, 4, 3, 2, 1]
 *                     \__________/
 *                   reverse this
 * - Output: [5, 6, 7, 1, 2, 3, 4]
 * - What happened? A is now in correct order!
 * 
 * Final: [5, 6, 7, 1, 2, 3, 4] ✅
 */

/*
 * EXAMPLE 2: nums = [-1, -100, 3, 99], k = 2
 * 
 * Initial: [-1, -100, 3, 99]
 *           \______/  \___/
 *            Part A   Part B
 *            (n-k=2)  (k=2)
 * 
 * Step 1: Reverse entire array
 * - [99, 3, -100, -1]
 * 
 * Step 2: Reverse first k=2 elements
 * - [99, 3, -100, -1] → [3, 99, -100, -1]
 * 
 * Step 3: Reverse remaining n-k=2 elements
 * - [3, 99, -100, -1] → [3, 99, -1, -100]
 * 
 * Final: [3, 99, -1, -100] ✅
 */

/*
 * WHY k = k % n?
 * 
 * If k >= n, we're doing extra full rotations that don't change the array.
 * 
 * Example: nums = [1,2,3,4,5], k = 7
 * - Rotating by 7 = rotating by 5 + 2
 * - Rotating by 5 brings array back to original
 * - Only the extra 2 rotations matter
 * - k % n = 7 % 5 = 2
 * 
 * Example: nums = [1,2,3], k = 0
 * - k % n = 0 % 3 = 0
 * - No rotation needed
 * 
 * Example: nums = [1,2,3], k = 3
 * - k % n = 3 % 3 = 0
 * - Full rotation = back to original
 * 
 * This optimization is CRITICAL:
 * - Prevents unnecessary work
 * - Handles k > n case
 * - Handles k = 0 case (no rotation)
 */

/*
 * EDGE CASES
 * 
 * Case 1: k = 0 (no rotation)
 * - k % n = 0
 * - Step 2: reverse [0, -1] → empty range, no change
 * - Step 3: reverse [0, n-1] → reverses then reverses again
 * - Actually, need to handle k=0 specially or both steps will reverse whole array twice
 * - After k % n, if k = 0, array stays same ✅
 * 
 * Case 2: k = n (full rotation)
 * - k % n = 0
 * - Same as k = 0, array unchanged ✅
 * 
 * Case 3: k = 1 (rotate by 1)
 * - Last element moves to front
 * - [1,2,3,4,5] → [5,1,2,3,4]
 * 
 * Case 4: k > n
 * - k % n handles it
 * - Example: k = 8, n = 5 → k % n = 3
 */

/*
 * ALTERNATIVE APPROACHES (mentioned in follow-up)
 * 
 * Approach 1: Using extra array (O(n) space)
 * - Create new array
 * - Place each element at (i + k) % n position
 * - Copy back to original
 * - Time: O(n), Space: O(n)
 * 
 * Approach 2: Rotate one by one (k times)
 * - Store last element
 * - Shift all elements right by 1
 * - Place stored element at front
 * - Repeat k times
 * - Time: O(n * k), Space: O(1)
 * 
 * Approach 3: Three reversals (THIS SOLUTION)
 * - Reverse entire, reverse first k, reverse last n-k
 * - Time: O(n), Space: O(1)
 * - OPTIMAL! ✅
 * 
 * Approach 4: Cyclic replacements
 * - Follow cycles of elements moving to correct positions
 * - Complex but also O(n) time, O(1) space
 */

/*
 * TIME AND SPACE COMPLEXITY
 * 
 * Time Complexity: O(n)
 * - Step 1: Reverse n elements → O(n)
 * - Step 2: Reverse k elements → O(k)
 * - Step 3: Reverse (n-k) elements → O(n-k)
 * - Total: O(n) + O(k) + O(n-k) = O(n)
 * 
 * Space Complexity: O(1)
 * - Only use constant extra space for swap
 * - Modify array in-place
 * - No recursion, no extra arrays
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Three reversal technique for array rotation
 *    → Reverse all, reverse first k, reverse last n-k
 * 
 * 2. Always do k = k % n first
 *    → Handles k > n and k = 0 cases
 * 
 * 3. Transformation: [A, B] → [B, A]
 *    → Reverse entire: [B^R, A^R]
 *    → Reverse parts: [B, A]
 * 
 * 4. In-place with O(1) space
 *    → Optimal solution for the problem
 * 
 * 5. Reverse function is key utility
 *    → Two pointers, swap and move inward
 */

class Solution {
  public void rotate(int[] nums, int k) {
    int n = nums.length;

    // CRITICAL: Handle k >= n case
    // If k = n, rotating n times brings array back to original
    // If k > n, we only need to rotate k % n times
    // Example: k = 8, n = 5 → only need to rotate 3 times
    k = k % n;

    // Step 1: Reverse the ENTIRE array
    // [1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]
    // This swaps the two parts but in reversed order
    int l = 0;
    int r = n - 1;
    reverse(nums, l, r);

    // Step 2: Reverse the FIRST k elements
    // [7,6,5,4,3,2,1] → [5,6,7,4,3,2,1]
    // This restores the "last k elements" to correct order
    l = 0;
    r = k - 1;
    reverse(nums, l, r);

    // Step 3: Reverse the REMAINING (n-k) elements
    // [5,6,7,4,3,2,1] → [5,6,7,1,2,3,4]
    // This restores the "first n-k elements" to correct order
    l = k;
    r = n - 1;
    reverse(nums, l, r);

    // Now we have [Part B, Part A] = rotated array!
  }

  /**
   * Helper function to reverse array elements from index l to r (inclusive)
   * 
   * Uses two-pointer technique:
   * - Left pointer starts at l
   * - Right pointer starts at r
   * - Swap elements and move pointers inward
   * - Stop when pointers meet or cross
   * 
   * @param nums Array to modify
   * @param l    Left index (inclusive)
   * @param r    Right index (inclusive)
   */
  public void reverse(int[] nums, int l, int r) {
    // Two pointers: move inward while swapping
    while (l < r) {
      // Swap nums[l] and nums[r]
      int temp = nums[l];
      nums[l] = nums[r];
      nums[r] = temp;

      // Move pointers inward
      l++;
      r--;
    }
    // When l >= r, we've reversed the entire range
  }
}