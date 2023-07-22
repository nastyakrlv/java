package scan;

import java.io.*;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) throws IOException {
        Scan scant = new Scan("123456  7 8 890");
        while (scant.hasNextInt()) {
            System.out.println(scant.nextInt());
        }
    }
}

