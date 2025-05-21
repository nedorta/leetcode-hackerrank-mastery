# Course Schedule - Solution Explanation

## 1. Problem Statement

You have `numCourses` courses labeled from 0 to `numCourses - 1`. Some courses have prerequisites: to take course `a`, you must first take course `b`. This is given as pairs `[a, b]` in the array `prerequisites`.

Task: Determine if it's possible to finish all courses. Return `true` if you can find an order to take every course once all prerequisites are satisfied; otherwise, return `false`.

## 2. Concrete Examples

| Example | Input | Output | Explanation |
|---------|-------|--------|-------------|
| 1 | numCourses = 2, prerequisites = [[1,0]] | true | You can take course 0 first, then course 1. |
| 2 | numCourses = 2, prerequisites = [[1,0],[0,1]] | false | Course 1 requires 0, and 0 requires 1 ⇒ circular dependency ⇒ impossible. |

## 3. Brute-Force Thought (Detect a Cycle by DFS from Each Node)

A naive method is, for each course, to follow its prerequisite chain and see if you ever revisit the same course (a cycle). You could use a DFS starting from each node:

```java
for each course c:
  if (hasCycleStartingAt(c)) return false
return true
```

- Time complexity: O(N + E) per DFS × N courses ⇒ O(N·(N+E)) in worst case
- Why improve: With up to 2000 courses and 5000 edges, this can be too slow

## 4. Optimal O(N + E) Approach: Kahn's Topological Sort (BFS on In-Degrees)

### 4.1. Key Idea

1. Build a graph with adjacency lists: for each prerequisite [a, b], add an edge b → a
2. Compute in-degree for each course: the number of prerequisites it has
3. Initialize a queue with all courses whose in-degree is 0 (no prerequisites)
4. BFS: repeatedly
   - Pop a course c from the queue (it's now "taken")
   - For each neighbor n of c (courses that depend on c), decrement inDegree[n]
   - If inDegree[n] becomes 0, add n to the queue
5. Count how many courses you "take." If you process all numCourses, you can finish; otherwise a cycle exists

This runs in O(N + E) time and uses O(N+E) space.

## 5. Walkthrough of the Code

```java
import java.util.*;

public class Solution {
    /**
     * Returns true if you can finish all courses given the prerequisites,
     * i.e., if the directed graph has no cycle.
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 1) Build adjacency list and in-degree array
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pair : prerequisites) {
            int course = pair[0], pre = pair[1];
            graph.get(pre).add(course);  // edge pre → course
            inDegree[course]++;          // course has one more prerequisite
        }

        // 2) Initialize queue with all courses having in-degree 0
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // 3) Process the queue
        int taken = 0;  // how many courses we can take in topological order
        while (!queue.isEmpty()) {
            int c = queue.poll();  // take course c
            taken++;
            // "Unlock" all courses that depend on c
            for (int nei : graph.get(c)) {
                inDegree[nei]--;           // remove the prerequisite link
                if (inDegree[nei] == 0) {  // no more prerequisites
                    queue.add(nei);
                }
            }
        }

        // 4) If we've taken all courses, no cycle exists
        return (taken == numCourses);
    }
}
```

### Code Section Breakdown

| Code Section | Purpose |
|--------------|---------|
| Build graph & inDegree | Store edges pre → course and count prerequisites per course |
| Queue init with inDegree==0 | Find all courses you can take immediately (no prerequisites) |
| BFS loop | "Take" each course, decrement in-degrees of dependents, enqueue when ready |
| taken == numCourses | Success if you end up taking every course (no cycles) |

## 6. Complexity Analysis

### Time: O(N + E)
- Building the graph and in-degree array: O(E)
- Initial scan for in-degree-zero nodes: O(N)
- BFS visits each node once and each edge once: O(N + E)

### Space: O(N + E)
- Adjacency list: O(E)
- In-degree array and queue: O(N)

## 7. Key Takeaway

- Converting course prerequisites into a directed graph and checking for cycles is the core challenge
- Kahn's algorithm (BFS with in-degrees) efficiently detects cycles and computes a valid ordering in linear time 