package scan;

import javax.print.MultiDocPrintService;
import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class Scan {
    private final BufferedReader reader;
    private char[] buffer;
    private int flag; // unused variable
    private int usefulBufferLength;
    private int bufferIndex;
    private boolean endOfFile;
    private final int  size; // 1) OPT+CMD+L 2) defaultSize

    private final Pattern INT_PATTERN;

    public Scan(InputStream inputStream) { // Хорошо
        reader = new BufferedReader(new InputStreamReader(inputStream));
        size = 32;
        buffer = new char[size];
        usefulBufferLength = bufferIndex = flag = 0;
        endOfFile = false;
        INT_PATTERN = Pattern.compile("^((-?[1-9]\\d{0,9})|0)$");//TODO: АКИМ проверить что быстрее isValidInt или try catch
    }

    public Scan(String inputStream) { // Хорошо
        this(new ByteArrayInputStream(inputStream.getBytes()));
    }

    public boolean hasNextLine() throws IOException { // Хорошо
        return hasNextChar() && !endOfFile;
    }

    public String nextLine() throws IOException { // Хорошо
        StringBuilder lineBuffer = new StringBuilder();
        char symbol;
        for (symbol = nextChar();
             hasNextLine() && symbol != '\n' && symbol != '\r';
             symbol = nextChar()) {
            lineBuffer.append(symbol);
        }
        if (!endOfFile && symbol == '\r') {
            if (nextChar()!='\n') {
                bufferIndex--;
            }
        }
        return lineBuffer.toString();
    }

    public boolean hasNextInt() throws IOException {
        StringBuilder lineBuffer = new StringBuilder();
        int startIndex = bufferIndex;
        boolean hasNumber = false;
        while (true) {
            if (bufferIndex >= usefulBufferLength) {
                increaseBuffer();
            }
            char symbol = nextChar();
            if (Character.isWhitespace(symbol) || endOfFile) {
                if (!lineBuffer.isEmpty()) { // Можно упростить, избежать ифа и бреаков используя continue и return.
                    hasNumber = isValidInt(lineBuffer.toString());
                    break;
                } else if (endOfFile && lineBuffer.isEmpty()) {
                    break;
                }
            } else {
                lineBuffer.append(symbol);
            }
        }
        bufferIndex = startIndex;
        return hasNumber;
    }

    public int nextInt() throws IOException {
        StringBuilder lineBuffer = new StringBuilder();
        while (hasNextChar()) { // Похожая логика выше. Упростить.
            char symbol = nextChar();
            if (Character.isWhitespace(symbol) ) {
                if (!lineBuffer.isEmpty()) {
                    break;
                }
            } else {
                lineBuffer.append(symbol);
            }
        }
        refillBuffer();
        if (isValidInt(lineBuffer.toString())) {
            return Integer.parseInt(lineBuffer.toString());
        } else {
            throw new InputMismatchException("дописать");
        }
    }

    private char nextChar() { // Хорошо
        return buffer[bufferIndex++];
    }

    private boolean hasNextChar() throws IOException {
        if (bufferIndex >= usefulBufferLength) {
            if (endOfFile) {
                return false;
            }
            refillBuffer();
        }
        return bufferIndex < usefulBufferLength; // Судя по бренчам метода refillBuffer исход данного сравнения однозначен
    }

    private void refillBuffer() throws IOException { // Этот метод можно значительно упростить, и не только там, где я расставил комментарии
        boolean ok = false; // Эта переменная не нужна, читать комменты ниже
        int usefulCharacters = usefulBufferLength - bufferIndex;
        if (usefulCharacters > 0) {
            if (usefulCharacters > size) { // Этот бранч никогда не выполнится
                assert(false);
//                ok = true;
//                char[] temp = new char[usefulCharacters];
//                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
//                buffer = temp;
//                usefulBufferLength = usefulCharacters;
//                bufferIndex = 0;
            } else if (usefulBufferLength > size)  { // В чем семантический смысл не делать это всегда?
                char[] temp = new char[size];
                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
                buffer = temp;
                usefulBufferLength = usefulCharacters;
                bufferIndex = 0;
            }
        } else { // Эту ветку можно заифать в начале и смерджить с условием ниже
            ok = true;
            buffer = new char[size];
            usefulBufferLength = 0;
            bufferIndex = 0;
        }
        if (ok) { // Наверх
            int count = reader.read(buffer, usefulBufferLength, buffer.length - usefulBufferLength);
            if (count != -1) {
                usefulBufferLength += count;
            } else {
                endOfFile = true;
            }
        }


    }

    private void increaseBuffer() throws IOException {
        if (bufferIndex >= buffer.length) {
                buffer = Arrays.copyOf(buffer, buffer.length * 2);
            } // OPT+CMD+L
        // COPY-PASTE 16 lines above
        int count = reader.read(buffer, bufferIndex, buffer.length - bufferIndex); // Почему не defaultSize? Мы же договорились считывать блоками по defaultSize?
        if (count != -1) {
            usefulBufferLength += count;
        } else {
            endOfFile = true;
        }
    }

    public void close() throws IOException { reader.close(); } // Тогда бы уж реализовала Auto-Closable

    private boolean isValidInt(String pat) {
        long num;
        if (INT_PATTERN.matcher(pat).matches()) {
            num =  Long.parseLong(pat);
            return (Integer.MAX_VALUE >= num) && (Integer.MIN_VALUE <= num);
        }
        return false;
    }

}


