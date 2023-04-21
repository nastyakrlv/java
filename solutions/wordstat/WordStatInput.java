package wordstat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/* TODO: исправь ошибки и сделай модификацию words */

public class WordStatInput {
    public static void main(String[] args) {
        try { //открыли файл для считывания
            if (args.length != 2) {
                throw new IllegalArgumentException("Expected 2 arguments, got " + args.length);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]),
                    StandardCharsets.UTF_8
            ));
            try { //открыли файл для записи
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        StandardCharsets.UTF_8
                ));
                try { // основной код
                    LinkedHashMap<String, Integer> words = new LinkedHashMap<>();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        StringBuilder currWord = new StringBuilder();
                        for (int i = 0; i < line.length(); i++) {
                            char read = line.charAt(i);
                            if (Character.isLetter(read) || Character.getType(read) == Character.DASH_PUNCTUATION || read == 39) {
                                currWord.append(Character.toLowerCase(read));
                            } else {
                                if (currWord.length() > 0) {
                                    words.put(currWord.toString(), words.getOrDefault(currWord.toString(), 0) + 1);
                                    currWord = new StringBuilder();
                                }
                            }
                        }
                        if (currWord.length() > 0) {
                            words.put(currWord.toString(), words.getOrDefault(currWord.toString(), 0) + 1);
                        }
                    }
                    for (String key : words.keySet()) {
                        writer.write(key + " " + words.get(key));
                        writer.newLine();
                    }
                } finally { //закрыли файл для записи
                    writer.close();
                }
            } finally { //закрыли файл для чтения
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Input/output error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}