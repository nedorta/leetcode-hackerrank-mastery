import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    // Complete the evenForest function below.
    static int evenForest(int t_nodes, int t_edges, List<Integer> t_from, List<Integer> t_to) {
        // Create a tree from the given edges
        List<List<Integer>> tree = new ArrayList<>();
        for (int i = 0; i < t_nodes; i++) {
            tree.add(new ArrayList<>());
        }
        for (int i = 0; i < t_edges; i++) {
            tree.get(t_from.get(i) - 1).add(t_to.get(i) - 1);
            tree.get(t_to.get(i) - 1).add(t_from.get(i) - 1);
        }

        int[] count = new int[t_nodes];
        boolean[] visited = new boolean[t_nodes];
        int[] removedEdges = new int[1];

        dfs(0, visited, count, removedEdges, tree);

        return removedEdges[0];
    }

    static void dfs(int node, boolean[] visited, int[] count, int[] removedEdges, List<List<Integer>> tree) {
        visited[node] = true;
        count[node] = 1;

        for (int neighbor : tree.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited, count, removedEdges, tree);
                if (count[neighbor] % 2 == 0) {
                    removedEdges[0]++;
                    count[neighbor] = 0;
                }
                count[node] += count[neighbor];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] tNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int tNodes = Integer.parseInt(tNodesEdges[0]);
        int tEdges = Integer.parseInt(tNodesEdges[1]);

        List<Integer> tFrom = new ArrayList<>();
        List<Integer> tTo = new ArrayList<>();

        IntStream.range(0, tEdges).forEach(i -> {
            try {
                String[] tFromTo = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                tFrom.add(Integer.parseInt(tFromTo[0]));
                tTo.add(Integer.parseInt(tFromTo[1]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = evenForest(tNodes, tEdges, tFrom, tTo);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
