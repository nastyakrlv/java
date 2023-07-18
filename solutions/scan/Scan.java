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
    private int flag;
    private int usefulBufferLength;
    private int bufferIndex;
    private boolean endOfFile;
    private final int  size;

//    private final Pattern INT_PATTERN;

    public Scan(InputStream inputStream) { // Хорошо
        reader = new BufferedReader(new InputStreamReader(inputStream));
        size = 32;
        buffer = new char[size];
        usefulBufferLength = bufferIndex = flag = 0;
        endOfFile = false;
//        INT_PATTERN = Pattern.compile("^((-?[1-9]\\d{0,9})|0)$");//TODO: АКИМ проверить что быстрее isValidInt или try catch
    }

    public Scan(String inputStream) { // Хорошо
        this(new ByteArrayInputStream(inputStream.getBytes()));
    }

    public boolean hasNextLine() throws IOException {
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
                if (!lineBuffer.isEmpty()) {
//                    hasNumber = isValidInt(lineBuffer.toString()); //TODO: АКИМ проверить что быстрее isValidInt или try catch
//                    break;
                    try {
                        Integer.parseInt(lineBuffer.toString());
                        hasNumber = true;
                    } catch (NumberFormatException e) {
                        hasNumber = false;
                    }
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
        while (hasNextChar()) { // TODO: for loop *пробовала уже, но стало хуже - сначала проверяем потом считываем
            char symbol = nextChar();
            if (Character.isWhitespace(symbol) ) {
                if (!lineBuffer.isEmpty()) { // TODO: !empty() *
                    break;
                }
            } else {
                lineBuffer.append(symbol);
            }
        }
        try { // good//TODO: АКИМ проверить что быстрее isValidInt или try catch
            refillBuffer();
            return Integer.parseInt(lineBuffer.toString());
        } catch (NumberFormatException e) {
            throw new InputMismatchException(e.getLocalizedMessage());
        }
    }

    private char nextChar() {
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
        boolean ok = false;
        int usefulCharacters = usefulBufferLength - bufferIndex;
        if (usefulCharacters > 0) {
            if (usefulCharacters > size) {
                ok = true;
                char[] temp = new char[usefulCharacters];
                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
                buffer = temp;
                usefulBufferLength = usefulCharacters;
                bufferIndex = 0;
            } else if (usefulBufferLength > size)  {
                char[] temp = new char[size];
                System.arraycopy(buffer, bufferIndex, temp, 0, usefulCharacters);
                buffer = temp;
                usefulBufferLength = usefulCharacters;
                bufferIndex = 0;
            }
        } else {
            ok = true;
            buffer = new char[size];
            usefulBufferLength = 0;
            bufferIndex = 0;
        }
        if (ok) {
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
            }
        int count = reader.read(buffer, bufferIndex, buffer.length - bufferIndex);
        if (count != -1) {
            usefulBufferLength += count;
        } else {
            endOfFile = true;
        }
    }

    public void close() throws IOException { reader.close(); }


















//    // TODO: обработай ошибку захода за конец потока (если тебя вызовут с flag == -1)
//    protected char nextChar() throws IOException {
//        // TODO: весь поток всегда хранится в памяти, переделать
//        /*
//           Увеличивай буффер только в методах вида "hasNext*",
//           в противном случае используй дефолтный размер.
//           P.S.: ужимай буфер, если тот стал большим и ты добежала его до конца.
//
//           Подсказка: сделай 2 метода
//
//           1) метод, который проверяет, есть ли впереди последовательность символов, подходящих под
//           заданный тобой критерий, при этом не двигая никак buffer_iterator, не забывая символы сзади,
//           но, при надобности, бесконтрольно расширяя буффер, кладя вперёд всё новые и новые блоки символов.
//
//           2) метод, который отдаёт тебе следующий символ, забывая предыдущие и при переходе за край буффера
//           не просто читает символы из потока кладя их наверх и заполняя себя полнсотью как <метод 1 name>,
//           а перечитывает символы в себя же, поверх старых. При этом, при заходе за край useful символов, пусть он
//           перед заполнением проверяет, не стал ли он слишком большим после вызовов <метод 1 name> и сжмается,
//           если увидит это.
//         */
//        if (bufferIndex >= usefulBufferLength) {
//            if (bufferIndex >= buffer.length) {
//                buffer = Arrays.copyOf(buffer, buffer.length * 2);
//            }
//            flag = reader.read(buffer, bufferIndex, buffer.length - bufferIndex);
//            usefulBufferLength += flag;
//            if (flag == -1) {
//                reader.close();
//                usefulBufferLength++;
//            }
//        }
//        return buffer[this.bufferIndex++];
//    }

//    private boolean isValidInt(String pat) {//TODO: АКИМ проверить что быстрее isValidInt или try catch
//        long num;
//        if (INT_PATTERN.matcher(pat).matches()) {
//            num =  Long.parseLong(pat);
//            return (Integer.MAX_VALUE >= num) && (Integer.MIN_VALUE <= num);
//        }
//        return false;
//    }

}


