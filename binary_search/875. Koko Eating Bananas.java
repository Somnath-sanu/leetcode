package binary_search;

import java.util.*;

/*
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has
 * piles[i] bananas. The guards have gone and will come back in h hours.
 * 
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she
 * chooses some pile of bananas and eats k bananas from that pile. If the pile
 * has less than k bananas, she eats all of them instead and will not eat any
 * more bananas during this hour.
 * 
 * Koko likes to eat slowly but still wants to finish eating all the bananas
 * before the guards return.
 * 
 * Return the minimum integer k such that she can eat all the bananas within h
 * hours.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: piles = [3,6,7,11], h = 8
 * Output: 4
 * Example 2:
 * 
 * Input: piles = [30,11,23,4,20], h = 5
 * Output: 30
 * Example 3:
 * 
 * Input: piles = [30,11,23,4,20], h = 6
 * Output: 23
 * 
 * 
 * Constraints:
 * 
 * 1 <= piles.length <= 10^4
 * piles.length <= h <= 10^9
 * 1 <= piles[i] <= 10^9
 */

/*
 * BINARY SEARCH ON ANSWER PATTERN - Finding Minimum Speed
 * 
 * This is a classic "binary search on answer" problem.
 * We're searching for the MINIMUM eating speed k, not searching in an array.
 * 
 * Key observations:
 * 1. We need to find MINIMUM k (eating speed)
 * 2. The answer has a RANGE: [1, max(piles)]
 * 3. MONOTONIC property: If speed k works, all speeds > k also work
 * 
 * Instead of checking linearly: 1, 2, 3, 4, ... (TLE)
 * We binary search: pick middle speed, check if possible, adjust range
 */

/*
 * WHY BINARY SEARCH WORKS - MONOTONIC PROPERTY
 * 
 * If Koko can finish with speed k, she can DEFINITELY finish with speed k+1
 * If Koko CANNOT finish with speed k, she CANNOT finish with speed k-1 either
 * 
 * This creates a monotonic property:
 * [impossible, impossible, ... , possible, possible, possible]
 *  1          2          k-1    k        k+1       max
 *                               ↑
 *                          Find this boundary!
 * 
 * Example: piles = [3,6,7,11], h = 8
 * - Speed 1: needs 3+6+7+11 = 27 hours ❌ (> 8)
 * - Speed 2: needs 2+3+4+6 = 15 hours ❌ (> 8)
 * - Speed 3: needs 1+2+3+4 = 10 hours ❌ (> 8)
 * - Speed 4: needs 1+2+2+3 = 8 hours ✅ (= 8)
 * - Speed 5+: also works but we want MINIMUM
 * 
 * Binary search finds the minimum k where "possible" becomes true
 */

/*
 * SEARCH RANGE: [left, right]
 * 
 * Left boundary (minimum possible speed):
 * - Minimum is 1 banana per hour
 * - Can't eat slower than 1 banana/hour
 * 
 * Right boundary (maximum needed speed):
 * - Worst case: eat the largest pile in 1 hour
 * - Max speed needed = max(piles)
 * - Example: piles = [3,6,7,11] → max = 11
 * - If k = 11, Koko can eat any pile in 1 hour
 * 
 * We don't need speed > max(piles) because:
 * - Each pile takes at least 1 hour (one pile per hour rule)
 * - Speed > max means we finish every pile in 1 hour
 * - Total time = number of piles (can't do better)
 */

/*
 * CHECKING IF SPEED k IS POSSIBLE
 * 
 * For each pile, calculate hours needed:
 * - hours = ceil(pile / k)
 * - If pile = 7 and k = 4: hours = ceil(7/4) = ceil(1.75) = 2
 * 
 * Why ceil?
 * - If pile = 7 and k = 4:
 *   - Hour 1: eat 4 bananas, 3 left
 *   - Hour 2: eat 3 bananas, 0 left
 *   - Total: 2 hours (not 1.75!)
 * 
 * Sum all hours and check if total <= h
 */

