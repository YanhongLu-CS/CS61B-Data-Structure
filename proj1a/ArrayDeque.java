public class ArrayDeque<T> {
    private T[] items;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;
    }
    public ArrayDeque(ArrayDeque<T> other) {
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.size);
        size = other.size;
    }
    public ArrayDeque(T item) {
        this();
        addFirst(item);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[size] = item;
        size += 1;
    }
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return (size == 0);
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        System.arraycopy(items, 0, items, 1, size);
        items[0] = item;
        size += 1;
    }
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = items[0];
        System.arraycopy(items, 1, items, 0, size - 1);
        size -= 1;
        return returnItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = items[size - 1];
        items[size - 1] = null;
        size -= 1;
        return returnItem;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[i] + " ");
        }
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[index];
    }


}
