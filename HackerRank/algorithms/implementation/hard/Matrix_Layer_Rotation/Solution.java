import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

class Result {

    public static void matrixRotation(List<List<Integer>> matrix, int r) {
        int m = matrix.size();
        int n = matrix.get(0).size();
        int[][] result = new int[m][n];

        int numLayers = Math.min(m, n) / 2;

        for (int layer = 0; layer < numLayers; layer++) {
            List<Integer> numbers = new ArrayList<>();
            List<Pair> indices = new ArrayList<>();

            // Get the numbers and indices for this layer
            for (int i = layer; i < m - layer; i++) {
                numbers.add(matrix.get(i).get(layer));
                indices.add(new Pair(i, layer));
            }
            for (int j = layer + 1; j < n - layer; j++) {
                numbers.add(matrix.get(m - layer - 1).get(j));
                indices.add(new Pair(m - layer - 1, j));
            }
            for (int i = m - layer - 2; i >= layer; i--) {
                numbers.add(matrix.get(i).get(n - layer - 1));
                indices.add(new Pair(i, n - layer - 1));
            }
            for (int j = n - layer - 2; j > layer; j--) {
                numbers.add(matrix.get(layer).get(j));
                indices.add(new Pair(layer, j));
            }

            // Rotate the numbers
            int shift = r % numbers.size();
            Collections.rotate(numbers, shift);

            // Put the rotated numbers back into the matrix
            for (int i = 0; i < numbers.size(); i++) {
                Pair pair = indices.get(i);
                result[pair.x][pair.y] = numbers.get(i);
            }
        }

        // Print the rotated matrix
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);
        int n = Integer.parseInt(firstMultipleInput[1]);
        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            matrix.add(
                Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList())
            );
        }

        Result.matrixRotation(matrix, r);

        bufferedReader.close();
    }
}
