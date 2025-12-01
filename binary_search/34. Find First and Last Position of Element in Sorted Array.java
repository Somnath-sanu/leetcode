package binary_search;

/*
 * Given an array of integers nums sorted in non-decreasing order, find the
 * starting and ending position of a given target value.
 * 
 * If target is not found in the array, return [-1, -1].
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 8
 * Output: [3,4]
 * Example 2:
 * 
 * Input: nums = [5,7,7,8,8,10], target = 6
 * Output: [-1,-1]
 * Example 3:
 * 
 * Input: nums = [], target = 0
 * Output: [-1,-1]
 * 
 * 
 * Constraints:
 * 
 * 0 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * nums is a non-decreasing array.
 * -10^9 <= target <= 10^9
 */

/*
 * BINARY SEARCH FOR BOUNDARIES PATTERN
 * 
 * This problem requires finding FIRST and LAST occurrence of target
 * Not just "does target exist?" but "where does it start and end?"
 * 
 * Key Insight: Run binary search TWICE
 * 1. First search: Find LEFTMOST (first) occurrence
 * 2. Second search: Find RIGHTMOST (last) occurrence
 * 
 * The trick: Even after finding target, keep searching to find the boundary!
 */

/*
 * FINDING FIRST OCCURRENCE - Search for LEFT BOUNDARY
 * 
 * Goal: Find the LEFTMOST index where nums[i] == target
 * 
 * Strategy:
 * - When nums[mid] == target: DON'T stop! Keep searching LEFT
 * - When nums[mid] < target: Search RIGHT (target is ahead)
 * - When nums[mid] > target: Search LEFT (target is behind)
 * 
 * Key condition: if (nums[mid] < target) → go right
 *                else → go left (includes == and >)
 * 
 * This ensures we keep moving LEFT even when we find target,
 * until we find the FIRST occurrence
 */

/*
 * FINDING LAST OCCURRENCE - Search for RIGHT BOUNDARY
 * 
 * Goal: Find the RIGHTMOST index where nums[i] == target
 * 
 * Strategy:
 * - When nums[mid] == target: DON'T stop! Keep searching RIGHT
 * - When nums[mid] < target: Search RIGHT (target is ahead)
 * - When nums[mid] > target: Search LEFT (target is behind)
 * 
 * Key condition: if (nums[mid] <= target) → go right (includes ==)
 *                else → go left (only >)
 * 
 * This ensures we keep moving RIGHT even when we find target,
 * until we find the LAST occurrence
 */

/*
 * EXAMPLE: nums = [5, 7, 7, 8, 8, 10], target = 8
 *                  0  1  2  3  4   5
 * 
 * FINDING FIRST OCCURRENCE:
 * 
 * Initial: l=0, r=5
 * 
 * Iteration 1:
 * - mid = 0 + (5-0)/2 = 2
 * - nums[2] = 7 < 8 → go right
 * - l = 3, r = 5
 * 
 * Iteration 2:
 * - mid = 3 + (5-3)/2 = 4
 * - nums[4] = 8 == 8 → found! but keep searching left
 * - first = 4
 * - nums[4] NOT < 8 → go left (else branch)
 * - l = 3, r = 3
 * 
 * Iteration 3:
 * - mid = 3 + (3-3)/2 = 3
 * - nums[3] = 8 == 8 → found! update first
 * - first = 3
 * - nums[3] NOT < 8 → go left
 * - l = 3, r = 2
 * 
 * Loop ends (l > r)
 * First occurrence: 3 ✅
 * 
 * FINDING LAST OCCURRENCE:
 * 
 * Initial: l=0, r=5
 * 
 * Iteration 1:
 * - mid = 0 + (5-0)/2 = 2
 * - nums[2] = 7 <= 8 → go right
 * - l = 3, r = 5
 * 
 * Iteration 2:
 * - mid = 3 + (5-3)/2 = 4
 * - nums[4] = 8 == 8 → found! but keep searching right
 * - last = 4
 * - nums[4] <= 8 → go right (continue searching)
 * - l = 5, r = 5
 * 
 * Iteration 3:
 * - mid = 5 + (5-5)/2 = 5
 * - nums[5] = 10 > 8 → go left
 * - l = 5, r = 4
 * 
 * Loop ends (l > r)
 * Last occurrence: 4 ✅
 * 
 * Answer: [3, 4] ✅
 */

/*
 * EXAMPLE: nums = [5, 7, 7, 8, 8, 10], target = 6
 * 
 * FINDING FIRST OCCURRENCE:
 * 
 * Iteration 1:
 * - mid = 2, nums[2] = 7 > 6 → go left
 * - l = 0, r = 1
 * 
 * Iteration 2:
 * - mid = 0, nums[0] = 5 < 6 → go right
 * - l = 1, r = 1
 * 
 * Iteration 3:
 * - mid = 1, nums[1] = 7 > 6 → go left
 * - l = 1, r = 0
 * 
 * Loop ends, first = -1 (never found)
 * 
 * Since first == -1, return [-1, -1] without searching for last
 */

