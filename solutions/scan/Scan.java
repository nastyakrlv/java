package scan;

import java.io.*;
import java.util.Arrays;

public class Scan {
    private final BufferedReader reader;
    private char[] buffer;
    private int flag;
    private int bufferLength;
    private int bufferIndex;

    public Scan(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        int size = 4096;
        buffer = new char[size];
        bufferLength = 0;
        bufferIndex = 0;
        flag = 0;
    }

    public Scan(String inputStream) {
        this(new ByteArrayInputStream(inputStream.getBytes()));
    }

    public boolean hasNextLine() {
        int startIndex = bufferIndex;
        while (flag != -1) {
            char symbol = block(bufferIndex);
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

    public String nextLine() {
        //считывает всю введенную строку
        StringBuilder lineBuffer = new StringBuilder();
        while (true) {
            char symbol = block(bufferIndex);
            if (flag==-1) {
                break;
            }
            if (symbol == '\n' || symbol == '\r') {
                break;
            }
            lineBuffer.append(symbol);
        }
        return lineBuffer.toString();
    }

    public boolean hasNextInt() {
        //проверяет, что следующая последовательность символов является int-ом
        int startIndex = bufferIndex;
        StringBuilder lineBuffer = new StringBuilder();
        while (flag != -1) {
            char symbol = block(bufferIndex);
            if (Character.isDigit(symbol) || symbol == 'a' || symbol == 'b' || symbol == 'c' || symbol == 'd' || symbol == 'e' || symbol == 'f' || symbol == 'g' || symbol == 'h' || symbol == 'i' || symbol == 'j' || symbol == '+' || symbol == '-') {
                lineBuffer.append(symbol);
            } else {
                if (lineBuffer.length()==0) {
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

    public String nextInt() {
        //считывает введенное число int
        StringBuilder lineBuffer = new StringBuilder();
        while (true) {
            if (flag==-1 && bufferLength==bufferIndex) {
                break;
            }
            char symbol = block(bufferIndex);
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



    private char block(int Index) {
        if (Index >= bufferLength) {
            if (bufferIndex >= buffer.length) {
                buffer = Arrays.copyOf(buffer, buffer.length * 2);
            }
            try {
                flag = reader.read(buffer, Index, buffer.length - Index);
                bufferLength += flag;
                if (flag == -1) {
                    reader.close();
                    bufferLength++;
                }
            } catch (IOException e) {
                System.out.println("An error occurred while scanning: " + e.getMessage());
            }
        }
        return buffer[this.bufferIndex++];
    }

}


