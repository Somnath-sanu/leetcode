package array;

import java.util.*;

/*
 * Given an integer array nums, return all the triplets [nums[i], nums[j],
 * nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] +
 * nums[k] == 0.
 * 
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not
 * matter.
 * Example 2:
 * 
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * Example 3:
 * 
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 * 
 * 
 * Constraints:
 * 
 * 3 <= nums.length <= 3000
 * -10^5 <= nums[i] <= 10^5
 * 
 */

/*
 * 3SUM PATTERN - Sort + Two Pointers
 * 
 * This is an extension of the Two Sum problem with a critical difference:
 * - Two Sum: return INDICES → cannot sort (would lose index positions)
 * - 3Sum: return VALUES → CAN sort (order doesn't matter!)
 * 
 * Algorithm:
 * 1. Sort the array
 * 2. Fix first element (nums[i])
 * 3. Use Two Sum II (two pointers) to find the other two elements
 * 4. Skip duplicates to avoid duplicate triplets
 */

/*
 * WHY SORTING IS VALID HERE (but not in Two Sum)
 * 
 * Two Sum problem:
 * - Return: [index_i, index_j]
 * - Cannot sort because we need original indices
 * - Must use HashMap to preserve positions
 * 
 * 3Sum problem:
 * - Return: [value_i, value_j, value_k]
 * - Sorting doesn't affect the answer
 * - Example: [-1, 0, 1] and [0, -1, 1] are the same triplet
 * - Sorting enables two-pointer technique!
 */

/*
 * ALGORITHM BREAKDOWN
 * 
 * For each element nums[i] as the "first" element:
 * - Problem becomes: Find two numbers that sum to -nums[i]
 * - This is exactly the Two Sum II problem (sorted array)!
 * - Use two pointers: left and right
 * 
 * Key insight:
 * nums[i] + nums[j] + nums[k] = 0
 * → nums[j] + nums[k] = -nums[i]
 * 
 * So we're doing Two Sum with target = -nums[i]
 */

/*
 * DUPLICATE HANDLING - THREE LEVELS
 * 
 * We need to skip duplicates at THREE positions to avoid duplicate triplets:
 * 
 * Level 1: Skip duplicate first elements (i)
 * - If nums[i] == nums[i-1], skip this i
 * - Example: [-1, -1, 2] → only process first -1
 * 
 * Level 2: Skip duplicate left pointers (j)
 * - After finding a valid triplet, skip while nums[j] == nums[j+1]
 * - Example: [-1, 0, 0, 1] → only use first 0
 * 
 * Level 3: Skip duplicate right pointers (k)
 * - After finding a valid triplet, skip while nums[k] == nums[k-1]
 * - Example: [-1, 0, 1, 1] → only use first 1
 */

/*
 * EXAMPLE: nums = [-1, 0, 1, 2, -1, -4]
 * 
 * Step 1: Sort
 * nums = [-4, -1, -1, 0, 1, 2]
 *         0   1   2  3  4  5
 * 
 * Step 2: Fix first element and use two pointers
 * 
 * i=0, nums[i]=-4, target=-(-4)=4:
 * - Left=1, Right=5
 * - nums[1] + nums[5] = -1 + 2 = 1 < 4 → left++
 * - nums[2] + nums[5] = -1 + 2 = 1 < 4 → left++
 * - nums[3] + nums[5] = 0 + 2 = 2 < 4 → left++
 * - nums[4] + nums[5] = 1 + 2 = 3 < 4 → left++
 * - left >= right, no triplet found
 * 
 * i=1, nums[i]=-1, target=-(-1)=1:
 * - Left=2, Right=5
 * - nums[2] + nums[5] = -1 + 2 = 1 = 1 ✅
 * - Found: [-1, -1, 2]
 * - Skip duplicates: nums[2]==nums[3]=-1, so left=3
 * - left++, right-- → left=3, right=4
 * - nums[3] + nums[4] = 0 + 1 = 1 = 1 ✅
 * - Found: [-1, 0, 1]
 * - left++, right-- → left=4, right=3
 * - left >= right, done
 * 
 * i=2, nums[i]=-1:
 * - SKIP! nums[2] == nums[1] (duplicate first element)
 * 
 * i=3, nums[i]=0, target=0:
 * - Left=4, Right=5
 * - nums[4] + nums[5] = 1 + 2 = 3 > 0 → right--
 * - left >= right, no triplet found
 * 
 * Result: [[-1, -1, 2], [-1, 0, 1]] ✅
 */

