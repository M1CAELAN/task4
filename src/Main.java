import java.util.*;
public class Main {
    public static String nonRepeatable(String s) {
        if (s.length() == 0) {
            return "";
        } else if (s.substring(0, s.length() - 1).contains(s.substring(s.length() - 1, s.length()))) {
            return nonRepeatable(s.substring(0, s.length() - 1));
        } else {
            return nonRepeatable(s.substring(0, s.length() - 1)) + s.substring(s.length() - 1, s.length());
        }
    }
    public static List<String> generateBrackets(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0) {
            result.add("");
        } else if (n == 1) {
            result.add("()");
        } else {
            for (int i = 0; i < n; i++) {
                for (String left : generateBrackets(i)) {
                    for (String right : generateBrackets(n - i - 1)) {
                        result.add("(" + left + ")" + right);
                    }
                }
            }
        }
        return result;
    }
    public static List<String> binarySystem(int n) {
        if (n == 1) {
            List<String> result = new ArrayList<>();
            result.add("0");
            result.add("1");
            return result;
        } else {
            List<String> prev = binarySystem(n-1);
            List<String> result = new ArrayList<>();
            for (String s : prev) {
                result.add(s + "0");
                result.add(s + "1");
            }
            return result;
        }
    }

    public static List<String> noAdjacentOnesAndZeros(int n) {
        List<String> result = new ArrayList<>();
        for (String s : binarySystem(n)) {
            if (!s.contains("00")) {
                result.add(s);
            }
        }
        return result;
    }
    public static String alphabeticRow(String s) {
        int max_len = 0;
        int curr_len = 1;
        String row = s.substring(0, 1);
        String longest_row = "";
        boolean voln = true;
        for (int i = 1; i < s.length(); i++) {
            if ((((int)s.charAt(i) == (int)s.charAt(i-1) + 1) && (curr_len == 1 || voln) || (((int)s.charAt(i) == (int)s.charAt(i-1) - 1) && (curr_len == 1 || !voln)))){
                curr_len++;
                row += s.charAt(i);
                voln = (int)s.charAt(i) == (int)s.charAt(i-1) + 1;
            } else {
                if (curr_len > max_len) {
                    max_len = curr_len;
                    longest_row = row;
                }
                curr_len = 1;
                row = s.substring(i, i + 1);
            }
        }
        if (curr_len > max_len) {
            longest_row = row;
        }
        return longest_row;
    }
    public static String countAndSort(String s) {
        HashMap<Integer, String> elements = new HashMap<>();
        int countInt = 0;
        String result = "";
        for(int i = 0; i < s.length()-1; i++){
            countInt+=1;
            if(s.charAt(i) != s.charAt(i+1)) {
                if(elements.containsKey(countInt) ){
                    elements.put(countInt, elements.get(countInt) + s.charAt(i));
                } else{
                    elements.put(countInt, String.valueOf(s.charAt(i)));
                }
                countInt = 0;
            }
        }
        if(elements.containsKey(countInt + 1) ){
            elements.put(countInt + 1, elements.get(countInt + 1) + s.charAt(s.length()-1));
        } else{
            elements.put(countInt + 1, String.valueOf(s.charAt(s.length()-1)));
        }
        List<Map.Entry<Integer, String>> sortedCount = new ArrayList<>(elements.entrySet());
        sortedCount.sort(Map.Entry.comparingByKey());
        for (Map.Entry<Integer, String> entry : sortedCount) {
            for(char c: entry.getValue().toCharArray()){
                result += String.valueOf(c) + entry.getKey();
            }
        }
        return result;
    }
    public static int convertToNum(String str){
        int result = 0;
        String [] num = {"one", "tw", "th", "fo", "fi", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
        HashMap <String, Integer> num_hash = new HashMap<>();
        for(int i = 1; i <= num.length; i++){
            num_hash.put(num[i-1], i);
        }
        String [] word = str.split(" ");
        for(int i = 0; i < word.length; i++){
            for(Map.Entry<String, Integer> entry : num_hash.entrySet()){
                if(word[i].contains(entry.getKey())){
                    if(word[i].contains("ty")){
                        result += entry.getValue()*10;
                    }else if(word[i].contains("teen")){
                        result += entry.getValue()+10;
                    }else {
                        result += entry.getValue();
                    }
                }
            }
            if(word[i].equals("hundred")){
                result *= 100;
            }
        }
        return result;
    }
    public static String uniqueSubstring(String s) {
        int start = 0;
        int end = 0;
        int max_length = 0;
        int max_start = 0;
        int max_end = 0;
        List<Character> unique_chars = new ArrayList<>();

        while (end < s.length()) {
            char c = s.charAt(end);
            if (unique_chars.contains(c)) {
                if (end - start > max_length) {
                    max_length = end - start;
                    max_start = start;
                    max_end = end;
                }

                while (s.charAt(start) != c) {
                    unique_chars.remove(start);
                    start++;
                }
                start++;
            }

            unique_chars.add(c);
            end++;
        }

        if (end - start > max_length) {
            max_length = end - start;
            max_start = start;
            max_end = end;
        }

        return s.substring(max_start, max_end);
    }
    public static int shortestWay(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        dp[0][0] = grid[0][0];
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[n-1][n-1];
    }
    public static String numericOrder(String input) {
        String[] words = input.split(" ");
        Map<Integer, String> wordMap = new HashMap<>();

        for (String word : words) {
            int index = 0;
            StringBuilder sb = new StringBuilder();
            for (char c : word.toCharArray()) {
                if (Character.isDigit(c)) {
                    index = Character.getNumericValue(c);
                } else {
                    sb.append(c);
                }
            }
            wordMap.put(index, sb.toString());
        }

        List<String> orderedWords = new ArrayList<>();
        for (int i = 1; i <= wordMap.size(); i++) {
            orderedWords.add(wordMap.get(i));
        }

        return String.join(" ", orderedWords);
    }
    public static int switchNums(int num1, int num2) {
        Integer[] num1Arr = new Integer[String.valueOf(num1).length()];
        int[] num2Arr = new int[String.valueOf(num2).length()];
        int i = 0, result = 0;
        while(num1 > 0){
            num1Arr[i] = num1 % 10;
            num1 = num1 / 10;
            i++;
        }
        i = String.valueOf(num2).length() - 1;
        while(num2 > 0){
            num2Arr[i] = num2 % 10;
            num2 = num2 / 10;
            i--;
        }
        Arrays.sort(num1Arr, Collections.reverseOrder());;
        i = 0;
        for (int j = 0; j < num1Arr.length; j++) {
            int w = i;
            while (w < num1Arr.length) {
                if (num2Arr[j] < num1Arr[w] ) {
                    num2Arr[j] = num1Arr[w];
                    i++;
                }
                w++;
            }
        }
        for(int j = 0; j < num2Arr.length; j++){
            result = result * 10;
            result += num2Arr[j] ;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("------1------");
        System.out.println(nonRepeatable("abracadabra"));
        System.out.println(nonRepeatable("paparazzi"));

        System.out.println("------2------");
        System.out.println(generateBrackets(1));
        System.out.println(generateBrackets(2));
        System.out.println(generateBrackets(3));

        System.out.println("------3------");
        System.out.println(noAdjacentOnesAndZeros(3));
        System.out.println(noAdjacentOnesAndZeros(4));

        System.out.println("------4------");
        System.out.println(alphabeticRow("abcdjuwx"));
        System.out.println(alphabeticRow("klmabzyxw"));

        System.out.println("------5------");
        System.out.println(countAndSort("aaabbcdd"));
        System.out.println(countAndSort("vvvvaajaaaaa"));

        System.out.println("------6------");
        System.out.println(convertToNum("zero"));
        System.out.println(convertToNum("five hundred sixty seven"));
        System.out.println(convertToNum("thirty one"));

        System.out.println("------7------");
        System.out.println(uniqueSubstring("123412324"));
        System.out.println(uniqueSubstring("111111"));
        System.out.println(uniqueSubstring("77897898"));

        System.out.println("------8------");
        int[][] grid1 = {{1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}};
        System.out.println(shortestWay(grid1));
        int[][] grid2 = {{2, 7, 3},
                {1, 4, 8},
                {4, 5, 9}};
        System.out.println(shortestWay(grid2));

        System.out.println("------9------");
        System.out.println(numericOrder("t3o the5m 1One all6 r4ule ri2ng"));
        System.out.println(numericOrder("re6sponsibility Wit1h gr5eat power3 4comes g2reat"));

        System.out.println("------10------");
        System.out.println(switchNums(519, 723));
        System.out.println(switchNums(491, 3912));
        System.out.println(switchNums(6274, 71259));
    }
}