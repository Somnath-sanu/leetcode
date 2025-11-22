package heaps;

import java.util.*;

/*
 * You are given an array of CPU tasks, each labeled with a letter from A to Z,
 * and a number n. Each CPU interval can be idle or allow the completion of one
 * task. Tasks can be completed in any order, but there's a constraint: there
 * has to be a gap of at least n intervals between two tasks with the same
 * label.
 * 
 * Return the minimum number of CPU intervals required to complete all tasks.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * 
 * Output: 8
 * 
 * Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A ->
 * B.
 * 
 * After completing task A, you must wait two intervals before doing A again.
 * The same applies to task B. In the 3rd interval, neither A nor B can be done,
 * so you idle. By the 4th interval, you can do A again as 2 intervals have
 * passed.
 */

/**
 * We have to do greedy approach
 * We have to first finish the task which has maximum frequency
 * after that we have to finish the task which has second maximum frequency
 * and so on
 */

/**
 * We also have to maintain cool down period
 * 
 * so frequency of a task is important
 * along with cool down period
 */

/**
 * 1. Find the frequency of each task -> HashMap or character array
 * 2. After each operation, know the max frequency -> max heap
 * 3. Maintain the cool down period -> queue
 */

/*
 * Task -> [Freq , ExecutionTime]
 * Heap -> max heap based on frequency
 * Queue -> to maintain cool down period based on execution time
 */

/*
 * We dont have to do anything with characters in input
 * we need their frequency to schedule tasks
 * 
 */

class Solution {
  public int leastInterval(char[] tasks, int n) {
    // HashMap<Character,Integer> freqMap = new HashMap<>();
    int[] freqArray = new int[26];

    // for (char ch : tasks) {
    // freqMap.put(ch, freqMap.getOrDefault(ch,0)+1);
    // }

    for (char ch : tasks) {
      freqArray[ch - 'A']++;
    }

    // [freq,executimeTime]
    PriorityQueue<int[]> maxPq = new PriorityQueue<>(
        (a, b) -> b[0] - a[0]);

    // for(int freq : freqMap.values()) {
    // maxPq.offer(new int[]{freq,0});
    // }

    for (int f : freqArray) {
      if (f > 0) {
        maxPq.offer(new int[] { f, 0 });
      }
    }

    Queue<int[]> q = new LinkedList<>();

    int time = 0;

    while (!(maxPq.isEmpty() && q.isEmpty())) {
      int freq = 0;
      if (!maxPq.isEmpty()) {
        int[] p = maxPq.poll();
        freq = p[0];
        freq -= 1;
      }

      time++;

      if (freq != 0) {
        // put this in queue
        int executionTime = time + n;
        q.offer(new int[] { freq, executionTime });
      }

      if (!q.isEmpty() && time == q.peek()[1]) {
        maxPq.offer(q.poll());
      }

    }

    return time;
  }
}