/*
 * EXAMPLE: piles = [3, 6, 7, 11], h = 8
 * 
 * Range: l = 1, r = 11
 * 
 * Iteration 1:
 * - mid = 1 + (11-1)/2 = 6
 * - Check k = 6:
 *   - pile 3: ceil(3/6) = 1 hour
 *   - pile 6: ceil(6/6) = 1 hour
 *   - pile 7: ceil(7/6) = 2 hours
 *   - pile 11: ceil(11/6) = 2 hours
 *   - Total: 1+1+2+2 = 6 hours <= 8 ✅
 * - Possible! Try smaller speed
 * - r = mid = 6
 * 
 * Iteration 2:
 * - mid = 1 + (6-1)/2 = 3
 * - Check k = 3:
 *   - pile 3: ceil(3/3) = 1 hour
 *   - pile 6: ceil(6/3) = 2 hours
 *   - pile 7: ceil(7/3) = 3 hours
 *   - pile 11: ceil(11/3) = 4 hours
 *   - Total: 1+2+3+4 = 10 hours > 8 ❌
 * - Not possible! Need faster speed
 * - l = mid + 1 = 4
 * 
 * Iteration 3:
 * - mid = 4 + (6-4)/2 = 5
 * - Check k = 5:
 *   - pile 3: ceil(3/5) = 1 hour
 *   - pile 6: ceil(6/5) = 2 hours
 *   - pile 7: ceil(7/5) = 2 hours
 *   - pile 11: ceil(11/5) = 3 hours
 *   - Total: 1+2+2+3 = 8 hours <= 8 ✅
 * - Possible! Try smaller speed
 * - r = mid = 5
 * 
 * Iteration 4:
 * - mid = 4 + (5-4)/2 = 4
 * - Check k = 4:
 *   - pile 3: ceil(3/4) = 1 hour
 *   - pile 6: ceil(6/4) = 2 hours
 *   - pile 7: ceil(7/4) = 2 hours
 *   - pile 11: ceil(11/4) = 3 hours
 *   - Total: 1+2+2+3 = 8 hours <= 8 ✅
 * - Possible! Try smaller speed
 * - r = mid = 4
 * 
 * Iteration 5:
 * - l = 4, r = 4
 * - l == r, loop exits
 * 
 * Answer: 4 ✅
 */

/*
 * EXAMPLE: piles = [30, 11, 23, 4, 20], h = 5
 * 
 * Range: l = 1, r = 30
 * 
 * Key insight: h = 5 = number of piles
 * This means Koko has EXACTLY 1 hour per pile
 * She must finish each pile in 1 hour
 * Minimum speed = max(piles) = 30
 * 
 * Binary search will converge to 30:
 * - Any k < 30 fails (can't finish pile of 30 in 1 hour)
 * - k = 30 works (finish each pile in exactly 1 hour)
 */

/*
 * WHY l < r (not l <= r)?
 * 
 * We're finding the MINIMUM speed, and we use r = mid (not mid - 1)
 * 
 * Using l < r with r = mid:
 * - When l == r, we've found the minimum
 * - No infinite loop because:
 *   - If possible: r = mid (r decreases or stays same)
 *   - If not possible: l = mid + 1 (l increases)
 *   - Eventually l == r
 * 
 * Alternative: Use l <= r with ans variable (like Solution2 in previous problem)
 * - Would need to track ans = mid when possible
 * - Then use r = mid - 1
 * - Both approaches work!
 */

/*
 * WHY r = mid (not r = mid - 1)?
 * 
 * Because mid COULD BE the answer!
 * 
 * When possible(mid) returns true:
 * - mid is a valid speed
 * - But we want MINIMUM, so we search left
 * - mid might be the minimum, so we keep it: r = mid
 * 
 * When possible(mid) returns false:
 * - mid is too slow
 * - We need faster speed, so we search right
 * - mid is definitely not the answer: l = mid + 1
 */

