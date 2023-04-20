package wordstat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/* TODO: исправь ошибки и сделай модификацию words */

public class WordStatInput {
    public static void main(String[] args) {
        try { //открыли файл для считывания
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(args[0]), // :MISTAKE: А если массив args пустой?
                    // :NOTE: лучше здесь вместо литерала строки импользовать StandardCharsets.UTF_8
                    "UTF8"
            ));
            try { //открыли файл для записи
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(args[1]), // :MISTAKE: то же самое
                        // :NOTE: здесь тоже
                        "UTF8"
                ));
                try { // основной код
                    /* :NOTE: повторно типы можно не писать, они подставятся сами
                     * LinkedHashMap<String,Integer> words = new LinkedHashMap<>() */
                    LinkedHashMap<String,Integer> words = new LinkedHashMap<String, Integer>();
                    /* :MISTAKE: зачем использовать BufferedReader, если ты считываешь посимвольно? Это глупо.
                     * Но так как посимвольное чтение вне зависимости от use-кейса всегда произрывает
                     * буферизированному, либо реализуй его сама, либо используй построчное считывание у BR'а. */
                    int read = reader.read();
                    String currWord = "";
                    while (read >= 0) {
                        System.out.println((char) read); // :NOTE: Зачем здесь это ?
                        if (Character.isLetter(read) || Character.getType(read)==Character.DASH_PUNCTUATION || read==39) { //проверяем нужный ли символ
                            char readchar = (char) read; //из числа в символ
                            if (!Character.isLowerCase(readchar)) { // :NOTE: isUpperCase ?)
                                readchar = Character.toLowerCase(readchar); //переводим в нижний регистер
                            }
                            // :NOTE: Вообще можно просто написать toLowerCase без ифа. Это приведёт ровно к такому же поведению

                            /* :MISTAKE: строки иммутабельны так то ._. -> O(n^2)
                             * Может всё-таки StringBuilder? */
                            currWord += String.valueOf(readchar);
                        } else {
                            if (!currWord.isEmpty()) {
                                // :NOTE: почитай про метод getOrDefault и 4 строчки превратятся в одну
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
                        // :MISTAKE: копипаста
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
            /* :MISTAKE: Всего 1 catch? Прочитай наиболее распространенные ошибки и сделай под них отдельный кетчи.*/
        } catch (IOException e) { //проверяемое исключение
            // :NOTE: В общем случае лучше getLocalizedMessage
            System.out.println("Input file reading error: " + e.getMessage());
        }
    }
}
