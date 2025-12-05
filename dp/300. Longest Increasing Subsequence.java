package dp;

import java.util.*;

/*
 * Given an integer array nums, return the length of the longest strictly
 * increasing subsequence.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [10,9,2,5,3,7,101,18]
 * Output: 4
 * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the
 * length is 4.
 * Example 2:
 * 
 * Input: nums = [0,1,0,3,2,3]
 * Output: 4
 * Example 3:
 * 
 * Input: nums = [7,7,7,7,7,7,7]
 * Output: 1
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 2500
 * -10^4 <= nums[i] <= 10^4
 * 
 * 
 * Follow up: Can you come up with an algorithm that runs in O(n log(n)) time
 * complexity?
 */

/*
 * LONGEST INCREASING SUBSEQUENCE (LIS) PATTERN
 * 
 * Two approaches to solve this classic problem:
 * 
 * APPROACH 1: DP with Path Reconstruction - O(n²)
 * - lis[i] = length of LIS ending at index i
 * - prev[i] = index of previous element in LIS
 * - Can reconstruct the actual subsequence
 * 
 * APPROACH 2: Binary Search (Patience Sorting) - O(n log n)
 * - Maintain a list of smallest ending elements
 * - Use binary search to find replacement position
 * - Gets length only (harder to reconstruct path)
 */

/*
 * APPROACH 1: DP - THE INTUITION
 * 
 * State: lis[i] = length of longest increasing subsequence ending at index i
 * 
 * Key insight:
 * - For each position i, we look at ALL previous positions (0 to i-1)
 * - If nums[prev] < nums[i], we can extend the LIS ending at prev
 * - lis[i] = max(lis[i], 1 + lis[prev]) for all valid prev
 * 
 * Base case: lis[i] = 1 for all i (each element forms LIS of length 1)
 * 
 * Answer: max(lis[i]) for all i
 */

/*
 * EXAMPLE: nums = [10, 9, 2, 5, 3, 7, 101, 18]
 * 
 * Building lis array:
 * 
 * i=0: nums[0]=10
 * - No previous elements
 * - lis[0] = 1
 * 
 * i=1: nums[1]=9
 * - Check prev=0: nums[0]=10 > 9 ❌ (not increasing)
 * - lis[1] = 1
 * 
 * i=2: nums[2]=2
 * - Check prev=0: nums[0]=10 > 2 ❌
 * - Check prev=1: nums[1]=9 > 2 ❌
 * - lis[2] = 1
 * 
 * i=3: nums[3]=5
 * - Check prev=0: nums[0]=10 > 5 ❌
 * - Check prev=1: nums[1]=9 > 5 ❌
 * - Check prev=2: nums[2]=2 < 5 ✅ → lis[3] = 1 + lis[2] = 2
 * - lis[3] = 2 (subsequence: [2, 5])
 * 
 * i=4: nums[4]=3
 * - Check prev=2: nums[2]=2 < 3 ✅ → lis[4] = 1 + lis[2] = 2
 * - lis[4] = 2 (subsequence: [2, 3])
 * 
 * i=5: nums[5]=7
 * - Check prev=2: nums[2]=2 < 7 ✅ → lis[5] = 1 + lis[2] = 2
 * - Check prev=3: nums[3]=5 < 7 ✅ → lis[5] = max(2, 1+2) = 3
 * - Check prev=4: nums[4]=3 < 7 ✅ → lis[5] = max(3, 1+2) = 3
 * - lis[5] = 3 (subsequence: [2, 5, 7])
 * 
 * i=6: nums[6]=101
 * - Best: extends [2, 5, 7]
 * - lis[6] = 4 (subsequence: [2, 5, 7, 101])
 * 
 * i=7: nums[7]=18
 * - Best: extends [2, 5, 7]
 * - lis[7] = 4 (subsequence: [2, 5, 7, 18])
 * 
 * Final lis: [1, 1, 1, 2, 2, 3, 4, 4]
 * Answer: max = 4 ✅
 */

/*
 * PATH RECONSTRUCTION - USING prev ARRAY
 * 
 * To reconstruct the actual subsequence:
 * - prev[i] = index of previous element in LIS ending at i
 * - Initially: prev[i] = i (points to itself)
 * - When updating: prev[i] = p (where p is the best previous index)
 * 
 * After building lis and prev:
 * 1. Find index with maximum lis value (maxIdx)
 * 2. Backtrack using prev array:
 *    - Add nums[maxIdx] to result
 *    - maxIdx = prev[maxIdx]
 *    - Repeat until prev[maxIdx] == maxIdx
 * 
 * This gives us the actual LIS!
 */

