package scan;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class Scan implements AutoCloseable {
    private final BufferedReader reader;
    private char[] buffer;
    private int usefulBufferLength;
    private int bufferIndex;
    private boolean endOfFile;
    private final int defaultSize;
    private final Pattern INT_PATTERN;

    public Scan(InputStream inputStream) { // Хорошо
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        defaultSize = 64;
        buffer = new char[defaultSize];
        usefulBufferLength = bufferIndex = 0;
        endOfFile = false;
        INT_PATTERN = Pattern.compile("^((-?[1-9]\\d{0,9})|0)$");
    }

    public Scan(File inputStream) throws FileNotFoundException { this(new FileInputStream(inputStream)); }

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
            if (nextChar() != '\n') {
                bufferIndex--;
            }
        }
        if (!lineBuffer.isEmpty()) {
            return lineBuffer.toString();
        } else {
            throw new NoSuchElementException("No line found");
        }
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
            if (Character.isWhitespace(symbol)) {
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
            throw new InputMismatchException("Invalid input");
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
        return bufferIndex < usefulBufferLength;
    }

    private void refillBuffer() throws IOException {
        int usefulCharacters = usefulBufferLength - bufferIndex;
        if (usefulCharacters > 0) {
            if (usefulCharacters > defaultSize) {
                char[] temp = new char[usefulCharacters];
                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
                buffer = temp;
                usefulBufferLength = usefulCharacters;
                bufferIndex = 0;
            } else if (usefulBufferLength > defaultSize) {
                char[] temp = new char[defaultSize];
                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
                buffer = temp;
                usefulBufferLength = usefulCharacters;
                bufferIndex = 0;
            }
        } else { // Эту ветку можно заифать в начале и смерджить с условием ниже *
            buffer = new char[defaultSize];
            usefulBufferLength = 0;
            bufferIndex = 0;
            readerRead(usefulBufferLength);
        }
    }

    private void increaseBuffer() throws IOException {
        if (bufferIndex >= buffer.length) {
            buffer = Arrays.copyOf(buffer, buffer.length * 2);
        }
        // COPY-PASTE 16 lines above *
        readerRead(bufferIndex);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    private void readerRead(int start) throws IOException {
        int count = reader.read(buffer, start, buffer.length - usefulBufferLength);
        if (count != -1) {
            usefulBufferLength += count;
        } else {
            endOfFile = true;
        }
    }

    private boolean isValidInt(String pat) {
        long num;
        if (INT_PATTERN.matcher(pat).matches()) {
            num = Long.parseLong(pat);
            return (Integer.MAX_VALUE >= num) && (Integer.MIN_VALUE <= num);
        }
        return false;
    }

}


