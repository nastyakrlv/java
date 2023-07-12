package reverse;

import scan.Scan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ReverseAbc {
    public static void main(String[] args) {
        try {
            Scan in = new Scan(System.in);
            String line;
            int elem = -1;
            int[][] reverse = new int[256][];
            while (in.hasNextLine()) {
                elem++;
                if (elem >= reverse.length) {
                    reverse = Arrays.copyOf(reverse, reverse.length * 2);
                }
                reverse[elem] = new int[16];
                line = in.nextLine();

//                StringBuilder output = new StringBuilder();
//                for (int j = 0; j < line.length(); j++) {
//                    char ch = line.charAt(j);
//                    if (Character.isLetter(ch)) {
//                        int number = ch - 'a';
//                    } else {
//                        output.append(ch);
//                    }
//                }
//                Scanner currentStringScanner = new Scanner(output.toString());
                char[] cs = line.toCharArray();
                for (int i = 0; i < cs.length; i++) {
                    if (Character.isLetter(cs[i])) {
                        int ch = (cs[i] - 'a');
                        cs[i] = (char) (ch + '0');
                    }
                }

                Scanner currentStringScanner = new Scanner(new String(cs));

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
            for (int i = elem; i >= 0; i--) {
                int length = reverse[i][0];
                for (int j = length; j >= 1; j--) {
                    String ok = Integer.toString(reverse[i][j]);
                    StringBuilder output = new StringBuilder();
                    for (int l = 0; l < ok.length(); l++) {
                        char ch = ok.charAt(l);
                        if (Character.isDigit(ch)) {
                            int number = Character.getNumericValue(ch);
                            char letter = (char) ('a' + number);
                            output.append(letter);
                        } else {
                            output.append(ch);
                        }
                    }
                    System.out.print(output + " ");
                }
                System.out.println();
            }
            // TODO: обработка ошибок (и работа с ресурсами) сюда же
        } catch (IOException e) {
            System.err.println("ERROR:" + e.getLocalizedMessage());
        }
    }
}
