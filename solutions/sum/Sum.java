package sum;

public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        int i = 0;
        int j = 0;
        for (String currentArg : args) {
            while (i < currentArg.length()) {
                while (j < currentArg.length() && !Character.isWhitespace(currentArg.charAt(j))) {
                    j++;
                }
                String tmp = currentArg.substring(i, j);
                if (!tmp.isEmpty()) {
                    sum += Integer.parseInt(tmp);
                }
                j++;
                i = j;
            }
            i = j = 0;
        }
        System.out.println(sum);
    }
}