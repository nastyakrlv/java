package wspp;

import java.util.*;

public class IntList {
    private int[] array;
    private int length;

    public IntList() {
        array = new int[16];
        length = 0;
    }

    public void add(int value) {
        if (length == array.length) { array = Arrays.copyOf(array, array.length*2); }
        array[length++] = value;
    }

    public int get(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    public int getLength() { return length; }

    public void remove(int index) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = index; i < length - 1; i++) {
            array[i] = array[i + 1];
        }
        length--;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(array[i]);
            if (i != length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public void set(int index, int element) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = element;
    }
}
