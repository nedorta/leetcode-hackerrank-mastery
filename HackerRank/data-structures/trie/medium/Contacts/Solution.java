import java.io.*;
import java.util.*;

class Result {

    public static List<Integer> contacts(List<List<String>> queries) {
    List<Integer> result = new ArrayList<>();
    Map<String, Integer> trie = new HashMap<>();

    for (List<String> query : queries) {
        String operation = query.get(0);
        String word = query.get(1);

        if (operation.equals("add")) {
            for (int i = 1; i <= word.length(); i++) {
                String prefix = word.substring(0, i);
                int count = 0;
                if (trie.containsKey(prefix)) {
                    count = trie.get(prefix);
                }
                trie.put(prefix, count + 1);
            }
        } else if (operation.equals("find")) {
            int count = 0;
            if (trie.containsKey(word)) {
                count = trie.get(word);
            }
            result.add(count);
        }
    }

    return result;
}

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        for (int i = 0; i < queriesRows; i++) {
            queries.add(Arrays.asList(bufferedReader.readLine().replaceAll("\\s+$", "").split(" ")));
        }

        List<Integer> result = Result.contacts(queries);

        for (int i = 0; i < result.size(); i++) {
            bufferedWriter.write(String.valueOf(result.get(i)));

            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
