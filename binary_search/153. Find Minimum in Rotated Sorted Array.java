package binary_search;

/*
 * Suppose an array of length n sorted in ascending order is rotated between 1
 * and n times. For example, the array nums = [0,1,2,4,5,6,7] might become:
 * 
 * [4,5,6,7,0,1,2] if it was rotated 4 times.
 * [0,1,2,4,5,6,7] if it was rotated 7 times.
 * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results
 * in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
 * 
 * Given the sorted rotated array nums of unique elements, return the minimum
 * element of this array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,4,5,1,2]
 * Output: 1
 * Explanation: The original array was [1,2,3,4,5] rotated 3 times.
 * Example 2:
 * 
 * Input: nums = [4,5,6,7,0,1,2]
 * Output: 0
 * Explanation: The original array was [0,1,2,4,5,6,7] and it was rotated 4
 * times.
 * Example 3:
 * 
 * Input: nums = [11,13,15,17]
 * Output: 11
 * Explanation: The original array was [11,13,15,17] and it was rotated 4 times.
 * 
 * 
 * Constraints:
 * 
 * n == nums.length
 * 1 <= n <= 5000
 * -5000 <= nums[i] <= 5000
 * All the integers of nums are unique.
 * nums is sorted and rotated between 1 and n times.
 */

/*
 * ROTATED SORTED ARRAY PATTERN
 * 
 * A rotated sorted array has TWO sorted portions:
 * 
 * Original: [0, 1, 2, 4, 5, 6, 7]
 * Rotated:  [4, 5, 6, 7, 0, 1, 2]
 *            \_______/  \_____/
 *            Part 1     Part 2
 *            (larger)   (smaller)
 * 
 * Key observations:
 * 1. The array is split into two sorted subarrays
 * 2. All elements in Part 1 > all elements in Part 2
 * 3. The MINIMUM is the first element of Part 2 (rotation point)
 * 4. If not rotated, minimum is at index 0
 */

/*
 * WHY COMPARE WITH nums[end]?
 * 
 * We compare nums[mid] with nums[end] to determine which part mid is in:
 * 
 * Case 1: nums[mid] > nums[end]
 * - mid is in the LARGER part (Part 1)
 * - Minimum must be to the RIGHT of mid
 * - Example: [4, 5, 6, 7, 0, 1, 2]
 *                    mid=7  end=2
 *            7 > 2, so minimum is in [0, 1, 2]
 * 
 * Case 2: nums[mid] <= nums[end]
 * - mid is in the SMALLER part (Part 2) OR array is not rotated
 * - Minimum is at mid OR to the LEFT of mid
 * - Example: [4, 5, 6, 7, 0, 1, 2]
 *                       mid=0  end=2
 *            0 < 2, so minimum could be 0 or to its left
 */

/*
 * WHY NOT COMPARE WITH nums[start]?
 * 
 * Comparing with nums[start] doesn't work reliably:
 * 
 * Example: [4, 5, 6, 7, 0, 1, 2]
 * 
 * If mid = 7:
 * - nums[mid] > nums[start] (7 > 4) → doesn't tell us which part mid is in!
 * - Both parts can have elements > start
 * 
 * But nums[mid] vs nums[end]:
 * - nums[mid] > nums[end] (7 > 2) → clearly mid is in larger part!
 * - This reliably tells us where the minimum is
 */

/*
 * EXAMPLE 1: nums = [3, 4, 5, 1, 2]
 *                    0  1  2  3  4
 * 
 * Goal: Find minimum (1 at index 3)
 * 
 * Iteration 1:
 * - st = 0, end = 4
 * - mid = 0 + (4-0)/2 = 2
 * - nums[mid] = 5, nums[end] = 2
 * - 5 > 2 → mid is in larger part, minimum is to the right
 * - st = mid + 1 = 3
 * 
 * Iteration 2:
 * - st = 3, end = 4
 * - mid = 3 + (4-3)/2 = 3
 * - nums[mid] = 1, nums[end] = 2
 * - 1 < 2 → mid could be minimum or minimum is to the left
 * - end = mid = 3
 * 
 * Iteration 3:
 * - st = 3, end = 3
 * - st == end, loop exits
 * 
 * Return nums[st] = nums[3] = 1 ✅
 */

/*
 * EXAMPLE 2: nums = [4, 5, 6, 7, 0, 1, 2]
 *                    0  1  2  3  4  5  6
 * 
 * Goal: Find minimum (0 at index 4)
 * 
 * Iteration 1:
 * - st = 0, end = 6
 * - mid = 0 + (6-0)/2 = 3
 * - nums[mid] = 7, nums[end] = 2
 * - 7 > 2 → minimum is to the right
 * - st = mid + 1 = 4
 * 
 * Iteration 2:
 * - st = 4, end = 6
 * - mid = 4 + (6-4)/2 = 5
 * - nums[mid] = 1, nums[end] = 2
 * - 1 < 2 → minimum could be at mid or to the left
 * - end = mid = 5
 * 
 * Iteration 3:
 * - st = 4, end = 5
 * - mid = 4 + (5-4)/2 = 4
 * - nums[mid] = 0, nums[end] = 1
 * - 0 < 1 → minimum could be at mid
 * - end = mid = 4
 * 
 * Iteration 4:
 * - st = 4, end = 4
 * - st == end, loop exits
 * 
 * Return nums[st] = nums[4] = 0 ✅
 */

