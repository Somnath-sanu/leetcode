package hard;

import java.util.*;

/*
 * Example 1:
 * 
 * Input: beginWord = "hit", endWord = "cog", wordList =
 * ["hot","dot","dog","lot","log","cog"]
 * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * Explanation: There are 2 shortest transformation sequences:
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 */

class Solution {
  public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    Set<String> set = new HashSet<>(wordList);

    List<List<String>> res = new ArrayList<>();

    if (!set.contains(endWord)) {
      return res;
    }

    Queue<List<String>> q = new LinkedList<>();

    List<String> temp = new ArrayList<>();
    temp.add(beginWord);

    q.offer(temp);

    if (set.contains(beginWord)) {
      set.remove(beginWord);
    }

    int level = 0;
    int resultLevel = -1;

    while (!q.isEmpty()) {
      int size = q.size();
      Set<String> used = new HashSet<>();

      for (int i = 0; i < size; i++) {
        List<String> list = q.poll();

        String w = list.get(list.size() - 1);

        if (w.equals(endWord)) {
          res.add(list);
          resultLevel = level;
          continue; // this will make q empty so while loop automatically breaks
          // still lets maintain resultLevel

        }

        for (String n : getNeighbours(w, set)) {
          list.add(n);
          q.offer(new ArrayList<>(list));
          list.remove(n);
          // set.remove(n);
          // This will delete path of another word having same n
          // so, we remove this after this level

          used.add(n);
        }
      }

      if (resultLevel == level) {
        break;
      }

      level++;

      for (String u : used) {
        set.remove(u);
      }
    }

    return res;
  }

  private List<String> getNeighbours(String word, Set<String> wordList) {
    List<String> neighbours = new ArrayList<>();

    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);

      for (char ch = 'a'; ch <= 'z'; ch++) {
        if (ch == c) {
          continue;
        }

        String res = word.substring(0, i) + ch + word.substring(i + 1);

        if (wordList.contains(res)) {
          neighbours.add(res);
        }
      }
    }

    return neighbours;
  }
}