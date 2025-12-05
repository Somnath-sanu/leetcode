package simulation;

/*There are n cars on an infinitely long road. The cars are numbered from 0 to n - 1 from left to right and each car is present at a unique point.

You are given a 0-indexed string directions of length n. directions[i] can be either 'L', 'R', or 'S' denoting whether the ith car is moving towards the left, towards the right, or staying at its current point respectively. Each moving car has the same speed.

The number of collisions can be calculated as follows:

When two cars moving in opposite directions collide with each other, the number of collisions increases by 2.
When a moving car collides with a stationary car, the number of collisions increases by 1.
After a collision, the cars involved can no longer move and will stay at the point where they collided. Other than that, cars cannot change their state or direction of motion.

Return the total number of collisions that will happen on the road.

 

Example 1:

Input: directions = "RLRSLL"
Output: 5
Explanation:
The collisions that will happen on the road are:
- Cars 0 and 1 will collide with each other. Since they are moving in opposite directions, the number of collisions becomes 0 + 2 = 2.
- Cars 2 and 3 will collide with each other. Since car 3 is stationary, the number of collisions becomes 2 + 1 = 3.
- Cars 3 and 4 will collide with each other. Since car 3 is stationary, the number of collisions becomes 3 + 1 = 4.
- Cars 4 and 5 will collide with each other. After car 4 collides with car 3, it will stay at the point of collision and get hit by car 5. The number of collisions becomes 4 + 1 = 5.
Thus, the total number of collisions that will happen on the road is 5. 
Example 2:

Input: directions = "LLRR"
Output: 0
Explanation:
No cars will collide with each other. Thus, the total number of collisions that will happen on the road is 0.
 

Constraints:

1 <= directions.length <= 10^5
directions[i] is either 'L', 'R', or 'S'. */

/*
 * COLLISION SIMULATION PATTERN - Key Insight
 * 
 * This problem looks like it needs complex simulation, but there's a brilliant insight:
 * 
 * CARS THAT NEVER COLLIDE:
 * 1. Leading L's (leftmost cars moving left) - they move away from all cars
 * 2. Trailing R's (rightmost cars moving right) - they move away from all cars
 * 
 * CARS THAT WILL COLLIDE:
 * - Everything in between will eventually collide and become stationary!
 * - Count all R's and L's in this middle section
 * - S's don't add to count (already stationary, 0 collision cost)
 */

/*
 * WHY LEADING L'S NEVER COLLIDE
 * 
 * Example: "LLLRS"
 *           ^^^
 *           These L's move left away from everything
 * 
 * - They start at the leftmost positions
 * - They move LEFT (away from all other cars)
 * - There's nothing to their left to collide with
 * - They will never encounter any other car
 * 
 * Visual:
 * Time 0: L L L R S
 *         ← ← ←  →
 * Time 1: ← L ← L ← L   R → S
 * Time 2: ← ← L ← ← L ← ← L   R → → S
 * 
 * The leading L's just keep moving left forever, no collisions!
 */

/*
 * WHY TRAILING R'S NEVER COLLIDE
 * 
 * Example: "SLRRR"
 *              ^^^
 *              These R's move right away from everything
 * 
 * - They start at the rightmost positions
 * - They move RIGHT (away from all other cars)
 * - There's nothing to their right to collide with
 * - They will never encounter any other car
 * 
 * Visual:
 * Time 0: S L R R R
 *           ← → → →
 * Time 1: S L ←   R → R → R →
 * Time 2: S ← L ←   R → → R → → R → →
 * 
 * The trailing R's just keep moving right forever, no collisions!
 */

/*
 * WHY EVERYTHING IN THE MIDDLE COLLIDES
 * 
 * Once we remove leading L's and trailing R's, what remains?
 * - Some combination of R, S, L
 * - Key: First non-L from left is either R or S
 * - Key: Last non-R from right is either L or S
 * 
 * This creates a "collision zone":
 * - Any R will eventually hit something to its right (L or S)
 * - Any L will eventually hit something to its left (R or S)
 * - S acts as a "wall" that stops cars
 * 
 * Example: "RLRSLL"
 * - Leading L's: none
 * - Trailing R's: none
 * - Middle: "RLRSLL" (everything)
 * 
 * What happens:
 * - R at 0 hits L at 1 → both become S (2 collisions)
 * - R at 2 hits S at 3 → R becomes S (1 collision)
 * - L at 4 hits S at 3 → L becomes S (1 collision)
 * - L at 5 hits S at 4 → L becomes S (1 collision)
 * - Total: 5 collisions ✅
 */

