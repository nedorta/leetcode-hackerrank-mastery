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

class Result {

    public static List<Integer> matchingStrings(List<String> stringList, List<String> queries) {
        HashMap<String, Integer> solMap = new HashMap<>();

        for (int i = 0; i < stringList.size(); i++) {
            if (solMap.containsKey(stringList.get(i))) {
                solMap.put(stringList.get(i), solMap.get(stringList.get(i)) + 1);
            } else {
                solMap.put(stringList.get(i), 1);
            }
        }

        ArrayList<Integer> resultArray = new ArrayList<>();

        for (int i = 0; i < queries.size(); i++) {
            resultArray.add(solMap.getOrDefault(queries.get(i), 0));
        }

        return resultArray;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int stringListCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> stringList = IntStream.range(0, stringListCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<Integer> res = Result.matchingStrings(stringList, queries);

        bufferedWriter.write(
            res.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
