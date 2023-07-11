package scan;

import java.io.*;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) throws IOException {
        Scanner scant = new Scanner(" --^%#*Q(%&Q(%= 123");
        System.out.print(scant.hasNextInt());
    }
}