/*
 * KEY DIFFERENCE BETWEEN FIRST AND LAST SEARCH:
 * 
 * Finding FIRST (leftmost):
 * if (nums[mid] < target) {
 *   l = mid + 1;  // Go right
 * } else {
 *   r = mid - 1;  // Go left (includes == and >)
 * }
 * 
 * Finding LAST (rightmost):
 * if (nums[mid] <= target) {  // Note: <= instead of <
 *   l = mid + 1;  // Go right (includes ==)
 * } else {
 *   r = mid - 1;  // Go left (only >)
 * }
 * 
 * The difference is in handling nums[mid] == target:
 * - First search: treat == as "go left" (find earlier occurrence)
 * - Last search: treat == as "go right" (find later occurrence)
 */

/*
 * WHY CONTINUE SEARCHING AFTER FINDING TARGET?
 * 
 * Example: nums = [8, 8, 8, 8, 8], target = 8
 * 
 * If we STOP at first match:
 * - mid = 2, nums[2] = 8 → return 2
 * - Wrong! First occurrence is at index 0
 * 
 * By continuing to search:
 * - mid = 2, nums[2] = 8 → first = 2, but keep going left
 * - mid = 0, nums[0] = 8 → first = 0, keep going left
 * - Eventually find index 0 ✅
 * 
 * Same for last occurrence - need to keep searching right
 */

/*
 * COMPARISON WITH LINEAR SEARCH:
 * 
 * Linear O(n):
 * for (int i = 0; i < n; i++) {
 *   if (nums[i] == target) {
 *     if (first == -1) first = i;
 *     last = i;
 *   }
 * }
 * 
 * Binary Search O(log n):
 * - Run binary search twice
 * - Each search is O(log n)
 * - Total: O(log n) + O(log n) = O(log n)
 * 
 * Much faster for large arrays!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Run binary search TWICE for first and last
 *    → Each search has slightly different condition
 * 
 * 2. Finding FIRST: use (nums[mid] < target)
 *    → Treat == as "go left"
 * 
 * 3. Finding LAST: use (nums[mid] <= target)
 *    → Treat == as "go right"
 * 
 * 4. Don't stop when finding target
 *    → Keep searching to find boundary
 * 
 * 5. Time: O(log n), Space: O(1)
 *    → Two binary searches, no extra space
 */

class Solution {
  public int[] searchRange(int[] nums, int target) {
    int n = nums.length;
    int first = -1;
    int last = -1;

    // LINEAR SEARCH APPROACH (O(n) - not optimal)
    // Iterate through entire array to find first and last
    //
    // for (int i = 0; i < n; i++) {
    // if (nums[i] == target) {
    // if (first == -1) {
    // first = i; // First occurrence
    // }
    // last = i; // Keep updating last
    // }
    // }
    //
    // if (first == -1) {
    // return new int[]{-1, -1};
    // }
    //
    // return new int[]{first, last};

    // BINARY SEARCH APPROACH (O(log n) - optimal)

    // ========== SEARCH 1: Find FIRST (leftmost) occurrence ==========
    int l = 0;
    int r = n - 1;

    while (l <= r) {
      int mid = l + (r - l) / 2;

      // If we find target, record it but keep searching left
      if (nums[mid] == target) {
        first = mid;
      }

      // Key condition for finding FIRST occurrence:
      // Only go right if nums[mid] is strictly LESS than target
      // Otherwise (== or >), go left to find earlier occurrence
      if (nums[mid] < target) {
        l = mid + 1; // Target is to the right
      } else {
        r = mid - 1; // Target is to the left OR we found it (keep searching left)
      }
    }

    // ========== SEARCH 2: Find LAST (rightmost) occurrence ==========
    l = 0;
    r = n - 1;

    while (l <= r) {
      int mid = l + (r - l) / 2;

      // If we find target, record it but keep searching right
      if (nums[mid] == target) {
        last = mid;
      }

      // Key condition for finding LAST occurrence:
      // Go right if nums[mid] is LESS than OR EQUAL to target
      // This includes when we find target (keep searching right)
      if (nums[mid] <= target) {
        l = mid + 1; // Target is to the right OR we found it (keep searching right)
      } else {
        r = mid - 1; // Target is to the left
      }
    }

    // If target was never found, first will still be -1
    if (first == -1) {
      return new int[] { -1, -1 };
    }

    return new int[] { first, last };
  }
}
