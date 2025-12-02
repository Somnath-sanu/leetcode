package binary_search;

/*
 * You are given a floating-point number hour, representing the amount of time
 * you have to reach the office. To commute to the office, you must take n
 * trains in sequential order. You are also given an integer array dist of
 * length n, where dist[i] describes the distance (in kilometers) of the ith
 * train ride.
 * 
 * Each train can only depart at an integer hour, so you may need to wait in
 * between each train ride.
 * 
 * For example, if the 1st train ride takes 1.5 hours, you must wait for an
 * additional 0.5 hours before you can depart on the 2nd train ride at the 2
 * hour mark.
 * Return the minimum positive integer speed (in kilometers per hour) that all
 * the trains must travel at for you to reach the office on time, or -1 if it is
 * impossible to be on time.
 * 
 * Tests are generated such that the answer will not exceed 10^7 and hour will
 * have at most two digits after the decimal point.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: dist = [1,3,2], hour = 6
 * Output: 1
 * Explanation: At speed 1:
 * - The first train ride takes 1/1 = 1 hour.
 * - Since we are already at an integer hour, we depart immediately at the 1
 * hour mark. The second train takes 3/1 = 3 hours.
 * - Since we are already at an integer hour, we depart immediately at the 4
 * hour mark. The third train takes 2/1 = 2 hours.
 * - You will arrive at exactly the 6 hour mark.
 * Example 2:
 * 
 * Input: dist = [1,3,2], hour = 2.7
 * Output: 3
 * Explanation: At speed 3:
 * - The first train ride takes 1/3 = 0.33333 hours.
 * - Since we are not at an integer hour, we wait until the 1 hour mark to
 * depart. The second train ride takes 3/3 = 1 hour.
 * - Since we are already at an integer hour, we depart immediately at the 2
 * hour mark. The third train takes 2/3 = 0.66667 hours.
 * - You will arrive at the 2.66667 hour mark.
 * Example 3:
 * 
 * Input: dist = [1,3,2], hour = 1.9
 * Output: -1
 * Explanation: It is impossible because the earliest the third train can depart
 * is at the 2 hour mark.
 * 
 * 
 * Constraints:
 * 
 * n == dist.length
 * 1 <= n <= 10^5
 * 1 <= dist[i] <= 10^5
 * 1 <= hour <= 10^9
 * There will be at most two digits after the decimal point in hour.
 */

/*
 * BINARY SEARCH ON ANSWER PATTERN - Finding Minimum Speed
 * 
 * Similar to Koko Eating Bananas, but with a CRITICAL difference:
 * - We must wait for integer hours between trains
 * - Last train doesn't need to wait (can arrive at fractional hour)
 * 
 * Key observations:
 * 1. We need to find MINIMUM speed
 * 2. The answer has a RANGE: [1, 10^7]
 * 3. MONOTONIC property: If speed k works, all speeds > k also work
 */

/*
 * KEY INSIGHT: Ceiling vs No Ceiling
 * 
 * For INTERMEDIATE trains (not the last):
 * - Must wait until next integer hour to depart
 * - time = ceil(dist / speed)
 * - Example: dist=1, speed=3 → 0.33 hours → wait until hour 1
 * 
 * For the LAST train:
 * - No need to wait, we just arrive
 * - time = dist / speed (exact, no ceiling)
 * - Example: dist=2, speed=3 → 0.67 hours (arrive at 2.67)
 * 
 * This is the CRITICAL difference from Koko Eating Bananas!
 */

/*
 * EXAMPLE: dist = [1, 3, 2], hour = 2.7, speed = 3
 * 
 * Train 1 (intermediate):
 * - Distance: 1 km
 * - Time: 1/3 = 0.33 hours
 * - Must wait until hour 1 (ceiling!)
 * - Cumulative time: ceil(0.33) = 1 hour
 * 
 * Train 2 (intermediate):
 * - Distance: 3 km
 * - Time: 3/3 = 1 hour
 * - Already at integer hour (1 + 1 = 2)
 * - Cumulative time: ceil(2) = 2 hours
 * 
 * Train 3 (LAST - no ceiling!):
 * - Distance: 2 km
 * - Time: 2/3 = 0.67 hours
 * - NO WAIT, just arrive
 * - Cumulative time: 2 + 0.67 = 2.67 hours
 * 
 * 2.67 <= 2.7 ✅ Possible!
 */