/*
 * CEILING DIVISION TRICK
 * 
 * To calculate ceil(a/b) without using Math.ceil:
 * 
 * Method 1: Math.ceil(pile / (double) k)
 * - Cast to double for floating point division
 * - Use Math.ceil to round up
 * 
 * Method 2: (pile + k - 1) / k
 * - Integer arithmetic only
 * - Example: pile = 7, k = 4
 *   - (7 + 4 - 1) / 4 = 10 / 4 = 2 ✅
 * - Example: pile = 8, k = 4
 *   - (8 + 4 - 1) / 4 = 11 / 4 = 2 ✅
 * 
 * Both methods work, choose based on preference
 */

/*
 * EARLY TERMINATION OPTIMIZATION
 * 
 * In possible() function:
 * - We can return false as soon as time > h
 * - No need to check remaining piles
 * - Saves computation when k is too slow
 * 
 * Example: piles = [30,11,23,4,20], h = 5, k = 1
 * - pile 30: needs 30 hours
 * - Already > 5, return false immediately!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Binary Search on Answer pattern
 *    → Search for minimum speed, not search in array
 *    → Range: [1, max(piles)]
 * 
 * 2. Monotonic property enables binary search
 *    → If speed k works, all speeds > k work
 *    → If speed k fails, all speeds < k fail
 * 
 * 3. Use l < r with r = mid
 *    → Prevents skipping the minimum
 *    → Loop exits when l == r
 * 
 * 4. Ceiling division for hours calculation
 *    → ceil(pile / k) = hours needed for that pile
 *    → Can use Math.ceil or (pile + k - 1) / k
 * 
 * 5. Early termination optimization
 *    → Return false as soon as time > h
 * 
 * 6. Time: O(n * log(max)), Space: O(1)
 *    → Binary search: log(max) iterations
 *    → Each check: O(n) to iterate piles
 */

class Solution {
  public int minEatingSpeed(int[] piles, int h) {
    int n = piles.length;

    // Right boundary: maximum speed needed = max(piles)
    // If k = max(piles), Koko can eat any pile in 1 hour
    int r = Arrays.stream(piles).max().getAsInt();

    // APPROACH 1: Linear Search (TLE - Time Limit Exceeded)
    // Why TLE? If max(piles) is large (10^9), checking each speed takes too long
    //
    // for (int k = 1; k <= r; k++) {
    // if (possible(k, piles, h)) {
    // return k; // First k that works is the minimum
    // }
    // }
    // return 0;

    // APPROACH 2: Binary Search with ans variable
    // Track the minimum valid speed found so far
    //
    // int l = 1;
    // int ans = 0;
    //
    // while (l <= r) {
    // int mid = l + (r - l) / 2;
    //
    // if (possible(mid, piles, h)) {
    // ans = mid; // Save this valid speed
    // r = mid - 1; // Try to find smaller speed
    // } else {
    // l = mid + 1; // Need faster speed
    // }
    // }
    //
    // return ans;

    // APPROACH 3: Binary Search without extra variable (OPTIMAL)
    // Use l < r with r = mid to avoid needing ans variable

    int l = 1; // Minimum possible speed

    while (l < r) { // Loop until l == r (found minimum)
      int mid = l + (r - l) / 2;

      if (possible(mid, piles, h)) {
        // mid is a valid speed, but we want minimum
        // mid COULD BE the answer, so keep it
        r = mid; // Search left half (try smaller speeds)
      } else {
        // mid is too slow, need faster speed
        // mid is definitely NOT the answer
        l = mid + 1; // Search right half (try faster speeds)
      }
    }

    // When l == r, we've found the minimum speed
    return l;
  }

  /**
   * Check if Koko can eat all bananas with speed k within h hours
   * 
   * @param k     Eating speed (bananas per hour)
   * @param piles Array of banana piles
   * @param h     Maximum hours available
   * @return true if possible, false otherwise
   */
  private boolean possible(int k, int[] piles, int h) {
    int time = 0; // Total hours needed

    for (int pile : piles) {
      // Calculate hours needed for this pile
      // ceil(pile / k) = hours to finish this pile
      // Example: pile = 7, k = 4 → ceil(7/4) = 2 hours
      time += (int) Math.ceil(pile / (double) k);

      // Early termination: if already exceeded h, no point continuing
      if (time > h) {
        return false;
      }
    }

    // Check if total time is within limit
    return true; // time <= h (already checked in loop)
  }
}