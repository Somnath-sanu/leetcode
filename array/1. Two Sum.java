package array;

import java.util.*;

/*
 * Given an array of integers nums and an integer target, return indices of the
 * two numbers such that they add up to target.
 * 
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 * 
 * You can return the answer in any order.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 * 
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 * 
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 * 
 * 
 * Constraints:
 * 
 * 2 <= nums.length <= 10^4
 * -10^9 <= nums[i] <= 10^9
 * -10^9 <= target <= 10^9
 * Only one valid answer exists.
 */

/*
 * TWO SUM PATTERN - HashMap Complement Lookup
 * 
 * The classic array problem with an elegant HashMap solution.
 * 
 * Key insight: For each number x, we need to find if (target - x) exists.
 * Instead of searching the entire array each time, use HashMap for O(1) lookup!
 */

/*
 * BRUTE FORCE APPROACH (O(n²))
 * 
 * Check every pair of numbers:
 * for (int i = 0; i < n; i++) {
 *   for (int j = i + 1; j < n; j++) {
 *     if (nums[i] + nums[j] == target) {
 *       return [i, j];
 *     }
 *   }
 * }
 * 
 * Time: O(n²) - nested loops
 * Space: O(1) - no extra space
 * 
 * Problem: Too slow for large arrays!
 */

/*
 * OPTIMIZED APPROACH - HashMap (O(n))
 * 
 * For each number nums[i], we ask:
 * "Does (target - nums[i]) exist in the array?"
 * 
 * Instead of linear search, use HashMap:
 * - Key: the number value
 * - Value: the index where it appears
 * 
 * For each nums[i]:
 * 1. Calculate complement = target - nums[i]
 * 2. Check if complement exists in HashMap
 * 3. If yes, return [map.get(complement), i]
 * 4. If no, add nums[i] to HashMap and continue
 */

/*
 * CRITICAL INSIGHT: Why add elements DURING iteration?
 * 
 * Wrong approach (add all elements first):
 * for (int i = 0; i < n; i++) {
 *   map.put(nums[i], i);
 * }
 * for (int i = 0; i < n; i++) {
 *   int complement = target - nums[i];
 *   if (map.containsKey(complement)) {
 *     return [map.get(complement), i];
 *   }
 * }
 * 
 * Problem: nums = [3, 2, 4], target = 6
 * - At i=0, nums[0]=3, complement = 6-3 = 3
 * - HashMap already contains 3 at index 0
 * - Returns [0, 0] ❌ (using same element twice!)
 * 
 * Correct approach (add during iteration):
 * - Only add elements we've already processed
 * - When checking nums[i], the map only contains nums[0..i-1]
 * - This ensures we never use the same index twice!
 */

/*
 * EXAMPLE 1: nums = [2, 7, 11, 15], target = 9
 * 
 * Iteration 1 (i=0, nums[i]=2):
 * - complement = 9 - 2 = 7
 * - map = {} (empty)
 * - 7 not in map
 * - Add: map = {2: 0}
 * 
 * Iteration 2 (i=1, nums[i]=7):
 * - complement = 9 - 7 = 2
 * - map = {2: 0}
 * - 2 in map! ✅
 * - Return [map.get(2), 1] = [0, 1]
 * 
 * Answer: [0, 1] ✅
 */

/*
 * EXAMPLE 2: nums = [3, 2, 4], target = 6
 * 
 * Iteration 1 (i=0, nums[i]=3):
 * - complement = 6 - 3 = 3
 * - map = {} (empty)
 * - 3 not in map (we haven't added it yet!)
 * - Add: map = {3: 0}
 * 
 * Iteration 2 (i=1, nums[i]=2):
 * - complement = 6 - 2 = 4
 * - map = {3: 0}
 * - 4 not in map
 * - Add: map = {3: 0, 2: 1}
 * 
 * Iteration 3 (i=2, nums[i]=4):
 * - complement = 6 - 4 = 2
 * - map = {3: 0, 2: 1}
 * - 2 in map! ✅
 * - Return [map.get(2), 2] = [1, 2]
 * 
 * Answer: [1, 2] ✅
 */

/*
 * EXAMPLE 3: nums = [3, 3], target = 6
 * 
 * This tests the duplicate value case!
 * 
 * Iteration 1 (i=0, nums[i]=3):
 * - complement = 6 - 3 = 3
 * - map = {} (empty)
 * - 3 not in map yet
 * - Add: map = {3: 0}
 * 
 * Iteration 2 (i=1, nums[i]=3):
 * - complement = 6 - 3 = 3
 * - map = {3: 0}
 * - 3 in map! ✅ (the FIRST occurrence)
 * - Return [map.get(3), 1] = [0, 1]
 * 
 * Answer: [0, 1] ✅
 * 
 * Key: We find the previous 3 (index 0) when processing current 3 (index 1)
 * This correctly uses two different indices!
 */

