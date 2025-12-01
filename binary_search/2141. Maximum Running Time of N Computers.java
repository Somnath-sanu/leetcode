package binary_search;

/*
 * You have n computers. You are given the integer n and a 0-indexed integer
 * array batteries where the ith battery can run a computer for batteries[i]
 * minutes. You are interested in running all n computers simultaneously using
 * the given batteries.
 * 
 * Initially, you can insert at most one battery into each computer. After that
 * and at any integer time moment, you can remove a battery from a computer and
 * insert another battery any number of times. The inserted battery can be a
 * totally new battery or a battery from another computer. You may assume that
 * the removing and inserting processes take no time.
 * 
 * Note that the batteries cannot be recharged.
 * 
 * Return the maximum number of minutes you can run all the n computers
 * simultaneously.
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: n = 2, batteries = [3,3,3]
 * Output: 4
 * Explanation:
 * Initially, insert battery 0 into the first computer and battery 1 into the
 * second computer.
 * After two minutes, remove battery 1 from the second computer and insert
 * battery 2 instead. Note that battery 1 can still run for one minute.
 * At the end of the third minute, battery 0 is drained, and you need to remove
 * it from the first computer and insert battery 1 instead.
 * By the end of the fourth minute, battery 1 is also drained, and the first
 * computer is no longer running.
 * We can run the two computers simultaneously for at most 4 minutes, so we
 * return 4.
 * 
 * Example 2:
 * 
 * 
 * Input: n = 2, batteries = [1,1,1,1]
 * Output: 2
 * Explanation:
 * Initially, insert battery 0 into the first computer and battery 2 into the
 * second computer.
 * After one minute, battery 0 and battery 2 are drained so you need to remove
 * them and insert battery 1 into the first computer and battery 3 into the
 * second computer.
 * After another minute, battery 1 and battery 3 are also drained so the first
 * and second computers are no longer running.
 * We can run the two computers simultaneously for at most 2 minutes, so we
 * return 2.
 * 
 * 
 * Constraints:
 * 
 * 1 <= n <= batteries.length <= 10^5
 * 1 <= batteries[i] <= 10^9
 */

/*
 * BINARY SEARCH ON ANSWER PATTERN 
 * 
 * This is a classic "binary search on answer" problem, not "binary search on array"
 * 
 * Key Characteristics:
 * 1. We're looking for MAXIMUM time (not searching for a value in array)
 * 2. The answer has a RANGE: [min_time, max_time]
 * 3. MONOTONIC property: If we can run for time T, we can run for any time < T
 * 
 * Instead of checking linearly: 1, 2, 3, 4, 5, ... (TLE)
 * We binary search: pick middle value, check if possible, adjust range
 */

/*
 * WHY BINARY SEARCH WORKS HERE - MONOTONIC PROPERTY
 * 
 * If we can run n computers for T minutes, we can DEFINITELY run for T-1 minutes
 * If we CANNOT run for T minutes, we CANNOT run for T+1 minutes either
 * 
 * This creates a monotonic property:
 * [possible, possible, possible, ... , impossible, impossible, impossible]
 *  1         2         3         4     5           6            7
 *                                 ↑
 *                            Find this boundary!
 * 
 * Example: n=2, batteries=[3,3,3], total=9
 * - Can run for 1 min? YES (need 2, have 9)
 * - Can run for 2 min? YES (need 4, have 9)
 * - Can run for 3 min? YES (need 6, have 9)
 * - Can run for 4 min? YES (need 8, have 9)
 * - Can run for 5 min? NO (need 10, have 9)
 * 
 * Binary search finds the maximum T where "possible" is true
 */