/*
 * WHY TWO POINTERS WORK ON SORTED ARRAY
 * 
 * When array is sorted:
 * - If sum > target: right-- (need smaller value)
 * - If sum < target: left++ (need larger value)
 * - If sum == target: found it!
 * 
 * Example: [-4, -1, -1, 0, 1, 2], target = 1
 *           L              R
 * - nums[L] + nums[R] = -4 + 2 = -2 < 1 → L++
 *              L         R
 * - nums[L] + nums[R] = -1 + 2 = 1 = 1 ✅
 * 
 * This is O(n) for finding all pairs (vs O(n²) brute force)
 */

/*
 * BRUTE FORCE COMPARISON
 * 
 * Naive approach (three nested loops):
 * for (i = 0 to n-3)
 *   for (j = i+1 to n-2)
 *     for (k = j+1 to n-1)
 *       if (nums[i] + nums[j] + nums[k] == 0)
 *         add to result
 * 
 * Time: O(n³)
 * Problem: Very slow + need to handle duplicates with HashSet
 * 
 * Optimized approach (THIS SOLUTION):
 * 1. Sort: O(n log n)
 * 2. For each i: O(n)
 *    - Two pointers: O(n)
 * Total: O(n²)
 * 
 * Much faster! And duplicates handled naturally with sorting
 */

/*
 * EXAMPLE 2: nums = [0, 0, 0]
 * 
 * After sorting: [0, 0, 0]
 * 
 * i=0, nums[i]=0, target=0:
 * - Left=1, Right=2
 * - nums[1] + nums[2] = 0 + 0 = 0 = 0 ✅
 * - Found: [0, 0, 0]
 * - Skip duplicates: nums[1] == nums[2], so left=2
 * - left++, right-- → left=2, right=1
 * - left >= right, done
 * 
 * i=1, nums[i]=0:
 * - SKIP! nums[1] == nums[0] (duplicate first element)
 * 
 * Result: [[0, 0, 0]] ✅
 */

/*
 * CRITICAL: When to skip duplicates for i
 * 
 * WRONG: if (nums[i] == nums[i+1]) continue;
 * - This would skip valid triplets!
 * - Example: [-1, -1, 2] would skip both -1's
 * 
 * CORRECT: if (i > 0 && nums[i] == nums[i-1]) continue;
 * - Only skip if we've already processed this value
 * - First occurrence of -1 is processed
 * - Second occurrence is skipped
 * 
 * Note: We use i > 0 (or i != 0) to avoid index out of bounds
 */

/*
 * WHY SKIP DUPLICATES IN TWO POINTERS?
 * 
 * After finding a valid triplet, we need to skip duplicates
 * for BOTH left and right pointers.
 * 
 * Example: nums = [-2, 0, 0, 0, 2, 2]
 * target = 2 (from i=-2)
 * 
 * Initial: left = 1 (0), right = 5 (2)
 * - nums[1] + nums[5] = 0 + 2 = 2 ✅
 * - Found: [-2, 0, 2]
 * 
 * Without skipping:
 * - left++, right-- → left=2, right=4
 * - nums[2] + nums[4] = 0 + 2 = 2 ✅
 * - Found: [-2, 0, 2] (DUPLICATE!)
 * 
 * With skipping:
 * - Skip while nums[left] == nums[left+1]: left goes from 1→2→3
 * - Skip while nums[right] == nums[right-1]: right goes from 5→4
 * - left++, right-- → left=4, right=3
 * - left >= right, done
 * - Only one [-2, 0, 2] triplet ✅
 */

