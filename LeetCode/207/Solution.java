class Solution {
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