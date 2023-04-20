package reverse;
import java.util.Arrays;
import java.util.Scanner;
public class Reverse {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        int elem = -1;
        int[][] reverse = new int[1][];
        while (in.hasNextLine()) {
            elem++;
            if (elem >= reverse.length) {
                reverse = Arrays.copyOf(reverse, reverse.length * 2);
            }
            reverse[elem] = new int[2];
            line = in.nextLine();
            Scanner currentStringScanner = new Scanner(line);
            int i = 0;
            while (currentStringScanner.hasNextInt()) {
                i++;
                if (i >= reverse[elem].length) {
                    reverse[elem] = Arrays.copyOf(reverse[elem], reverse[elem].length*2);
                }
                reverse[elem][i] = currentStringScanner.nextInt();
            }
            reverse[elem][0] = i;
        }
        for (int i = elem; i>=0; i--) {
            for (int j = reverse[i][0]; j >= 1; j--) {
                System.out.print(reverse[i][j] + " ");
            }
            System.out.println();
        }
    }
}
