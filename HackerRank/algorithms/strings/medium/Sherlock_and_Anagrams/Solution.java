package HackerRank.domains.algorithms.strings.medium.Sherlock_and_Anagrams;

import java.io.*;
import java.util.*;
import java.util.stream.*;

class Result {

    public static int sherlockAndAnagrams(String s) {
        int result = 0;

        for(int counter = s.length(); counter >= 1; counter--) {

            for(int i = 0; i + counter <= s.length(); i++) {

                String firstSubString = s.substring(i, i + counter);

                char[] firstSubStrArray = firstSubString.toCharArray();
                Arrays.sort(firstSubStrArray);
                String str1 = String.valueOf(firstSubStrArray);

                for(int j = i + 1; j + counter <= s.length(); j++) {
                    String secondSubString = s.substring(j, j + counter);

                    char[] secondSubStrArray = secondSubString.toCharArray();
                    Arrays.sort(secondSubStrArray);
                    String str2 = String.valueOf(secondSubStrArray);

                    if(str1.equals(str2)) {
                        result++;
                    }
                }
            }

        }

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.sherlockAndAnagrams(s);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