/*
 * WHY THIS WORKS FOR DUPLICATES
 * 
 * When we have duplicate values:
 * - The HashMap stores the MOST RECENT index
 * - Example: [3, 3, 3], after processing all → map = {3: 2}
 * 
 * But we don't need all indices!
 * - We find the answer as soon as we see the second occurrence
 * - When at index 1, we look back and find index 0
 * - No need to continue further
 * 
 * The key: We're building the HashMap AS we go
 * - At position i, map contains [0..i-1]
 * - This prevents using the same index twice
 */

/*
 * EDGE CASES
 * 
 * Case 1: Negative numbers
 * - nums = [-1, -2, -3, -4], target = -6
 * - complement = -6 - (-3) = -3
 * - Works fine with HashMap!
 * 
 * Case 2: Zero
 * - nums = [0, 4, 3, 0], target = 0
 * - At i=3, complement = 0 - 0 = 0
 * - Finds the first 0 at index 0 ✅
 * 
 * Case 3: Large numbers
 * - nums = [1000000000, 999999999], target = 1999999999
 * - HashMap handles large integers fine
 * 
 * Case 4: Two elements (minimum size)
 * - nums = [1, 2], target = 3
 * - Always finds answer (problem guarantees solution exists)
 */

/*
 * WHY RETURN [-1, -1] AT END?
 * 
 * This is defensive programming.
 * - Problem guarantees a solution exists
 * - So we should never reach this line
 * - But Java requires a return statement for all paths
 * 
 * In practice:
 * - If we reach this line, input violated constraints
 * - Good to have for debugging
 */

/*
 * TIME AND SPACE COMPLEXITY
 * 
 * Time Complexity: O(n)
 * - Single pass through the array
 * - HashMap operations (put, get, containsKey) are O(1) average
 * - Total: O(n) iterations × O(1) per iteration = O(n)
 * 
 * Space Complexity: O(n)
 * - HashMap can store up to n elements in worst case
 * - Worst case: answer is [n-2, n-1], we store n-1 elements
 * - Example: nums = [1,2,3,4,5], target = 9 (4+5)
 *   - Store {1:0, 2:1, 3:2, 4:3} before finding answer
 */

/*
 * COMPARISON: Different Approaches
 * 
 * 1. Brute Force (two nested loops):
 *    Time: O(n²), Space: O(1)
 *    - Simple but too slow
 * 
 * 2. Sort + Two Pointers:
 *    Time: O(n log n), Space: O(n)
 *    - Sort array (O(n log n))
 *    - Use two pointers (O(n))
 *    - Problem: loses original indices after sorting!
 *    - Would need to store original indices
 * 
 * 3. HashMap (THIS SOLUTION):
 *    Time: O(n), Space: O(n)
 *    - OPTIMAL for this problem!
 *    - Trade space for time
 *    - Preserves indices naturally
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. HashMap for O(1) complement lookup
 *    → Check if (target - current) exists
 * 
 * 2. Add elements DURING iteration, not before
 *    → Prevents using same index twice
 *    → Map only contains previous elements
 * 
 * 3. For duplicates, find first occurrence
 *    → As soon as we see second duplicate, we find the first
 * 
 * 4. Trade space for time
 *    → O(n) space for O(n) time instead of O(n²)
 * 
 * 5. This pattern applies to many problems
 *    → "Two Sum" variations
 *    → Complement/pair lookup problems
 */

class Solution {
  public int[] twoSum(int[] nums, int target) {
    // CRITICAL: Don't add all elements to map first!
    //
    // If we add all elements before checking:
    // Example: nums = [3, 2, 4], target = 6
    // - At i=0, nums[0]=3, complement = 6-3 = 3
    // - Map already contains 3 at index 0
    // - Would return [0, 0] ❌ (using same element twice!)
    //
    // Correct approach: Add elements AS we iterate
    // - Map only contains elements we've already passed
    // - At position i, map has nums[0..i-1]
    // - This ensures we never use the same index twice!

    Map<Integer, Integer> map = new HashMap<>();

    // Single pass: check and add simultaneously
    for (int i = 0; i < nums.length; i++) {
      // Calculate the complement we need to find
      int complement = target - nums[i];

      // Check if complement exists in elements we've seen before
      if (map.containsKey(complement)) {
        // Found it! Return the pair of indices
        // map.get(complement) is the index of the complement (earlier in array)
        // i is the current index
        return new int[] { map.get(complement), i };
      }

      // Haven't found the complement yet
      // Add current element to map for future iterations
      // Key: value, Value: index
      map.put(nums[i], i);
    }

    // We should never reach here if input is valid
    // Problem guarantees exactly one solution exists
    // This is defensive programming / required by Java
    return new int[] { -1, -1 };
  }
}