/*
 * EXAMPLE: Path reconstruction for [10, 9, 2, 5, 3, 7, 101, 18]
 * 
 * After DP:
 * lis:  [1, 1, 1, 2, 2, 3, 4, 4]
 * prev: [0, 1, 2, 2, 2, 3, 5, 5]
 *                          ↑ maxIdx
 * 
 * maxIdx = 6 (lis[6] = 4)
 * 
 * Backtrack:
 * - Add nums[6] = 101
 * - maxIdx = prev[6] = 5
 * - Add nums[5] = 7
 * - maxIdx = prev[5] = 3
 * - Add nums[3] = 5
 * - maxIdx = prev[3] = 2
 * - Add nums[2] = 2
 * - maxIdx = prev[2] = 2 (points to itself, stop)
 * 
 * Result (in reverse): [2, 5, 7, 101] ✅
 */

/*
 * APPROACH 2: BINARY SEARCH - THE GENIUS INSIGHT
 * 
 * Key observation: We don't need to track ALL possible LIS
 * We only need to track the SMALLEST ending element for each length
 * 
 * Maintain list res where:
 * - res[i] = smallest ending element of LIS with length i+1
 * 
 * For each new number:
 * 1. If num > last element in res: append (extends LIS)
 * 2. Else: find position to replace (binary search)
 *    - Replace first element >= num
 *    - This keeps res[i] as small as possible
 * 
 * Final answer: res.size()
 */

/*
 * WHY BINARY SEARCH WORKS - PATIENCE SORTING
 * 
 * Imagine playing cards:
 * - Place each card on the leftmost pile where card on top > current card
 * - If no such pile, create new pile on the right
 * 
 * Number of piles = LIS length!
 * 
 * Example: [10, 9, 2, 5, 3, 7, 101, 18]
 * 
 * Step 1: 10 → [10]
 * Step 2: 9 → [9] (replace 10, smaller ending)
 * Step 3: 2 → [2] (replace 9, smaller ending)
 * Step 4: 5 → [2, 5] (5 > 2, extend)
 * Step 5: 3 → [2, 3] (replace 5, keep smaller)
 * Step 6: 7 → [2, 3, 7] (7 > 3, extend)
 * Step 7: 101 → [2, 3, 7, 101] (extend)
 * Step 8: 18 → [2, 3, 7, 18] (replace 101)
 * 
 * Final res: [2, 3, 7, 18] (length 4) ✅
 * 
 * Note: res is NOT always the actual LIS!
 * It's just a list that maintains the right length.
 */

/*
 * WHY REPLACEMENT KEEPS LENGTH CORRECT
 * 
 * When we replace res[idx] with smaller num:
 * - We're not constructing the actual LIS
 * - We're keeping the smallest possible endings
 * - This maximizes chances of future extensions
 * 
 * Example: res = [2, 5, 7], new num = 3
 * - 3 < 5, so we replace: res = [2, 3, 7]
 * - Now res is NOT a valid increasing sequence!
 * - But length is still correct (3)
 * - And we have better chance to extend (3 < 5)
 */

/*
 * BINARY SEARCH IMPLEMENTATION
 * 
 * We search for:
 * - First element >= target
 * 
 * Why?
 * - We want to replace the smallest element that's >= num
 * - This keeps each position as small as possible
 * 
 * Standard binary search:
 * - If arr[mid] >= target: search left (could be answer)
 * - If arr[mid] < target: search right
 * - Return l (first position >= target)
 */

/*
 * COMPARISON: DP vs Binary Search
 * 
 * DP Approach (O(n²)):
 * - Pros: Can reconstruct actual LIS easily
 * - Cons: Slower for large arrays
 * - Best for: When you need the actual subsequence
 * 
 * Binary Search (O(n log n)):
 * - Pros: Much faster
 * - Cons: Harder to reconstruct LIS (possible but complex)
 * - Best for: When you only need the length
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. DP approach: lis[i] = LIS length ending at i
 *    → O(n²) time, can reconstruct path
 * 
 * 2. Use prev array for path reconstruction
 *    → Track best previous index
 *    → Backtrack from maximum
 * 
 * 3. Binary search: maintain smallest endings
 *    → O(n log n) time
 *    → Result list gives length, not actual LIS
 * 
 * 4. Patience sorting analogy
 *    → Number of piles = LIS length
 * 
 * 5. Replacement maintains correct length
 *    → Even though list isn't strictly increasing
 */

