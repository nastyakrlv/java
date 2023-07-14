package scan;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Scan {
    private final BufferedReader reader;
    private char[] buffer;
    private int flag;
    private int usefulBufferLength;
    private int bufferIndex;

    public Scan(InputStream inputStream) { // Хорошо
        reader = new BufferedReader(new InputStreamReader(inputStream));
        buffer = new char[256];
        usefulBufferLength = bufferIndex = flag = 0;
    }

    public Scan(String inputStream) { // Хорошо
        this(new ByteArrayInputStream(inputStream.getBytes()));
    }

    public boolean hasNextLine() throws IOException { // Хорошо
        int startIndex = bufferIndex;
        boolean isNewLine = false;
        if (flag==-1) {
            return false;
        }
        for (char symbol = nextChar();
             !isNewLine && flag!=-1;
             symbol=nextChar()) {
            if (symbol == '\r') {
                isNewLine = true;
                symbol = nextChar();
                if (symbol != '\n') {
                    bufferIndex--;
                    break;
                }
            } else if (symbol == '\n') {
                isNewLine = true;
            }
        }
        bufferIndex = startIndex;
        return isNewLine;
    }


    public String nextLine() throws IOException { // Хорошо
        StringBuilder lineBuffer = new StringBuilder();
        char symbol;
        for (symbol = nextChar();
             symbol != '\n' && symbol != '\r';
             symbol = nextChar()) {
            lineBuffer.append(symbol);
        }
        if (flag != -1 && symbol == '\r') {
            if (nextChar()!='\n') {
                bufferIndex--;
            }
        }
        return lineBuffer.toString();
    }

    public boolean hasNextInt() throws IOException {
        //TODO: MAXVALUE
        StringBuilder lineBuffer = new StringBuilder();
        int startIndex = bufferIndex;
        boolean hasNumber = false;
        if (flag==-1) {
            return false;
        }
        while (true) {
            char symbol = nextChar();
            if (Character.isWhitespace(symbol) || flag ==-1) {
                if (lineBuffer.length() > 0) {
                    try { /* TODO: исправить.
                            Написать отдельный метод для валидации int'a,
                            не надеяться на ислючения
                           */
                        Integer.parseInt(lineBuffer.toString());
                        hasNumber = true;
                    } catch (NumberFormatException e) {
                        hasNumber = false;
                    }
                    break;
                } else if (flag ==-1 && lineBuffer.length() == 0) {
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
        while (true) { // TODO: for loop
            if (flag==-1 && usefulBufferLength==bufferIndex) {
                break;
            }
            char symbol = nextChar();
            if (Character.isWhitespace(symbol) ) {
                if (lineBuffer.length() > 0) { // TODO: !empty()
                    break;
                }
            } else {
                lineBuffer.append(symbol);
            }
        }
        try { // good
            return Integer.parseInt(lineBuffer.toString());
        } catch (NumberFormatException e) {
            throw new InputMismatchException(e.getLocalizedMessage());
        }
    }

    // TODO: обработай ошибку захода за конец потока (если тебя вызовут с flag == -1)
    protected char nextChar() throws IOException {
        // TODO: весь поток всегда хранится в памяти, переделать
        /*
           Увеличивай буффер только в методах вида "hasNext*",
           в противном случае используй дефолтный размер.
           P.S.: ужимай буфер, если тот стал большим и ты добежала его до конца.

           Подсказка: сделай 2 метода

           1) метод, который проверяет, есть ли впереди последовательность символов, подходящих под
           заданный тобой критерий, при этом не двигая никак buffer_iterator, не забывая символы сзади,
           но, при надобности, бесконтрольно расширяя буффер, кладя вперёд всё новые и новые блоки символов.

           2) метод, который отдаёт тебе следующий символ, забывая предыдущие и при переходе за край буффера
           не просто читает символы из потока кладя их наверх и заполняя себя полнсотью как <метод 1 name>,
           а перечитывает символы в себя же, поверх старых. При этом, при заходе за край useful символов, пусть он
           перед заполнением проверяет, не стал ли он слишком большим после вызовов <метод 1 name> и сжмается,
           если увидит это.
         */
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

    @Deprecated
    private void shiftBuffer() { // Тебе не нужно никуда сдвигать буффер.
        int remainingChars = usefulBufferLength - bufferIndex;
        if (remainingChars > 0) {
            System.arraycopy(buffer, bufferIndex, buffer, 0, remainingChars);
        }
        usefulBufferLength = remainingChars;
        bufferIndex = 0;
    }

}