/*
 * THE BRILLIANT INSIGHT
 * 
 * Instead of simulating collisions step by step:
 * "Every moving car (R or L) in the middle zone will collide exactly once"
 * 
 * Why?
 * - Each R or L starts moving
 * - It will hit something (another car or a stationary wall)
 * - When it hits, it stops and becomes S
 * - Each moving car contributes 1 collision when it stops
 * 
 * S cars don't count because:
 * - They're already stationary (0 cost)
 * - But they cause OTHER cars to collide (those cars count)
 */

/*
 * EXAMPLE: directions = "RLRSLL"
 * 
 * Step 1: Find leading L's
 * - i starts at 0
 * - directions[0] = 'R' (not L)
 * - Stop, i = 0
 * - No leading L's to skip
 * 
 * Step 2: Find trailing R's
 * - j starts at 5 (n-1)
 * - directions[5] = 'L' (not R)
 * - Stop, j = 5
 * - No trailing R's to skip
 * 
 * Step 3: Count R's and L's in middle [0, 5]
 * - Index 0: 'R' → collisions++ (= 1)
 * - Index 1: 'L' → collisions++ (= 2)
 * - Index 2: 'R' → collisions++ (= 3)
 * - Index 3: 'S' → skip (already stationary)
 * - Index 4: 'L' → collisions++ (= 4)
 * - Index 5: 'L' → collisions++ (= 5)
 * 
 * Total: 5 collisions ✅
 */

/*
 * EXAMPLE: directions = "LLRR"
 * 
 * Step 1: Find leading L's
 * - i = 0: 'L' → i++
 * - i = 1: 'L' → i++
 * - i = 2: 'R' → stop
 * - i = 2 (skipped first 2 L's)
 * 
 * Step 2: Find trailing R's
 * - j = 3: 'R' → j--
 * - j = 2: 'R' → j--
 * - j = 1: 'L' → stop
 * - j = 1 (skipped last 2 R's)
 * 
 * Step 3: Count in middle [2, 1]
 * - Loop: for k = 2; k <= 1 → false
 * - No iterations!
 * 
 * Total: 0 collisions ✅
 * (All L's move left, all R's move right, no collision zone)
 */

/*
 * EXAMPLE: directions = "SSRSSLL"
 * 
 * Step 1: Leading L's
 * - i = 0: 'S' → stop
 * - i = 0
 * 
 * Step 2: Trailing R's
 * - j = 6: 'L' → j--
 * - j = 5: 'L' → j--
 * - j = 4: 'S' → stop
 * - j = 4
 * 
 * Step 3: Count in [0, 4]
 * - Index 0: 'S' → skip
 * - Index 1: 'S' → skip
 * - Index 2: 'R' → collisions++ (= 1)
 * - Index 3: 'S' → skip
 * - Index 4: 'S' → skip
 * 
 * Total: 1 collision ✅
 * (Only the R collides with the S wall)
 */

/*
 * WHY STACK APPROACH IS WRONG
 * 
 * The commented-out stack approach tries to simulate collisions:
 * - Push cars onto stack
 * - When collision detected, update stack
 * 
 * Problem: Doesn't handle chain reactions correctly
 * 
 * Example: "RSSL"
 * Stack simulation:
 * - Push 'R'
 * - Push 'S' (R hits S, 1 collision) → stack: ['S']
 * - Push 'S' → stack: ['S', 'S']
 * - Push 'L' (L hits S, 1 collision) → stack: ['S', 'S']
 * - Total: 2 collisions
 * 
 * But wait, what about the R? It collides!
 * The stack approach counts some collisions correctly but misses others.
 * 
 * Correct approach:
 * - Leading L's: none (first is R)
 * - Trailing R's: none (last is L)
 * - Middle [0,3]: R, S, S, L
 * - Count R's and L's: R(1) + L(1) = 2 ✅
 */

