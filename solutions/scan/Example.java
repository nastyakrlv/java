package scan;

import java.io.*;

public class Example {
    public static void main(String[] args) throws IOException {
        Scan scant = new Scan(System.in);
        System.out.println("Введите слова через пробел:");
        StringBuilder word;
        while (scant.hasNextLine()) {
            word = new StringBuilder(scant.nextLine());
            System.out.println("Прочитано слово: " + word);
        }

    }
}

