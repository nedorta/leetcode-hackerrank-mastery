import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static class Graph {
        private ArrayList<LinkedList<Integer>> graph;
        private boolean[] isMarked;

        public Graph(int size) {
            isMarked = new boolean[size];
            graph = new ArrayList<LinkedList<Integer>>(size);

            for (int counter = 0; counter < size; counter++){
                graph.add(new LinkedList<Integer>());
            }
        }

        public void addEdge(int first, int second) {
            graph.get(first).add(second);
            graph.get(second).add(first);
        }

        private LinkedList<Integer> getAdjacency(int vertex){
            return graph.get(vertex);
        }

        public int[] shortestReach(int startId) { // 0 indexed
            int[] result = new int[graph.size()];

            for (int counter = 0; counter < graph.size(); counter++){
                result[counter] = -1;
            }

            LinkedList<Integer> workingNodes = getAdjacency(startId);

            int totalWeight = 6;

            while(!workingNodes.isEmpty()){
                LinkedList<Integer> newNodes = new LinkedList<Integer>();

                for (int vertex: workingNodes){
                    if (!isMarked[vertex]) {
                        isMarked[vertex] = true;
                        newNodes.addAll(getAdjacency(vertex));
                        result[vertex] = totalWeight;
                    }
                }

                workingNodes = newNodes;
                totalWeight += 6;
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int queries = scanner.nextInt();

        for (int t = 0; t < queries; t++) {

            // Create a graph of size n where each edge weight is 6:
            Graph graph = new Graph(scanner.nextInt());
            int m = scanner.nextInt();

            // read and set edges
            for (int i = 0; i < m; i++) {
                int u = scanner.nextInt() - 1;
                int v = scanner.nextInt() - 1;

                // add each edge to the graph
                graph.addEdge(u, v);
            }

            // Find shortest reach from node s
            int startId = scanner.nextInt() - 1;
            int[] distances = graph.shortestReach(startId);

            for (int i = 0; i < distances.length; i++) {
                if (i != startId) {
                    System.out.print(distances[i]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        scanner.close();
    }
}