/*
 * EXAMPLE 3: nums = [11, 13, 15, 17] (not rotated or rotated n times)
 *                     0   1   2   3
 * 
 * Goal: Find minimum (11 at index 0)
 * 
 * Iteration 1:
 * - st = 0, end = 3
 * - mid = 0 + (3-0)/2 = 1
 * - nums[mid] = 13, nums[end] = 17
 * - 13 < 17 → minimum is at mid or to the left
 * - end = mid = 1
 * 
 * Iteration 2:
 * - st = 0, end = 1
 * - mid = 0 + (1-0)/2 = 0
 * - nums[mid] = 11, nums[end] = 13
 * - 11 < 13 → minimum is at mid
 * - end = mid = 0
 * 
 * Iteration 3:
 * - st = 0, end = 0
 * - st == end, loop exits
 * 
 * Return nums[st] = nums[0] = 11 ✅
 */

/*
 * WHY st < end (not st <= end)?
 * 
 * Using st <= end would cause infinite loop:
 * 
 * When st == end:
 * - mid = st + (end - st)/2 = st + 0 = st
 * - If nums[mid] <= nums[end]: end = mid = st
 * - st and end don't change → infinite loop!
 * 
 * Using st < end:
 * - Loop exits when st == end
 * - At this point, st points to the minimum
 * - No infinite loop possible
 */

/*
 * WHY end = mid (not end = mid - 1)?
 * 
 * Because mid COULD BE the minimum!
 * 
 * Example: [3, 4, 5, 1, 2]
 * When mid points to 1 (the minimum):
 * - nums[mid] < nums[end] → minimum is at mid or to the left
 * - If we do end = mid - 1, we SKIP the minimum!
 * - We must do end = mid to keep mid in the search range
 * 
 * This is safe because:
 * - When nums[mid] > nums[end], we know mid is NOT minimum
 * - So we can safely do st = mid + 1
 * - When nums[mid] <= nums[end], mid COULD be minimum
 * - So we must keep mid: end = mid
 */

/*
 * VISUAL REPRESENTATION:
 * 
 * Rotated array: [4, 5, 6, 7, 0, 1, 2]
 * 
 *     7  ←  Peak (largest in Part 1)
 *    / \
 *   6   \
 *  /     \
 * 5       0  ←  Minimum (rotation point)
 * |        \
 * 4         1
 *            \
 *             2
 * 
 * The minimum is at the "valley" where the rotation happened
 * Binary search navigates to this valley by comparing with end
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Rotated array has two sorted parts
 *    → Minimum is at the start of the smaller part
 * 
 * 2. Compare nums[mid] with nums[end]
 *    → Determines which part mid is in
 *    → nums[mid] > nums[end]: search right
 *    → nums[mid] <= nums[end]: search left (keep mid)
 * 
 * 3. Use st < end (not <=)
 *    → Prevents infinite loop
 *    → Loop exits when st == end
 * 
 * 4. Use end = mid (not mid - 1)
 *    → mid could be the minimum
 *    → Must keep mid in search range
 * 
 * 5. Time: O(log n), Space: O(1)
 *    → Binary search halves search space each iteration
 */

class Solution {
  public int findMin(int[] nums) {
    int n = nums.length;
    int st = 0;
    int end = n - 1;

    // Binary search to find the rotation point (minimum element)
    // Loop while search range has more than one element
    while (st < end) { // CRITICAL: Use < not <=
      // If we use <=, we get infinite loop when st == end
      // because mid = st, and end = mid = st (no progress)

      int mid = st + (end - st) / 2;

      // Compare mid with end to determine which part mid is in
      if (nums[mid] > nums[end]) {
        // Case 1: mid is in the LARGER part (Part 1)
        // Minimum must be to the RIGHT of mid
        // Example: [4, 5, 6, 7, 0, 1, 2]
        // mid=7 end=2
        // 7 > 2, so search [0, 1, 2]
        st = mid + 1;
      } else {
        // Case 2: mid is in the SMALLER part (Part 2) or array not rotated
        // Minimum is at mid OR to the LEFT of mid
        // Example: [4, 5, 6, 7, 0, 1, 2]
        // mid=0 end=2
        // 0 < 2, so minimum could be 0

        // CRITICAL: Use end = mid (not mid - 1)
        // Because mid COULD BE the minimum!
        // If we do mid - 1, we might skip the minimum
        end = mid;
      }
    }

    // When st == end, we've found the minimum
    // st points to the rotation point (smallest element)
    return nums[st];
  }
}

