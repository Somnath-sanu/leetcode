package hard;

class Solution {
  public String longestSubsequenceRepeatedK(String s, int k) {
    int n = s.length();

    int[] freq = new int[26];
    for (char c : s.toCharArray()) {
      freq[c - 'a']++;
    }

    int[] requiredFreq = new int[26];

    for (int i = 0; i < 26; i++) {
      if (freq[i] >= k) {
        requiredFreq[i] = freq[i] / k;// atmost this can be used in subsequence

      }
    }

    int maxLen = n / k; // maxLen of subsequence // best ans

    StringBuilder curr = new StringBuilder();
    StringBuilder result = new StringBuilder();

    // backtrack(s,curr,canUse,requiredFreq,k,maxLen,result);

    // return result.toString();

    // little optimization
    for (int len = maxLen; len >= 0; len--) {
      int[] tempRequiredFreq = requiredFreq;
      if (backtrack(s, curr, tempRequiredFreq, k, len, result)) {
        return result.toString();
      }
    }
    return result.toString();
  }

  private void backtrackSlower(String s, StringBuilder curr, boolean[] canUse, int[] requiredFreq, int k, int maxLen,
      StringBuilder result) {
    // base case
    if (curr.length() > maxLen) {
      return;
    }

    if ((curr.length() > result.length()
        || (curr.length() == result.length()
            && (curr.toString()).compareTo(result.toString()) > 0))
        && isSubsequence(s, curr, k)) {
      result.setLength(0);
      result.append(curr);
    }
    // compareTo -> check > 0 not == 1; , +ve , -ve , 0
    // "az".compareTo("aa") // returns 25

    for (int i = 0; i < 26; i++) {
      if (canUse[i] == false || requiredFreq[i] == 0)
        continue;

      // PICK
      char ch = (char) (i + 'a');
      curr.append(ch);
      requiredFreq[i]--;

      // EXPLORE
      backtrackSlower(s, curr, canUse, requiredFreq, k, maxLen, result);

      // NO-PICK
      curr.deleteCharAt(curr.length() - 1);
      requiredFreq[i]++;

    }

  }

  private boolean isSubsequence(String s, StringBuilder sub, int k) {
    int i = 0;
    int j = 0;
    int n = s.length();
    int m = sub.length();
    int maxL = m * k;

    // either we can concatinate
    // sub = "let" // len -> 3 // k -> 2 // maxL -> 6
    // subb = "letlet"

    // OR mod (%)

    while (i < n && j < maxL) {
      if (s.charAt(i) == sub.charAt(j % m)) {
        j++;
      }
      i++;
    }

    return (j == maxL);
  }

  // little optimized

  private boolean backtrack(String s, StringBuilder curr, int[] requiredFreq, int k, int maxLen, StringBuilder result) {
    // base case
    if (curr.length() == maxLen) {
      if (isSubsequence(s, curr, k)) {
        result.append(curr);
        return true;
      }
      return false;
    }

    // i should be 25 not 26(indexOutOfBound)
    for (int i = 25; i >= 0; i--) { // now no need to check for lexographically
      if (requiredFreq[i] == 0)
        continue;

      // PICK
      char ch = (char) (i + 'a');
      curr.append(ch);
      requiredFreq[i]--;

      // EXPLORE
      if (backtrack(s, curr, requiredFreq, k, maxLen, result)) {
        return true;
      }

      // NO-PICK
      curr.deleteCharAt(curr.length() - 1);
      requiredFreq[i]++;

    }

    return false;
  }
}

// T.C : n * (n/k)!
// n/k -> max len of subsequence
// S.C : n/k