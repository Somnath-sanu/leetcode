package math;

/*
 * Given a positive integer k, you need to find the length of the smallest
 * positive integer n such that n is divisible by k, and n only contains the
 * digit 1.
 * 
 * Return the length of n. If there is no such n, return -1.
 * 
 * Note: n may not fit in a 64-bit signed integer.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: k = 1
 * Output: 1
 * Explanation: The smallest answer is n = 1, which has length 1.
 * Example 2:
 * 
 * Input: k = 2
 * Output: -1
 * Explanation: There is no such positive integer n divisible by 2.
 * Example 3:
 * 
 * Input: k = 3
 * Output: 3
 * Explanation: The smallest answer is n = 111, which has length 3.
 * 
 * 
 * Constraints:
 * 
 * 1 <= k <= 10^5
 */

/*
 * REPUNIT NUMBERS - Numbers with only 1s
 * 
 * A repunit is a number that contains only the digit 1.
 * 
 * Examples:
 * - Length 1: 1
 * - Length 2: 11
 * - Length 3: 111
 * - Length 4: 1111
 * - Length n: (10^n - 1) / 9
 * 
 * This problem asks: What's the smallest repunit divisible by k?
 */

/*
 * WHY OVERFLOW IS A PROBLEM
 * 
 * Naive approach: Build actual repunit numbers
 * - n = 1
 * - n = 11 = n * 10 + 1
 * - n = 111 = n * 10 + 1
 * - n = 1111 = n * 10 + 1
 * 
 * Problem: Repunits grow VERY quickly!
 * - Length 10: 1,111,111,111 (fits in int)
 * - Length 19: 1,111,111,111,111,111,111 (fits in long)
 * - Length 20: 11,111,111,111,111,111,111 (OVERFLOW!)
 * 
 * Example overflow sequence (from commented code):
 * 1111111111111111111 (19 ones - valid)
 * -7335632962598440505 (20 ones - OVERFLOW! Negative number)
 * 430646668853801415 (21 ones - garbage)
 * 
 * Since k can be up to 10^5, we might need repunits with length > 19.
 * We CANNOT store the actual number!
 */

/*
 * SOLUTION: Modular Arithmetic
 * 
 * Key Insight: We don't need the actual repunit, just whether it's divisible by k!
 * 
 * Mathematical Property:
 * If we want to check if n is divisible by k, we only need n % k == 0
 * 
 * We can build the remainder incrementally:
 * - n₁ = 1, remainder₁ = 1 % k
 * - n₂ = 11 = 1 * 10 + 1, remainder₂ = (remainder₁ * 10 + 1) % k
 * - n₃ = 111 = 11 * 10 + 1, remainder₃ = (remainder₂ * 10 + 1) % k
 * 
 * Formula: remainder_new = (remainder_old * 10 + 1) % k
 * 
 * This keeps the numbers small (always < k) and avoids overflow!
 */

/*
 * EXAMPLE: k = 3
 * 
 * Iteration 1: rem = (0 * 10 + 1) % 3 = 1 % 3 = 1 (n = 1)
 *   - 1 % 3 ≠ 0, continue
 * 
 * Iteration 2: rem = (1 * 10 + 1) % 3 = 11 % 3 = 2 (n = 11)
 *   - 11 % 3 ≠ 0, continue
 * 
 * Iteration 3: rem = (2 * 10 + 1) % 3 = 21 % 3 = 0 (n = 111)
 *   - 111 % 3 == 0 ✅ Found it! Return length = 3
 */

/*
 * EXAMPLE: k = 7
 * 
 * Iteration 1: rem = 1 % 7 = 1 (n = 1, 1 ÷ 7 = 0 R 1)
 * Iteration 2: rem = 11 % 7 = 4 (n = 11, 11 ÷ 7 = 1 R 4)
 * Iteration 3: rem = 41 % 7 = 6 (n = 111, 111 ÷ 7 = 15 R 6)
 * Iteration 4: rem = 61 % 7 = 5 (n = 1111, 1111 ÷ 7 = 158 R 5)
 * Iteration 5: rem = 51 % 7 = 2 (n = 11111, 11111 ÷ 7 = 1587 R 2)
 * Iteration 6: rem = 21 % 7 = 0 (n = 111111, 111111 ÷ 7 = 15873 R 0) ✅
 * 
 * Answer: 6 (the repunit 111111 is divisible by 7)
 */

