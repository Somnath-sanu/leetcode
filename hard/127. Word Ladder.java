package hard;

import java.util.*;

/*
 * A transformation sequence from word beginWord to word endWord using a
 * dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk
 * such that:
 * 
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to
 * be in wordList.
 * sk == endWord
 * Given two words, beginWord and endWord, and a dictionary wordList, return the
 * number of words in the shortest transformation sequence from beginWord to
 * endWord, or 0 if no such sequence exists.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log","cog"]
 * Output: 5
 * Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot"
 * -> "dog" -> cog", which is 5 words long.
 */

class Solution {
  public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> set = new HashSet<>(wordList);

    if (!set.contains(endWord)) {
      return 0;
    }

    Queue<String> q = new LinkedList<>();

    q.offer(beginWord);

    if (set.contains(beginWord)) { // O(1)
      set.remove(beginWord); // O(1)
    }

    int level = 0;

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        String w = q.poll();

        if (w.equals(endWord)) { // string cannot be compared via ==
          return level + 1;
        }

        for (String neighbour : getNeighbours(w, set)) {
          if (set.contains(neighbour)) { // No need for this check, as in getNeighours func we added word after checking
                                         // as well

            q.offer(neighbour);
            set.remove(neighbour); // to mark as visited

          }
        }
      }

      level++;
    }

    return 0;
  }

  public List<String> getNeighbours(String word, Set<String> wordList) {
    // T.C of set is O(1)
    List<String> neighbours = new ArrayList<>();

    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);

      for (char j = 'a'; j <= 'z'; j++) {

        StringBuilder sb = new StringBuilder();

        if (j == c) {
          continue;
        }

        sb.append(word.substring(0, i));
        sb.append(j);
        sb.append(word.substring(i + 1));

        String res = sb.toString();

        if (wordList.contains(res)) {
          neighbours.add(res);
        }
      }

    }

    return neighbours;
  }
}