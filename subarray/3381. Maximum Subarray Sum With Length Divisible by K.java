package subarray;

/*You are given an array of integers nums and an integer k.

Return the maximum sum of a subarray of nums, such that the size of the subarray is divisible by k.

 

Example 1:

Input: nums = [1,2], k = 1

Output: 3

Explanation:

The subarray [1, 2] with sum 3 has length equal to 2 which is divisible by 1.

Example 2:

Input: nums = [-1,-2,-3,-4,-5], k = 4

Output: -10

Explanation:

The maximum sum subarray is [-1, -2, -3, -4] which has length equal to 4 which is divisible by 4.

Example 3:

Input: nums = [-5,1,2,-3,4], k = 2

Output: 4

Explanation:

The maximum sum subarray is [1, 2, -3, 4] which has length equal to 4 which is divisible by 2.

 

Constraints:

1 <= k <= nums.length <= 2 * 10^5
-10^9 <= nums[i] <= 10^9 */

/*
 * KADANE'S ALGORITHM - Classic Maximum Subarray Problem (O(n))
 * 
 * Standard Kadane's Algorithm (no length constraint):
 * At each index, we decide:
 * 1. Start a new subarray from current element
 * 2. Extend the previous subarray by including current element
 * 
 * Choose whichever gives maximum sum:
 * currSum = max(nums[i], currSum + nums[i])
 * 
 * Example: nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * - At index 3 (value 4): max(4, -4 + 4) = 4 (start new)
 * - At index 4 (value -1): max(-1, 4 + (-1)) = 3 (extend)
 * - At index 5 (value 2): max(2, 3 + 2) = 5 (extend)
 * - Maximum subarray: [4, -1, 2, 1] with sum 6
 */

/*
 * THIS PROBLEM: Kadane's with Length Constraint
 * 
 * Additional constraint: Subarray length must be divisible by k
 * 
 * Key Insight: We can't just consider individual elements anymore.
 * We must consider CHUNKS of size k as the basic unit.
 * 
 * Modified Kadane's:
 * - Instead of adding single elements, add chunks of k elements
 * - At each chunk, decide: start new or extend previous
 * - currSum = max(chunkSum, currSum + chunkSum)
 */

/*
 * WHY PROCESS k SEPARATE SEQUENCES?
 * 
 * Problem: Subarrays can start at ANY index, not just multiples of k
 * 
 * Example: nums = [1, 2, 3, 4, 5, 6], k = 2
 * Valid subarrays (length divisible by 2):
 * - Starting at 0: [1,2], [1,2,3,4], [1,2,3,4,5,6]
 * - Starting at 1: [2,3], [2,3,4,5]
 * - Starting at 2: [3,4], [3,4,5,6]
 * - Starting at 3: [4,5]
 * - Starting at 4: [5,6]
 * 
 * Observation: Subarrays starting at index i can only include elements at
 * indices i, i+k, i+2k, i+3k, ... (to maintain length divisible by k)
 * 
 * Solution: Process k separate sequences:
 * - Sequence 0: indices 0, k, 2k, 3k, ... (start=0)
 * - Sequence 1: indices 1, k+1, 2k+1, 3k+1, ... (start=1)
 * - Sequence 2: indices 2, k+2, 2k+2, 3k+2, ... (start=2)
 * - ...
 * - Sequence k-1: indices k-1, 2k-1, 3k-1, ... (start=k-1)
 * 
 * Each sequence is independent and we apply Kadane's to each!
 */

/*
 * EXAMPLE: nums = [-5, 1, 2, -3, 4], k = 2
 * 
 * We process 2 sequences (start = 0 and start = 1):
 * 
 * Sequence 0 (start=0, indices 0, 2, 4):
 *   Chunk 1: indices [0,1] = [-5, 1], sum = -4
 *     currSum = max(-4, 0 + (-4)) = -4
 *     result = -4
 *   
 *   Chunk 2: indices [2,3] = [2, -3], sum = -1
 *     currSum = max(-1, -4 + (-1)) = -1
 *     result = -1
 *   
 *   Chunk 3: indices [4] - incomplete, skip
 * 
 * Sequence 1 (start=1, indices 1, 3):
 *   Chunk 1: indices [1,2] = [1, 2], sum = 3
 *     currSum = max(3, 0 + 3) = 3
 *     result = 3
 *   
 *   Chunk 2: indices [3,4] = [-3, 4], sum = 1
 *     currSum = max(1, 3 + 1) = 4 (extend!)
 *     result = 4 ✅
 * 
 * Final answer: 4 (subarray [1, 2, -3, 4])
 */

