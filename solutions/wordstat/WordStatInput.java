package wordstat;

import java.io.*;
import java.util.*;

public class WordStatInput {
    public static void main(String[] args) {
        try { //открыли файл для считывания
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]),
                    "UTF8"
            ));
            try { //открыли файл для записи
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "UTF8"
                ));
                try { // основной код
                    LinkedHashMap<String,Integer> words = new LinkedHashMap<String, Integer>();
                    int read = reader.read();
                    String currWord = "";
                    while (read >= 0) {
                        System.out.println((char) read);
                        if (Character.isLetter(read) || Character.getType(read)==Character.DASH_PUNCTUATION || read==39) { //проверяем нужный ли символ
                            char readchar = (char) read; //из числа в символ
                            if (!Character.isLowerCase(readchar)) {
                                readchar = Character.toLowerCase(readchar); //переводим в нижний регистер
                            }
                            currWord += String.valueOf(readchar); //добавляем символ к строке - текущее слово
                        } else {
                            if (!currWord.isEmpty()) {
                                if (words.containsKey(currWord)) {
                                    words.put(currWord, words.get(currWord) + 1);
                                } else {
                                    words.put(currWord, 1);
                                }
                                currWord = "";
                            }
                        }
                        read = reader.read();
                    }
                    if (!currWord.isEmpty()) {
                        if (words.containsKey(currWord)) {
                            words.put(currWord, words.get(currWord) + 1);
                        } else {
                            words.put(currWord, 1);
                        }
                        currWord = "";
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
        } catch (IOException e) { //проверяемое исключение
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }
}
