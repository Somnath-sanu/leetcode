package graph;

import java.util.*;

class Solution {
  int rows; // global variable
  int cols; // global variable

  void dfs(int row, int col, int newColor, int curColor, int[][] image) {
    // out of bound cases
    if (row < 0 || row >= rows || col < 0 || col >= cols) { // first check this //imp
      return;
    }

    if (image[row][col] != curColor) {
      return;
    }

    // This is like a visited array check
    if (image[row][col] == newColor) { // imp condition to avoid infinite loop
      return;
    }

    image[row][col] = newColor; // we are changing in-place

    // neighbours
    int[][] adjList = {
        { row - 1, col },
        { row, col + 1 },
        { row + 1, col },
        { row, col - 1 }
    };

    for (int[] neighbour : adjList) {
      dfs(neighbour[0], neighbour[1], newColor, curColor, image);
    }
  }

  public int[][] floodFill(int[][] image, int sr, int sc, int color) {
    rows = image.length;
    cols = image[0].length;
    int curColor = image[sr][sc];
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[] { sr, sc });
    image[sr][sc] = color; // fill // visit
    while (!queue.isEmpty()) {
      int[] node = queue.poll();
      int row = node[0];
      int col = node[1];
      int[][] adjList = {
          { row - 1, col },
          { row, col + 1 },
          { row + 1, col },
          { row, col - 1 }
      };
      for (int[] neighbour : adjList) {
        int r = neighbour[0];
        int c = neighbour[1];
        if (r < 0 || r >= rows || c < 0 || c >= cols || image[r][c] != curColor || image[r][c] == color) {
          continue; // not break;
        }
        queue.offer(new int[] { r, c });
        image[r][c] = color;
      }
    }

    return image;

    // -------------------------------------------

    // dfs(sr,sc,color,image[sr][sc],image);
    // return image;
  }
}