/*
 * EXAMPLE: nums = [-1, -2, -3, -4, -5], k = 4
 * 
 * We process 4 sequences:
 * 
 * Sequence 0 (start=0):
 *   Chunk 1: indices [0,1,2,3] = [-1,-2,-3,-4], sum = -10
 *     currSum = max(-10, 0 + (-10)) = -10
 *     result = -10 ✅
 *   
 *   Chunk 2: indices [4] - incomplete, skip
 * 
 * Sequence 1 (start=1):
 *   Chunk 1: indices [1,2,3,4] = [-2,-3,-4,-5], sum = -14
 *     currSum = max(-14, 0 + (-14)) = -14
 *     result = -14
 * 
 * Sequences 2 and 3: Not enough elements for even one chunk
 * 
 * Final answer: -10 (subarray [-1,-2,-3,-4])
 */

/*
 * PREFIX SUM OPTIMIZATION
 * 
 * To quickly calculate sum of any subarray [i, j]:
 * - Build prefix sum array: prefSum[i] = sum of nums[0..i]
 * - Sum of nums[i..j] = prefSum[j] - prefSum[i-1]
 * 
 * Example: nums = [1, 2, 3, 4, 5]
 * prefSum = [1, 3, 6, 10, 15]
 * 
 * Sum of nums[2..4] = prefSum[4] - prefSum[1] = 15 - 3 = 12
 * Which equals: 3 + 4 + 5 = 12 ✅
 * 
 * Edge case: When i = 0, prefSum[i-1] doesn't exist
 * Handle: prefSum[j] - (i > 0 ? prefSum[i-1] : 0)
 */

/*
 * ALGORITHM WALKTHROUGH:
 * 
 * 1. Build prefix sum array for O(1) subarray sum queries
 * 
 * 2. For each starting position (0 to k-1):
 *    - This represents one of the k independent sequences
 * 
 * 3. For each sequence, process chunks of size k:
 *    - Calculate chunk sum using prefix sum
 *    - Apply Kadane's: currSum = max(chunkSum, currSum + chunkSum)
 *    - Track maximum across all chunks and sequences
 * 
 * 4. Return the maximum sum found
 */

/*
 * WHY i + k - 1 < n CHECK?
 * 
 * We need a complete chunk of k elements starting at index i.
 * - Start index: i
 * - End index: i + k - 1
 * - Last valid index: n - 1
 * 
 * Condition: i + k - 1 < n ensures we have k elements
 * Equivalently: i + k <= n or i <= n - k
 * 
 * Example: n = 5, k = 2
 * - i = 0: chunk [0,1] ✅ (0 + 2 - 1 = 1 < 5)
 * - i = 2: chunk [2,3] ✅ (2 + 2 - 1 = 3 < 5)
 * - i = 4: chunk [4,5] ❌ (4 + 2 - 1 = 5 >= 5, incomplete!)
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Extension of Kadane's algorithm for length constraint
 *    → Process chunks of size k instead of individual elements
 * 
 * 2. Handle all possible starting positions
 *    → Process k independent sequences (start = 0 to k-1)
 * 
 * 3. Use prefix sum for efficiency
 *    → O(1) time to calculate any subarray sum
 * 
 * 4. Apply Kadane's to each sequence
 *    → currSum = max(chunkSum, currSum + chunkSum)
 *    → Decide: start new or extend previous
 * 
 * 5. Time Complexity: O(n)
 *    → Build prefix sum: O(n)
 *    → Process k sequences, each with n/k chunks: O(k * n/k) = O(n)
 * 
 * 6. Space Complexity: O(n)
 *    → Prefix sum array
 */

class Solution {
  public long maxSubarraySum(int[] nums, int k) {
    int n = nums.length;

    // Step 1: Build prefix sum array for O(1) subarray sum calculation
    // prefSum[i] = sum of nums[0..i]
    long[] prefSum = new long[n];
    prefSum[0] = nums[0];
    for (int i = 1; i < n; i++) {
      prefSum[i] = prefSum[i - 1] + nums[i];
    }

    // Track the maximum sum across all sequences
    long result = Long.MIN_VALUE;

    // Step 2: Process k independent sequences
    // Each sequence starts at a different position (0 to k-1)
    for (int start = 0; start < k; start++) { // start < n will give TLE
      // Kadane's algorithm for this sequence
      // currSum tracks the maximum sum ending at current chunk
      long currSum = 0;

      // Process chunks of size k in this sequence
      // Chunks: [start, start+k-1], [start+k, start+2k-1], ...
      int i = start;
      while (i < n && i + k - 1 < n) {
        // Calculate end index of current chunk
        int j = i + k - 1;

        // Calculate sum of chunk [i, j] using prefix sum
        // Sum = prefSum[j] - prefSum[i-1]
        // Handle edge case when i = 0 (no prefSum[i-1])
        long subSum = prefSum[j] - (i > 0 ? prefSum[i - 1] : 0);

        // Kadane's decision: start new subarray or extend previous?
        // - subSum: start new subarray from this chunk
        // - currSum + subSum: extend previous subarray with this chunk
        currSum = Math.max(subSum, currSum + subSum);

        // Update global maximum
        result = Math.max(result, currSum);

        // Move to next chunk in this sequence (jump by k)
        i += k;
      }
    }

    return result;
  }
}
