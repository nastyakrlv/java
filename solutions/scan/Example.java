package scan;

import java.io.*;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) throws IOException {
        StringBuilder output = new StringBuilder();
        String line = "abcdefghij";
        for (int j = 0; j < line.length(); j++) {
            char ch = line.charAt(j);
            if (Character.isLetter(ch)) {
                int number = ch - 'a';
                output.append(number);
            } else {
                output.append(ch);
            }
        }
        System.out.println(output);

    }
}

