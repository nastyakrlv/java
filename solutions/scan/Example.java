package scan;

import java.io.*;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) throws IOException {
        Scan scant = new Scan("4");
        while (scant.hasNextInt()) {
            System.out.println(scant.nextInt());
        }
    }
}

