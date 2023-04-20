package reverse;
import java.util.Arrays;
import java.util.Scanner;
public class ReverseTranspose {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String line;
        int elem = -1;
        int[][] reverse = new int[1][];
        while (in.hasNextLine()) {
            line = in.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            elem++;
            if (elem >= reverse.length) {
                reverse = Arrays.copyOf(reverse, reverse.length * 2);
            }
            reverse[elem] = new int[2];
            Scanner currentStringScanner = new Scanner(line);
            int i = 0;
            while (currentStringScanner.hasNextInt()) {
                i++;
                if (i >= reverse[elem].length) {
                    reverse[elem] = Arrays.copyOf(reverse[elem], reverse[elem].length * 2);
                }
                reverse[elem][i] = currentStringScanner.nextInt();
            }
            reverse[elem][0] = i;
        }
        int maxim = 0;
        for (int i=0; i <= elem; i++) {
            if (reverse[i][0] > maxim) {
                maxim = reverse[i][0];
            }
        }
        int rows = elem+1;
        int columns = maxim;
        int[][] transposedArr = new int[columns][rows];
        for(int i = 0; i < rows; i++) {
            for(int j = 1; j <= reverse[i][0]; j++) {
                transposedArr[j-1][i] = reverse[i][j];
            }
        }
        for(int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (transposedArr[i][j] == 0 && i >= reverse[j][0]) {
                    System.out.print("");
                } else {
                    System.out.print(transposedArr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
