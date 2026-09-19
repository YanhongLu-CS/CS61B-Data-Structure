public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static int startlength = 8;
    private static int min_length = 8;
    public ArrayDeque() {
        items = (T[]) new Object[startlength];
        size = 0;
        nextFirst = 0;
        nextLast = 1;

    }

    public ArrayDeque(T item) {
        this();
        addLast(item);
    }

    public ArrayDeque(ArrayDeque<T> other) {
        this();
        for (int i = 0; i < other.size(); i++) {
            addLast(other.get(i));
        }
    }

    private int plus_one(int index) {
        return (index + 1) % items.length;
    }

    private int minus_one(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = items[(nextFirst + 1 + i) % items.length];
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = plus_one(nextLast);
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = minus_one(nextFirst);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    private double getUtilizationRate() {
        return (double) size / (double) items.length;
    }

    public T removeFirst() {
        if (isEmpty()) return null;
        T temp = items[plus_one(nextFirst)];
        items[plus_one(nextFirst)] = null;
        nextFirst = plus_one(nextFirst);
        size -= 1;
        if (getUtilizationRate() < 0.25 && size > min_length) {
            resize (items.length / 2);
        }
        return temp;
    }

    public T removeLast() {
        if (isEmpty()) return null;
        T temp = items[minus_one(nextLast)];
        items[minus_one(nextLast)] = null;
        nextLast = minus_one(nextLast);
        size -= 1;
        if (getUtilizationRate() < 0.25 && size > min_length) {
            resize (items.length / 2);
        }
        return temp;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(nextFirst + 1 + i) % items.length] + " ");
        }
    }

    public T get(int index) {
        if (index >= size || index < 0) return null;
        return items[(nextFirst + 1 + index) % items.length];
    }
}