/*
 * KEY INSIGHT: Battery Swapping
 * 
 * We can SWAP batteries at any time with NO cost!
 * This means we can treat all battery power as a POOL of energy
 * 
 * Example: batteries = [3, 3, 3], n = 2
 * 
 * Traditional thinking (wrong):
 * - Computer 1 uses battery 0 (3 min)
 * - Computer 2 uses battery 1 (3 min)
 * - Battery 2 is wasted
 * - Total: 3 minutes
 * 
 * With swapping (correct):
 * - Minute 1-2: Comp1 uses bat0, Comp2 uses bat1
 * - Minute 3: Comp1 uses bat2, Comp2 uses remaining bat1
 * - Minute 4: Comp1 uses remaining bat2, Comp2 uses remaining bat0
 * - Total: 4 minutes ✅
 * 
 * The key: We can distribute total battery power across all computers!
 */

/*
 * SEARCH RANGE: [left, right]
 * 
 * Left boundary (minimum possible time):
 * - Could be 1 (safest)
 * - Or min(batteries) (optimization)
 * - We use min(batteries) to reduce search space
 * 
 * Right boundary (maximum possible time):
 * - Best case: distribute ALL battery power evenly across n computers
 * - Total power = sum of all batteries
 * - Max time per computer = total_power / n
 * - Example: batteries=[3,3,3], n=2 → max = 9/2 = 4
 * 
 * We can't do better than this because:
 * - We have finite total energy
 * - We need to power n computers simultaneously
 * - Even with perfect distribution, max time = total/n
 */

/*
 * CHECKING IF TIME T IS POSSIBLE
 * 
 * Question: Can we run n computers for T minutes each?
 * 
 * Total energy needed: n * T
 * Total energy available: sum of all batteries
 * 
 * Greedy Strategy:
 * For each battery with power B:
 * - We can use min(B, T) towards our goal
 * - Why min(B, T)? Because:
 *   - If B >= T: we only need T from this battery (rest is wasted per computer)
 *   - If B < T: we use all B from this battery
 * 
 * Algorithm:
 * target = n * T
 * for each battery B:
 *   target -= min(B, T)
 * if target <= 0: possible!
 */

/*
 * WHY min(battery, target_time)?
 * 
 * Example: n=3, T=5, batteries=[10, 10, 3, 5]
 * 
 * Total needed: 3 * 5 = 15 minutes
 * 
 * Battery 0 (10 min):
 * - Can contribute at most 5 min to one computer (not 10!)
 * - Why? Because each computer only runs for 5 min total
 * - Extra 5 min is wasted if dedicated to one computer
 * - Use: min(10, 5) = 5
 * 
 * Battery 1 (10 min):
 * - Same logic: min(10, 5) = 5
 * 
 * Battery 2 (3 min):
 * - Use all: min(3, 5) = 3
 * 
 * Battery 3 (5 min):
 * - Use all: min(5, 5) = 5
 * 
 * Total available: 5 + 5 + 3 + 5 = 18 >= 15 ✅ Possible!
 * 
 * If we didn't use min():
 * Total: 10 + 10 + 3 + 5 = 28 (wrong calculation!)
 * This overcounts because batteries > T can't fully contribute to one computer
 */

/*
 * EXAMPLE WALKTHROUGH: n=2, batteries=[3,3,3]
 * 
 * Total sum = 9
 * Left = min(batteries) = 3
 * Right = sum/n = 9/2 = 4
 * 
 * Binary Search:
 * 
 * Iteration 1:
 * - mid = 3 + (4-3)/2 = 3
 * - Check possible(3, 2, [3,3,3]):
 *   - target = 2 * 3 = 6
 *   - Battery 0: target -= min(3,3) = 6-3 = 3
 *   - Battery 1: target -= min(3,3) = 3-3 = 0 ✅
 *   - Possible! res = 3, left = 4
 * 
 * Iteration 2:
 * - mid = 4 + (4-4)/2 = 4
 * - Check possible(4, 2, [3,3,3]):
 *   - target = 2 * 4 = 8
 *   - Battery 0: target -= min(3,4) = 8-3 = 5
 *   - Battery 1: target -= min(3,4) = 5-3 = 2
 *   - Battery 2: target -= min(3,4) = 2-3 = -1 ✅
 *   - Possible! res = 4, left = 5
 * 
 * Iteration 3:
 * - left = 5, right = 4
 * - left > right, exit
 * 
 * Answer: 4 ✅
 */

