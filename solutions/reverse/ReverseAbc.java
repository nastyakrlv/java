package reverse;

import scan.Scan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseAbc {
    public static void main(String[] args) {
        try {
            Scan inn = new Scan(System.in);
            try {
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
                    char[] cs = line.toCharArray(); // Норм.
                    for (int i = 0; i < cs.length; i++) {
                        if (Character.isLetter(cs[i])) {
                            int ch = (cs[i] - 'a');
                            cs[i] = (char) (ch + '0');
                        }
                    }
                    try {
                        Scan currentStringScanner = new Scan(new String(cs));
                        try {
                            int i = 0;
                            while (currentStringScanner.hasNextInt()) {
                                i++;
                                if (i >= reverse[elem].length) {
                                    reverse[elem] = Arrays.copyOf(reverse[elem], reverse[elem].length * 2);
                                }
                                reverse[elem][i] = currentStringScanner.nextInt();
                            }
                            reverse[elem][0] = i;
                        } finally {
                            currentStringScanner.close();
                        }
                    } catch (IOException e) {
                        System.err.println("ERROR:" + e.getLocalizedMessage());
                    }
                }
                for (int i = elem; i >= 0; i--) {
                    int length = reverse[i][0];
                    for (int j = length; j >= 1; j--) {
                        String ok = Integer.toString(reverse[i][j]);
                        char[] cs = ok.toCharArray();
                        for (int l = 0; l < cs.length; l++) {
                            if (Character.isDigit(cs[l])) {
                                int ch = (cs[l] - '0');
                                cs[l] = (char) (ch + 'a');
                            }
                        }
                        System.out.print(new String(cs) + " ");
                    }
                    System.out.println();
                }
                // TODO: обработка ошибок (и работа с ресурсами) сюда же * сделала finally + добавила другую замену символов - работает чуть быстрее
                /*
                  Обрати внимание на обработку ошибок в WordStatWords.
                 */
            } finally {
                inn.close();
            }
        } catch (IOException e) {
            System.err.println("ERROR:" + e.getLocalizedMessage());
        }
    }
}