class Solution {
  public int lengthOfLIS(int[] nums) {
    // APPROACH 1: DP WITH PATH RECONSTRUCTION
    // T.C → O(n²)
    // S.C → O(n)

    // BASIC DP (LENGTH ONLY) - COMMENTED OUT:
    // int n = nums.length;
    // int[] lis = new int[n];
    // Arrays.fill(lis, 1);
    // int max = 1;
    //
    // for(int i = 1; i < n; i++){
    // for(int prev = 0; prev < i; prev++) {
    // if(nums[prev] < nums[i]) {
    // lis[i] = Math.max(lis[i], 1 + lis[prev]);
    // }
    // }
    // max = Math.max(max, lis[i]);
    // }
    // return max;

    // ENHANCED DP: With path reconstruction to print actual subsequence

    int n = nums.length;

    // lis[i] = length of LIS ending at index i
    int[] lis = new int[n];

    // prev[i] = index of previous element in LIS ending at i
    // Used for path reconstruction
    int[] prev = new int[n];

    // Initialize:
    // - Each element forms LIS of length 1
    // - Each element points to itself initially
    for (int i = 0; i < n; i++) {
      lis[i] = 1;
      prev[i] = i; // Points to itself (no previous element)
    }

    int max = 1; // Maximum LIS length found
    int maxIdx = 0; // Index where maximum LIS ends

    // For each position i, check all previous positions
    for (int i = 1; i < n; i++) {
      for (int p = 0; p < i; p++) {
        // Can we extend LIS ending at p by adding nums[i]?
        if (nums[p] < nums[i]) {
          // Check if this gives better LIS at position i
          if (lis[i] < 1 + lis[p]) {
            prev[i] = p; // Track where we came from
            lis[i] = 1 + lis[p]; // Update LIS length
          }
        }
      }

      // Track maximum LIS and where it ends
      if (lis[i] > max) {
        max = lis[i];
        maxIdx = i;
      }
    }

    // RECONSTRUCT THE ACTUAL LIS (Optional - for printing)
    // Backtrack using prev array to get the subsequence
    ArrayList<Integer> list = new ArrayList<>();
    list.add(nums[maxIdx]); // Start from maximum

    // Follow prev pointers until we reach starting point
    while (prev[maxIdx] != maxIdx) {
      maxIdx = prev[maxIdx];
      list.add(0, nums[maxIdx]); // Add to front (building in order)
    }

    // Print the actual LIS
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ,");
    }

    return max;
  }
}

class Solution2 {
  public int lengthOfLIS(int[] nums) {
    // APPROACH 2: BINARY SEARCH (PATIENCE SORTING)
    // T.C → O(n log n)
    // S.C → O(n)

    // Maintain res[] in sorted order
    // res[i] = smallest ending element of LIS with length i+1
    List<Integer> res = new ArrayList<>();

    for (int num : nums) {
      if (res.isEmpty() || res.get(res.size() - 1) < num) {
        // Case 1: num is larger than all elements
        // Extend the LIS by appending num
        res.add(num);

      } else {
        // Case 2: num can replace some element
        // Find the leftmost position where res[idx] >= num
        // Replace it to keep smallest possible ending
        int idx = binarySearch(res, num);
        res.set(idx, num);
      }
    }

    // Debug: Print the final res list
    // NOTE: This is NOT necessarily the actual LIS!
    // It's a list that maintains the correct length
    print(res);

    return res.size();
  }

  /**
   * Binary search to find first element >= target
   * 
   * This finds the position where we should replace
   * to keep the smallest possible ending elements
   * 
   * @param arr    Sorted list
   * @param target Value to search for
   * @return Index of first element >= target
   */
  private int binarySearch(List<Integer> arr, int target) {
    int l = 0;
    int r = arr.size() - 1;

    // Standard binary search for first element >= target
    while (l <= r) {
      int mid = l + (r - l) / 2;

      if (arr.get(mid) == target) {
        return mid; // Found exact match

      } else if (arr.get(mid) > target) {
        r = mid - 1; // Target could be on left

      } else {
        l = mid + 1; // Target is on right
      }
    }

    // l is the position of first element >= target
    return l;
  }

  /**
   * Helper to print the res list
   * 
   * WARNING: This list is NOT the actual LIS!
   * It maintains correct length but elements may not form
   * a valid increasing subsequence from the original array
   */
  private void print(List<Integer> arr) {
    for (int val : arr) {
      System.out.print(val + " ,");
    }
    System.out.println();
  }
}