/*
 * WHY IMPOSSIBLE CASE EXISTS?
 * 
 * Example: dist = [1, 3, 2], hour = 1.9
 * 
 * Minimum time needed (even at infinite speed):
 * - Train 1: 0 hours (instant) → wait until hour 1
 * - Train 2: 0 hours (instant) → wait until hour 2
 * - Train 3: 0 hours (instant) → arrive at hour 2
 * 
 * Minimum possible time: 2 hours
 * Required time: 1.9 hours
 * 2 > 1.9 → IMPOSSIBLE! ❌
 * 
 * General rule: If hour < (n - 1), it's impossible
 * - We need at least (n-1) hours for waiting between trains
 * - Example: 3 trains need at least 2 hours of waiting
 */

/*
 * SEARCH RANGE: [left, right]
 * 
 * Left boundary:
 * - Minimum speed is 1 km/h
 * 
 * Right boundary:
 * - Problem states answer won't exceed 10^7
 * - We use r = 10^7 as upper bound
 * 
 * Why 10^7?
 * - If no solution exists within [1, 10^7], return -1
 * - This is given in the problem constraints
 */

/*
 * EXAMPLE: dist = [1, 3, 2], hour = 6
 * 
 * Range: l = 1, r = 10^7
 * 
 * Iteration 1:
 * - mid = 5000000
 * - Check speed = 5000000:
 *   - Train 1: 1/5000000 ≈ 0 → ceil = 1
 *   - Train 2: 3/5000000 ≈ 0 → ceil = 1
 *   - Train 3: 2/5000000 ≈ 0 (no ceil)
 *   - Total: 1 + 1 + 0 = 2 hours <= 6 ✅
 * - Possible! Try smaller speed
 * - ans = 5000000, r = 4999999
 * 
 * ... (binary search continues)
 * 
 * Eventually converges to speed = 1:
 * - Train 1: 1/1 = 1 → ceil = 1
 * - Train 2: 3/1 = 3 → ceil = 3
 * - Train 3: 2/1 = 2 (no ceil)
 * - Total: 1 + 3 + 2 = 6 hours <= 6 ✅
 * 
 * Answer: 1 ✅
 */

/*
 * EXAMPLE: dist = [1, 3, 2], hour = 2.7
 * 
 * Check speed = 3:
 * - Train 1: 1/3 = 0.33 → ceil = 1
 * - Train 2: 3/3 = 1 → ceil = 1
 * - Train 3: 2/3 = 0.67 (no ceil)
 * - Total: 1 + 1 + 0.67 = 2.67 <= 2.7 ✅
 * 
 * Check speed = 2:
 * - Train 1: 1/2 = 0.5 → ceil = 1
 * - Train 2: 3/2 = 1.5 → ceil = 2
 * - Train 3: 2/2 = 1 (no ceil)
 * - Total: 1 + 2 + 1 = 4 > 2.7 ❌
 * 
 * Answer: 3 ✅ (minimum speed that works)
 */

/*
 * CRITICAL BUG TO AVOID
 * 
 * WRONG approach (ceiling for ALL trains):
 * for (int d : dist) {
 *   time += Math.ceil(d / (double) k);
 * }
 * 
 * Why wrong? Last train gets ceiling too!
 * - dist = [1,3,2], hour = 2.7, speed = 3
 * - Train 3: ceil(2/3) = ceil(0.67) = 1
 * - Total: 1 + 1 + 1 = 3 > 2.7 ❌ (should be 2.67!)
 * 
 * CORRECT approach (ceiling only for intermediate):
 * for (int i = 0; i < dist.length; i++) {
 *   time += dist[i] / (double) k;
 *   if (i < dist.length - 1) {  // Not the last train
 *     time = Math.ceil(time);
 *   }
 * }
 */