/*
 * WHY THIS ALGORITHM IS CORRECT
 * 
 * Mathematical proof:
 * 1. Leading L's move away → 0 collisions
 * 2. Trailing R's move away → 0 collisions
 * 3. Middle zone has at least one "barrier":
 *    - Either first car is R or S (stops L's coming from right)
 *    - Or last car is L or S (stops R's coming from left)
 * 4. Every R in middle will hit this barrier → 1 collision
 * 5. Every L in middle will hit this barrier → 1 collision
 * 6. S's are already stationary → 0 collision cost
 * 
 * Total collisions = count of R's and L's in middle zone
 */

/*
 * TIME AND SPACE COMPLEXITY
 * 
 * Time Complexity: O(n)
 * - First while loop: O(n) worst case
 * - Second while loop: O(n) worst case
 * - For loop: O(n) worst case
 * - Total: O(n) - single pass overall
 * 
 * Space Complexity: O(1)
 * - Only use constant extra variables (i, j, k, collisions)
 * - No extra data structures
 */

/*
 * KEY TAKEAWAYS:
 * 
 * 1. Leading L's and trailing R's never collide
 *    → They move away from all other cars
 * 
 * 2. Everything in between will collide
 *    → Creates a "collision zone"
 * 
 * 3. Count R's and L's in middle, skip S's
 *    → Each moving car contributes 1 collision
 *    → S's are already stationary (0 cost)
 * 
 * 4. Don't simulate - use mathematical insight
 *    → O(n) instead of complex simulation
 * 
 * 5. This is a pattern for "inevitable collision" problems
 *    → Identify which elements can escape
 *    → Everything else will interact
 */

class Solution {
  public int countCollisions(String directions) {
    // WRONG APPROACH: Stack simulation (commented out)
    //
    // Why it's wrong:
    // - Tries to simulate collisions step by step
    // - Doesn't handle chain reactions correctly
    // - Misses some collisions in complex scenarios
    // - Example: "RSSL" gives wrong answer
    //
    // Stack<Character> st = new Stack<>();
    // int collisions = 0;
    // int n = directions.length();
    //
    // for (char c : directions.toCharArray()) {
    // if (!st.isEmpty()) {
    // char ch = st.peek();
    // if (c == 'L' && ch == 'R') {
    // collisions += 2;
    // st.pop();
    // st.push('S');
    // }
    // else if (c == 'L' && ch == 'S') {
    // collisions += 1;
    // st.pop();
    // st.push('S');
    // }
    // else if (c == 'S' && ch == 'R') {
    // collisions += 1;
    // st.pop();
    // st.push('S');
    // }else {
    // st.push(c);
    // }
    // }else {
    // st.push(c);
    // }
    // }
    // return collisions;

    // CORRECT APPROACH: Mathematical insight

    int collisions = 0;
    int n = directions.length();

    // Step 1: Skip all leading L's
    // These cars move LEFT away from everything
    // They will NEVER collide with any other car
    //
    // Example: "LLLRS" → skip first 3 L's
    // Visual: ← ← ← R S (L's move away)
    int i = 0;
    while (i < n && directions.charAt(i) == 'L') {
      i++;
    }

    // Step 2: Skip all trailing R's
    // These cars move RIGHT away from everything
    // They will NEVER collide with any other car
    //
    // Example: "SLRRR" → skip last 3 R's
    // Visual: S L → → → (R's move away)
    int j = n - 1;
    while (j >= 0 && directions.charAt(j) == 'R') {
      j--;
    }

    // Step 3: Count all R's and L's in the middle zone
    // Every moving car (R or L) in this zone will collide exactly once
    // S's don't count because they're already stationary (0 cost)
    //
    // Middle zone: [i, j]
    // - Everything here is "trapped" and will collide
    // - First car from left is R or S (blocks L's)
    // - Last car from right is L or S (blocks R's)
    //
    // Note: Could use while loop, but for loop is clearer here
    for (int k = i; k <= j; k++) {
      if (directions.charAt(k) == 'S') {
        continue; // S is already stationary, 0 collision cost
      }
      // This is an R or L that will collide
      collisions++;
    }

    return collisions;
  }
}
