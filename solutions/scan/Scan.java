package scan;

import java.io.*;

public class Scan {
    BufferedReader reader;
    char[] buffer;
    int bufferLength;
    int bufferIndex;

    public Scan(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        buffer = new char[3]; //TODO: использую 3 для проверки потом 1024
        bufferLength = 0;
        bufferIndex = 0;

    }

    public StringBuilder next() throws IOException {
        //считывает до пробела
        StringBuilder temp = new StringBuilder();
        char simbol = block(bufferIndex);
        while (simbol!=-1) {
            if (!Character.isWhitespace(simbol)) {
                temp.append(simbol);
            } else {
                if (!temp.isEmpty()) {
                    break;
                }
            }
            bufferIndex++;
            simbol = block(bufferIndex);
        }
        return temp;
    }

    public boolean hasNextLine() throws IOException {
        char simbol = block(bufferIndex);
        while (simbol!=-1) {
            if (simbol == '\n' || simbol == '\r') {
                bufferIndex++;
                return true;
            }
            bufferIndex++;
            simbol = block(bufferIndex);
        }
        return false;
    }

    public String nextLine() throws IOException {
        //считывает всю введенную строку
        StringBuilder lineBuffer = new StringBuilder();
        char simbol = block(bufferIndex);
        while (simbol!=-1) {
            if (simbol == '\n') {
                bufferIndex++;
                break;
            }
            lineBuffer.append(simbol);
            bufferIndex++;
            simbol = block(bufferIndex);
        }
        return lineBuffer.toString();
    }


//    public String nextInt() {
//        //считывает введенное число int
//    }
//
//    public boolean hasNextInt() {
//        //проверяет, что следующая последовательность символов является int-ом
//    }
//
    private char block(int bufferIndex) throws IOException {
        if (bufferIndex >= bufferLength) {
            bufferLength = reader.read(buffer, 0, buffer.length);
            this.bufferIndex = 0;
        }
        return buffer[this.bufferIndex];
    }

}