/*
 * TIME AND SPACE COMPLEXITY
 * 
 * Time Complexity: O(n²)
 * - Sorting: O(n log n)
 * - Outer loop (i): O(n) iterations
 * - Inner two pointers: O(n) per iteration
 * - Total: O(n log n) + O(n) × O(n) = O(n²)
 * 
 * Space Complexity: O(1) or O(n)
 * - Sorting: O(log n) or O(n) depending on sorting algorithm
 * - No extra data structures used
 * - Output list doesn't count towards space complexity
 * - If we ignore sorting space: O(1)
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Sorting enables two-pointer technique
 *    → Valid because we return values, not indices
 * 
 * 2. Reduce 3Sum to Two Sum
 *    → Fix first element, find two numbers that sum to -first
 * 
 * 3. Skip duplicates at THREE levels
 *    → First element: if nums[i] == nums[i-1]
 *    → Left pointer: while nums[j] == nums[j+1]
 *    → Right pointer: while nums[k] == nums[k-1]
 * 
 * 4. Two pointers work on sorted array
 *    → If sum > target: right--
 *    → If sum < target: left++
 * 
 * 5. O(n²) optimal for this problem
 *    → Better than O(n³) brute force
 *    → No known O(n) solution for 3Sum
 */

class Solution {
  // T.C : O(n²)
  // S.C : O(1) (excluding sorting space)

  public List<List<Integer>> threeSum(int[] nums) {
    // KEY DIFFERENCE from Two Sum:
    // In Two Sum, we had to return INDICES
    // → Cannot sort (would lose original positions)
    // → Must use HashMap
    //
    // In 3Sum, we return VALUES
    // → CAN sort (order doesn't matter)
    // → Enables two-pointer technique
    // → Natural duplicate handling

    int n = nums.length;
    List<List<Integer>> result = new ArrayList<>();

    // Step 1: Sort the array
    // This enables two-pointer technique
    // Time: O(n log n)
    Arrays.sort(nums);

    // Step 2: Fix first element and find the other two
    // We iterate up to n-2 because we need at least 2 elements after i
    for (int i = 0; i < n - 2; i++) {
      // CRITICAL: Skip duplicate first elements
      // If nums[i] == nums[i-1], we've already processed this value
      // Example: [-1, -1, 2, ...] → only process first -1
      //
      // Why i != 0 (not i > 0)?
      // - Both work, just different style
      // - i != 0 reads as "not the first element"
      // - i > 0 reads as "index greater than 0"
      if (i != 0 && nums[i] == nums[i - 1]) {
        continue; // Skip this iteration, move to next unique value
      }

      // Fix nums[i] as the first element of triplet
      // Problem: Find two numbers that sum to -nums[i]
      // This is exactly Two Sum II on sorted array!
      //
      // Example: nums = [-4, -1, -1, 0, 1, 2]
      // If i=1, nums[i]=-1
      // We look for two numbers in [-1, 0, 1, 2] that sum to 1
      twoSum(nums, result, -nums[i], i + 1);
    }

    return result;
  }

  /**
   * Two Sum II on sorted array using two pointers
   * 
   * Find all pairs nums[i] + nums[j] = target where i < j
   * Start from index k (to avoid reusing the first element)
   * 
   * @param nums   Sorted array
   * @param result List to add found triplets
   * @param target Sum we're looking for
   * @param k      Starting index (after the first fixed element)
   */
  private void twoSum(int[] nums, List<List<Integer>> result, int target, int k) {
    // Two pointers: start from both ends of the subarray
    int i = k; // Left pointer (start)
    int j = nums.length - 1; // Right pointer (end)

    // Move pointers towards each other
    while (i < j) {
      int sum = nums[i] + nums[j];

      if (sum > target) {
        // Sum too large, need smaller value
        // Since array is sorted, decrease right pointer
        j--;
      } else if (sum < target) {
        // Sum too small, need larger value
        // Since array is sorted, increase left pointer
        i++;
      } else {
        // Found a valid pair! sum == target
        // Add the complete triplet: [first_element, nums[i], nums[j]]
        // Note: -target is the original first element we fixed
        result.add(Arrays.asList(-target, nums[i], nums[j]));

        // CRITICAL: Skip duplicate left pointers
        // Move left pointer past all duplicates
        // Example: [... 0, 0, 0 ...] → skip to last 0
        while (i < j && nums[i] == nums[i + 1]) {
          i++;
        }

        // CRITICAL: Skip duplicate right pointers
        // Move right pointer past all duplicates
        // Example: [... 1, 1, 1 ...] → skip to first 1
        while (i < j && nums[j] == nums[j - 1]) {
          j--;
        }

        // Move both pointers to continue searching
        // We've already processed this pair and its duplicates
        i++;
        j--;
      }
    }
    // When i >= j, we've exhausted all possible pairs
  }
}