/*
 * ALTERNATIVE IMPLEMENTATION
 * 
 * Instead of checking index, we can:
 * 1. Add exact time for current train
 * 2. Apply ceiling to cumulative time
 * 3. Skip ceiling for last train
 * 
 * This is what the provided code does:
 * - time += d / (double) k;  // Add exact time
 * - time = Math.ceil(time);  // Round up cumulative
 * 
 * But we need to skip ceiling for last train!
 * The code applies ceiling AFTER adding each train.
 * 
 * For last train, we should NOT apply ceiling.
 * The provided code has a subtle issue - it applies ceiling
 * even for the last train, which is incorrect.
 */

/*
 * CORRECTED LOGIC
 * 
 * We need to track which train we're on:
 * - For trains 0 to n-2: apply ceiling
 * - For train n-1 (last): no ceiling
 * 
 * Better approach:
 * for (int i = 0; i < dist.length; i++) {
 *   time += dist[i] / (double) k;
 *   
 *   if (time > h) return false;  // Early termination
 *   
 *   if (i < dist.length - 1) {   // Not the last train
 *     time = Math.ceil(time);    // Wait until next hour
 *   }
 * }
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Binary Search on Answer pattern
 *    → Search for minimum speed
 *    → Range: [1, 10^7]
 * 
 * 2. CRITICAL: Ceiling only for intermediate trains
 *    → Intermediate trains: must wait until next integer hour
 *    → Last train: can arrive at fractional hour
 * 
 * 3. Impossible case check
 *    → If hour < (n - 1), return -1 immediately
 *    → Need at least (n-1) hours for waiting
 * 
 * 4. Early termination
 *    → Return false as soon as time > hour
 * 
 * 5. Use ans variable with l <= r
 *    → Track minimum valid speed
 *    → Return -1 if no valid speed found
 * 
 * 6. Time: O(n * log(10^7)), Space: O(1)
 *    → Binary search: log(10^7) ≈ 23 iterations
 *    → Each check: O(n) to iterate trains
 */

class Solution {
  public int minSpeedOnTime(int[] dist, double hour) {
    int n = dist.length;

    // Early check: impossible case
    // Need at least (n-1) hours for waiting between trains
    // Example: 3 trains need at least 2 hours
    if (hour < n - 1) {
      return -1;
    }

    // Right boundary: problem guarantees answer <= 10^7
    int r = (int) 1e7;

    // Left boundary: minimum speed is 1
    int l = 1;

    // Track the minimum valid speed found
    int ans = -1;

    // Binary search for minimum speed
    while (l <= r) {
      int mid = l + (r - l) / 2;

      if (possible(mid, dist, hour)) {
        // mid is a valid speed
        ans = mid; // Save this answer
        r = mid - 1; // Try to find smaller speed
      } else {
        // mid is too slow
        l = mid + 1; // Need faster speed
      }
    }

    // Return the minimum speed found, or -1 if none exists
    return ans;
  }

  /**
   * Check if we can reach office on time with speed k
   * 
   * CRITICAL: Apply ceiling only for intermediate trains!
   * - Intermediate trains: must wait until next integer hour
   * - Last train: can arrive at fractional hour
   * 
   * @param k    Speed in km/h
   * @param dist Array of distances
   * @param h    Maximum hours available
   * @return true if possible, false otherwise
   */
  private boolean possible(int k, int[] dist, double h) {
    double time = 0;

    for (int i = 0; i < dist.length; i++) {
      // Add time for current train
      // time = distance / speed
      time += dist[i] / (double) k;

      // Early termination: if already exceeded limit
      if (time > h) {
        return false;
      }

      // CRITICAL: Apply ceiling only for intermediate trains
      // For last train (i == dist.length - 1), skip ceiling
      if (i < dist.length - 1) {
        // Must wait until next integer hour to catch next train
        time = Math.ceil(time);
      }
      // For last train, we just arrive (no waiting needed)
    }

    // Check if total time is within limit
    return time <= h;
  }
}