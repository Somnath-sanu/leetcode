package heaps;

/*
 * You are given n projects where the ith project has a pure profit profits[i]
 * and a minimum capital of capital[i] is needed to start it.
 * 
 * Initially, you have w capital. When you finish a project, you will obtain its
 * pure profit and the profit will be added to your total capital.
 * 
 * Pick a list of at most k distinct projects from given projects to maximize
 * your final capital, and return the final maximized capital.
 * 
 * The answer is guaranteed to fit in a 32-bit signed integer.
 * 
 * Input: k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
 * Output: 4
 * Explanation: Since your initial capital is 0, you can only start the project
 * indexed 0.
 * After finishing it you will obtain profit 1 and your capital becomes 1.
 * With capital 1, you can either start the project indexed 1 or the project
 * indexed 2.
 * Since you can choose at most 2 projects, you need to finish the project
 * indexed 2 to get the maximum capital.
 * Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
 */

import java.util.*;

class Solution {
  public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    // [capital , profit]
    // minHeap based on capital
    PriorityQueue<int[]> minPq = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]);

    PriorityQueue<Integer> maxPq = new PriorityQueue<>(
        (a, b) -> b - a);

    int n = profits.length; // same for capital as well

    for (int i = 0; i < n; i++) {
      minPq.offer(new int[] { capital[i], profits[i] });
    }

    for (int i = 0; i < k; i++) {
      while (!minPq.isEmpty()) {
        if (minPq.peek()[0] <= w) {
          maxPq.offer(minPq.poll()[1]);
        } else {
          break;
        }
      }
      if (maxPq.isEmpty()) {
        break;
      }

      w += maxPq.poll();
    }

    return w;

  }
}