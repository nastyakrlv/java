package sum;

public class SumDouble {
    public static void main(String[] args) {
        double sum = 0;
        int i = 0;
        int j = 0;
        for (String currentArg : args) {
            while (i < currentArg.length()) {
                while (j < currentArg.length() && !Character.isWhitespace(currentArg.charAt(j))) {
                    j++;
                }
                String tmp = currentArg.substring(i, j);
                if (!tmp.isEmpty()) {
                    sum += Double.parseDouble(tmp);
                }
                j++;
                i = j;
            }
            i = j = 0;
        }
        System.out.println(sum);
    }
}