package scan;

import java.io.*;
import java.util.Arrays;

public class Scan {
    private final BufferedReader reader;
    private char[] buffer;
    private int flag;
    private int usefulBufferLength;
    private int bufferIndex;

    public Scan(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        buffer = new char[4096];
        usefulBufferLength = bufferIndex = flag = 0;
    }

    public Scan(String inputStream) {
        this(new ByteArrayInputStream(inputStream.getBytes()));
    }

    public boolean hasNextLine() throws IOException {
        int startIndex = bufferIndex;
        while (flag != -1) {
            char symbol = nextChar();
            // TODO: поддержать \n\r или \r\n
            if (symbol == '\n' || symbol == '\r') {
                // возвращаем индекс в начало после прочтения строки
                bufferIndex = startIndex;
                return true;
            }
        }
        // возвращаем индекс в начало, если мы достигли конца файла
        bufferIndex = startIndex;
        return false;
    }

    public String nextLine() throws IOException {
        //считывает всю введенную строку
        StringBuilder lineBuffer = new StringBuilder();
        for (char symbol = nextChar();
            // TODO: поддержать \n\r или \r\n
             symbol != '\n' && symbol != '\r' && flag!=-1;
             symbol = nextChar()) {
            lineBuffer.append(symbol);
        }
        return lineBuffer.toString();
    }

    public boolean hasNextInt() throws IOException {
        //проверяет, что следующая последовательность символов является int-ом
        int startIndex = bufferIndex;
        StringBuilder lineBuffer = new StringBuilder();
        while (flag != -1) {
            char symbol = nextChar();
            // TODO: хуйня
            if (Character.isDigit(symbol) || symbol == 'a' || symbol == 'b' || symbol == 'c' || symbol == 'd' || symbol == 'e' || symbol == 'f' || symbol == 'g' || symbol == 'h' || symbol == 'i' || symbol == 'j' || symbol == '+' || symbol == '-') {
                lineBuffer.append(symbol);
            } else {
                // TODO: парсятся некорректный числа, переработать
                if (lineBuffer.length() == 0) {
                    if (flag==-1) {
                        bufferIndex = startIndex;
                        return false;
                    }
                } else {
                    bufferIndex = startIndex;
                    return true;
                }
            }
        }
        // возвращаем индекс в начало, если мы достигли конца файла
        bufferIndex = startIndex;
        return lineBuffer.length() > 0;
    }

    public String nextInt() throws IOException {
        //считывает введенное число int
        StringBuilder lineBuffer = new StringBuilder();
        // TODO: for лучше
        while (true) {
            if (flag==-1 && usefulBufferLength ==bufferIndex) {
                break;
            }
            char symbol = nextChar();
            // TODO: хуйня + парсятся некорректный числа, переработать
            if (Character.isDigit(symbol) || symbol  == 'a' || symbol == 'b' || symbol == 'c' || symbol == 'd' || symbol == 'e' || symbol == 'f' || symbol == 'g' || symbol == 'h' || symbol == 'i' || symbol == 'j' || symbol == '+' || symbol == '-') {
                lineBuffer.append(symbol);
            } else {
                if (lineBuffer.length() > 0 && Character.isWhitespace(symbol)) {
                    break;
                }
            }
        }
        return lineBuffer.toString();
    }

    // TODO: обработай ошибку заходи за конец потока
    protected char nextChar() throws IOException { // TODO: весь поток всегда хранится в памяти, переделать
        if (bufferIndex >= usefulBufferLength) {
            if (bufferIndex >= buffer.length) {
                buffer = Arrays.copyOf(buffer, buffer.length * 2);
            }
            flag = reader.read(buffer, bufferIndex, buffer.length - bufferIndex);
            usefulBufferLength += flag;
            if (flag == -1) {
                reader.close();
                usefulBufferLength++;
            }
        }
        return buffer[this.bufferIndex++];
    }

}