/*
 * ALTERNATIVE APPROACH - Solution2
 * 
 * This is a different strategy that also works!
 * 
 * Key Differences from Solution1:
 * 1. Uses st <= end (instead of st < end)
 * 2. Tracks minimum in ans variable (instead of returning index)
 * 3. Uses end = mid - 1 (instead of end = mid)
 * 
 * Why does this work when Solution1 needed st < end?
 * Because we're TRACKING the minimum in ans before moving pointers!
 */

/*
 * WHY st <= end WORKS HERE (but not in Solution1)
 * 
 * Solution1 problem with st <= end:
 * - When st == end, mid = st
 * - If nums[mid] <= nums[end]: end = mid = st
 * - No progress → infinite loop!
 * 
 * Solution2 avoids this because:
 * - When nums[mid] <= nums[end]: we do end = mid - 1 (not mid)
 * - This ALWAYS makes progress: end decreases
 * - No infinite loop possible!
 * 
 * Example: nums = [1], st = 0, end = 0
 * - mid = 0
 * - nums[0] <= nums[0] → ans = min(MAX, 1) = 1
 * - end = mid - 1 = -1
 * - Loop exits (st > end)
 * - Return ans = 1 ✅
 */

/*
 * WHY end = mid - 1 WORKS HERE (but not in Solution1)
 * 
 * Solution1 problem with end = mid - 1:
 * - If mid IS the minimum, we skip it!
 * - We lose the answer
 * 
 * Solution2 avoids this because:
 * - We save nums[mid] in ans BEFORE doing end = mid - 1
 * - Even if we skip mid, we've already captured its value
 * - ans = Math.min(ans, nums[mid]) ensures we don't lose the minimum
 */

/*
 * EXAMPLE: nums = [3, 4, 5, 1, 2]
 *                  0  1  2  3  4
 * 
 * Initial: st = 0, end = 4, ans = MAX
 * 
 * Iteration 1:
 * - mid = 0 + (4-0)/2 = 2
 * - nums[2] = 5, nums[4] = 2
 * - 5 > 2 → minimum is to the right
 * - st = mid + 1 = 3
 * - ans = MAX (unchanged)
 * 
 * Iteration 2:
 * - st = 3, end = 4
 * - mid = 3 + (4-3)/2 = 3
 * - nums[3] = 1, nums[4] = 2
 * - 1 < 2 → save this value!
 * - ans = min(MAX, 1) = 1 ✅
 * - end = mid - 1 = 2
 * 
 * Iteration 3:
 * - st = 3, end = 2
 * - st > end, loop exits
 * 
 * Return ans = 1 ✅
 */

/*
 * COMPARISON: Solution1 vs Solution2
 * 
 * Solution1 (Index-based):
 * - Returns the INDEX where minimum is located
 * - Uses st < end to avoid infinite loop
 * - Uses end = mid to keep potential minimum in range
 * - Simpler logic, no extra variable needed
 * 
 * Solution2 (Value-based):
 * - Tracks the MINIMUM VALUE in ans variable
 * - Uses st <= end (safe because we do end = mid - 1)
 * - Uses end = mid - 1 (safe because we saved value in ans)
 * - Requires extra variable but more flexible
 * 
 * Both are O(log n) time and O(1) space
 * Both are correct - choose based on preference!
 */

/*
 * WHY TRACK ans = Integer.MAX_VALUE?
 * 
 * We need a starting value for comparison.
 * 
 * Integer.MAX_VALUE ensures:
 * - Any actual array value will be smaller
 * - First comparison will update ans to actual value
 * 
 * Alternative: Could initialize ans = nums[0]
 * - Also works, but MAX_VALUE is more general
 */

/*
 * KEY INSIGHT: The Trade-off
 * 
 * Solution1: More elegant, fewer variables
 * - Relies on pointer convergence to find minimum
 * - Must be careful with loop condition and end update
 * 
 * Solution2: More explicit, tracks minimum directly
 * - Saves minimum value as we find it
 * - Allows more aggressive pointer movement (end = mid - 1)
 * - Easier to understand for some people
 * 
 * Both strategies are valid binary search patterns!
 */

class Solution2 {
  public int findMin(int[] nums) {
    int n = nums.length;
    int st = 0;
    int end = n - 1;
    
    // Track the minimum value found so far
    // Initialize to MAX_VALUE so any array value will be smaller
    int ans = Integer.MAX_VALUE;

    // Binary search with st <= end
    // This works here because we do end = mid - 1 (not end = mid)
    while (st <= end) {
      int mid = st + (end - st) / 2;

      if (nums[mid] > nums[end]) {
        // Case 1: mid is in the LARGER part
        // Minimum is to the right, don't save nums[mid]
        st = mid + 1;
      } else {
        // Case 2: mid is in the SMALLER part or array not rotated
        // nums[mid] could be the minimum!
        
        // CRITICAL: Save nums[mid] in ans BEFORE moving end
        // This ensures we don't lose the minimum even if we skip mid
        ans = Math.min(ans, nums[mid]);
        
        // Now we can safely do end = mid - 1
        // Even if mid was the minimum, we've already saved it in ans
        end = mid - 1;
      }
    }

    // Return the minimum value we tracked
    return ans;
  }
}
