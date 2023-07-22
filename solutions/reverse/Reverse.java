package reverse;
import scan.Scan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        try (Scan inn = new Scan(System.in)) {
            String line;
            int elem = -1;
            int[][] reverse = new int[256][];
            while (inn.hasNextLine()) {
                elem++;
                if (elem >= reverse.length) {
                    reverse = Arrays.copyOf(reverse, reverse.length * 2);
                }
                reverse[elem] = new int[16];
                line = inn.nextLine();
                try (Scan currentStringScanner = new Scan(line)) {
                        int i = 0;
                        while (currentStringScanner.hasNextInt()) {
                            i++;
                            if (i >= reverse[elem].length) {
                                reverse[elem] = Arrays.copyOf(reverse[elem], reverse[elem].length * 2);
                            }
                            reverse[elem][i] = currentStringScanner.nextInt();
                        }
                        reverse[elem][0] = i;

                } catch (IOException e) {
                    System.err.println("ERROR:" + e.getLocalizedMessage());
                }
            }
            for (int i = elem; i >= 0; i--) {
                int length = reverse[i][0];
                for (int j = length; j >= 1; j--) {
                    System.out.print(reverse[i][j] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("ERROR:" + e.getLocalizedMessage());
        }
    }
}
