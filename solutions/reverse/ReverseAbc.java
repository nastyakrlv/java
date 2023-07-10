package reverse;
import scan.Scan;


import java.util.Arrays;

public class ReverseAbc {
    public static void main(String[] args) {
        Scan in = new Scan(System.in);
        String line;
        int elem = -1;
        String[][] reverse = new String[4096][];
        while (in.hasNextLine()) {
            elem++;
            if (elem >= reverse.length) {
                reverse = Arrays.copyOf(reverse, reverse.length * 2);
            }
            reverse[elem] = new String[16];
            line = in.nextLine();
            Scan currentStringScanner = new Scan(line);
            int i = 0;
            while (currentStringScanner.hasNextInt()) {
                i++;
                if (i >= reverse[elem].length) {
                    reverse[elem] = Arrays.copyOf(reverse[elem], reverse[elem].length*2);
                }
                reverse[elem][i] = currentStringScanner.nextInt();
            }
            reverse[elem][0] = String.valueOf(i);
        }
        for (int i = elem; i>=0; i--) {
            int length = Integer.parseInt(reverse[i][0]);
            for (int j = length; j >= 1; j--) {
                System.out.print(reverse[i][j] + " ");
            }
            System.out.println();
        }
    }
}
