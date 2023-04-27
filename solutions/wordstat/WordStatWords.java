package wordstat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

// TODO: Всё ок.

public class WordStatWords {
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
                    TreeMap<String, Integer> words = new TreeMap<>();
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
                                words.put(currWord.toString(), words.getOrDefault(currWord.toString(), 0) + 1);
                                currWord = new StringBuilder();
                            }
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
            System.out.println("Input file not found: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.println("Input/output error: " + e.getLocalizedMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}