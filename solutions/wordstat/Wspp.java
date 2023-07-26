package wordstat;

import scan.Scan;
import wspp.IntList;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new IllegalArgumentException("Expected 2 arguments, got " + args.length);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]),
                    StandardCharsets.UTF_8
            )); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    StandardCharsets.UTF_8
            ))) {
                Map<String, IntList> words = new LinkedHashMap<>();
                int number = 1;
                String line;
                while ((line = reader.readLine()) != null) {
                    StringBuilder currWord = new StringBuilder();
                    for (int i = 0; i < line.length(); i++) {
                        char read = line.charAt(i);
                        // Запоминаем, хорошая ли буква
                        boolean accepted = Character.isLetter(read) || Character.getType(read) == Character.DASH_PUNCTUATION || read == '\'';
                        // Если хорошая, добавляем в текущее слово
                        if (accepted) {
                            currWord.append(Character.toLowerCase(read));
                        }
                        // Если у нас набрано непустое слово и мы
                        // (1 вариант) встали на плохую букву или (2 вариант) у нас заканчивается строка,
                        // добавляем слово в мапу.
                        if (!currWord.isEmpty() && (!accepted || (i + 1 == line.length()))) {
                            words.putIfAbsent(currWord.toString(), new IntList());
                            words.get(currWord.toString()).add(number++);
                            currWord = new StringBuilder();
                        }
                    }
                }
                for (Map.Entry<String, IntList> entry : words.entrySet()) {
                    String key = entry.getKey();
                    IntList value = entry.getValue();
                    writer.write(key + " " + value.getLength() + " " + value);
                    writer.newLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Input/output error: " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}