/*
 * EDGE CASE: Why not just return r?
 * 
 * Consider: n=3, batteries=[10,10,3,5]
 * 
 * sum = 28
 * r = 28/3 = 9
 * 
 * Check possible(9, 3, [10,10,3,5]):
 * - target = 3 * 9 = 27
 * - Battery 0: target -= min(10,9) = 27-9 = 18
 * - Battery 1: target -= min(10,9) = 18-9 = 9
 * - Battery 2: target -= min(3,9) = 9-3 = 6
 * - Battery 3: target -= min(5,9) = 6-5 = 1
 * - target = 1 > 0 ❌ NOT possible!
 * 
 * So we can't just return r, we need to binary search!
 * The answer might be less than sum/n due to battery size constraints
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Binary Search on Answer pattern
 *    → Search for maximum time, not search in array
 *    → Range: [min_battery, sum/n]
 * 
 * 2. Monotonic property enables binary search
 *    → If time T works, all times < T work
 *    → If time T fails, all times > T fail
 * 
 * 3. Battery swapping = pooling energy
 *    → Treat all batteries as shared resource
 *    → Can redistribute power optimally
 * 
 * 4. Greedy check with min(battery, time)
 *    → Each battery contributes min(B, T)
 *    → Prevents overcounting large batteries
 * 
 * 5. Time: O(n log(sum/n))
 *    → Binary search: log(sum/n) iterations
 *    → Each check: O(n) to iterate batteries
 */

class Solution {
  public long maxRunTime(int n, int[] batteries) {
    // APPROACH 1: Linear Search (TLE - Time Limit Exceeded)
    // Why TLE? If sum/n is large (e.g., 10^9), checking each value takes too long
    //
    // long sum = 0;
    // for (int b : batteries) {
    // sum += b;
    // }
    // long l = 1;
    // long r = sum / n;
    //
    // // Check from maximum down to 1
    // for(long i = r; i >= 1; i--) {
    // if (possible(i, n, batteries)) {
    // return i;
    // }
    // }
    // return 0;

    // APPROACH 2: Binary Search (Optimal)

    // Step 1: Initialize left boundary
    // Use min battery value as lower bound (optimization)
    // Could also use 1, but min(batteries) reduces search space
    long l = Long.MAX_VALUE;

    long sum = 0;

    // Calculate sum and find minimum battery
    for (int b : batteries) {
      l = Math.min(l, b);
      sum += b;
    }

    // Step 2: Initialize right boundary
    // Maximum possible time = total energy / number of computers
    // This is the theoretical upper limit with perfect distribution
    long r = sum / n;

    // Step 3: Binary search for maximum valid time
    long res = 0; // Store the best answer found so far

    while (l <= r) {
      long mid = l + (r - l) / 2; // Avoid overflow with (l+r)/2

      // Check if we can run n computers for mid minutes
      if (possible(mid, n, batteries)) {
        // If possible, this is a valid answer
        res = mid;
        // Try to find a larger time (search right half)
        l = mid + 1;
      } else {
        // If not possible, try smaller time (search left half)
        r = mid - 1;
      }
    }

    return res;
  }

  /**
   * Check if we can run n computers for t minutes each
   * 
   * Strategy: Greedy allocation
   * - Calculate total energy needed: n * t
   * - For each battery, use min(battery_power, t)
   * - If total available >= total needed, return true
   * 
   * @param t         Target time per computer
   * @param n         Number of computers
   * @param batteries Array of battery capacities
   * @return true if we can run n computers for t minutes
   */
  private boolean possible(long t, int n, int[] batteries) {
    // Total energy needed to run n computers for t minutes
    long target = n * t;

    // Greedily allocate battery power
    for (int b : batteries) {
      // Each battery can contribute at most min(b, t)
      // Why? A battery with power > t can only provide t to one computer
      // (the computer only runs for t minutes total)
      target -= Math.min(b, t);

      // Early termination: if we've covered the target, we're done
      if (target <= 0) {
        return true;
      }
    }

    // If target > 0, we don't have enough battery power
    return false;
  }
}