/*
 * PIGEONHOLE PRINCIPLE - Why loop only up to k?
 * 
 * Key Observation: Remainders when dividing by k can only be 0, 1, 2, ..., k-1
 * That's k possible remainders.
 * 
 * If we generate k+1 repunits, we'll have k+1 remainders.
 * By pigeonhole principle: At least two repunits must have the SAME remainder.
 * 
 * If remainder repeats without hitting 0, we're in a cycle → no solution exists.
 * If we hit remainder = 0 within k iterations → solution found!
 * 
 * Therefore: If no solution found in k iterations, return -1.
 * 
 * Example with k = 7:
 * - Possible remainders: 0, 1, 2, 3, 4, 5, 6 (7 values)
 * - We check up to 7 repunits
 * - If we see remainder 0 → found!
 * - If we see a repeat (e.g., remainder 1 appears twice) → cycle, no solution
 */

/*
 * WHEN IS THERE NO SOLUTION?
 * 
 * Mathematical Fact: A repunit can only be divisible by k if gcd(k, 10) = 1
 * 
 * Why? Repunits have the form (10^n - 1) / 9
 * If k shares a factor with 10 (i.e., k is divisible by 2 or 5),
 * then no repunit will ever be divisible by k.
 * 
 * Examples:
 * - k = 2: All repunits are odd (1, 11, 111, ...) → never divisible by 2
 * - k = 5: All repunits end in 1 → never divisible by 5
 * - k = 6 = 2 × 3: Has factor 2 → no solution
 * - k = 3: gcd(3, 10) = 1 → solution exists (111)
 * - k = 7: gcd(7, 10) = 1 → solution exists (111111)
 * 
 * Our algorithm handles this automatically:
 * - If k is even or divisible by 5, we'll never find remainder = 0
 * - Loop terminates after k iterations, return -1
 */

/*
 * WHY rem = (rem * 10 + 1) % k WORKS
 * 
 * Building repunit n from previous repunit:
 * - Previous: n_old (e.g., 11)
 * - Current: n_new = n_old * 10 + 1 (e.g., 11 * 10 + 1 = 111)
 * 
 * Modular arithmetic property:
 * (a * b + c) % k = ((a % k) * b + c) % k
 * 
 * Therefore:
 * n_new % k = (n_old * 10 + 1) % k
 *           = ((n_old % k) * 10 + 1) % k
 *           = (rem_old * 10 + 1) % k
 * 
 * This is why we can track just the remainder instead of the full number!
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Use modular arithmetic to avoid overflow
 *    → Track remainder instead of actual repunit
 *    → rem = (rem * 10 + 1) % k
 * 
 * 2. Pigeonhole principle guarantees termination
 *    → At most k iterations needed
 *    → Either find solution or detect cycle
 * 
 * 3. No solution exists if k is divisible by 2 or 5
 *    → gcd(k, 10) must be 1 for solution to exist
 *    → Algorithm handles this automatically
 * 
 * 4. Space: O(1), Time: O(k)
 *    → Very efficient even for large k
 */

class Solution {
  public int smallestRepunitDivByK(int k) {
    // Start with remainder 0 (representing empty/no repunit yet)
    // We'll build up: 1, 11, 111, 1111, ...
    int rem = 0;

    // Pigeonhole principle: If solution exists, we'll find it within k iterations
    // If we don't find it in k iterations, we're in a cycle → no solution
    for (int len = 1; len <= k; len++) {
      // Build next repunit's remainder
      // Formula: new_repunit = old_repunit * 10 + 1
      // In terms of remainders: new_rem = (old_rem * 10 + 1) % k
      rem = (rem * 10 + 1) % k;

      // Check if current repunit is divisible by k
      // If remainder is 0, we found the answer!
      if (rem == 0) {
        return len; // Return the length of the repunit
      }

      // Otherwise, continue building longer repunits
      // Example progression for k=7:
      // len=1: rem = 1 (repunit: 1)
      // len=2: rem = 4 (repunit: 11)
      // len=3: rem = 6 (repunit: 111)
      // len=4: rem = 5 (repunit: 1111)
      // len=5: rem = 2 (repunit: 11111)
      // len=6: rem = 0 (repunit: 111111) ✅ Found!
    }

    // If we've checked k repunits without finding remainder = 0,
    // then no solution exists (k is divisible by 2 or 5)
    return -1;
  }
}