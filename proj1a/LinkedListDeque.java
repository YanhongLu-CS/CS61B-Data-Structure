// deque 的意思是双端队列
public class LinkedListDeque<T> {
    private static class Node<T> {
        public Node<T> prev;
        public Node<T> next;
        public T item;
        public Node(T i, Node<T> p, Node<T> n) {
            item = i;
            prev = p;
            next = n;
        }
    }
    private int size;
    private Node<T> sentinel;

    public LinkedListDeque() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    // public LinkedListDeque(LinkedListDeque<T> other) {
    //     this();
    //     Node<T> ptr = other.sentinel.next;
    //     while (ptr != other.sentinel) {
    //         addLast(ptr.item);
    //         ptr = ptr.next;
    //     }
    // }

    // public LinkedListDeque(T item) {
    //     this();
    //     addFirst(item);
    // }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (sentinel == sentinel.next);
    }

    public void addFirst(T item) {
        size += 1;
        sentinel.next = new Node<>(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(T item) {
        size += 1;
        sentinel.prev = new Node<>(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    public T removeFirst() {
        if (! isEmpty()) {
            size -= 1;
            T temp = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return temp;
        }
        return null;
    }

    public T removeLast() {
        if(! isEmpty()) {
            size -= 1;
            T temp = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return temp;
        }
        return null;
    }

    public void printDeque() {
        Node<T> ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }

    public T get(int index) {
        if (index < size && index >= 0) {
            Node<T> ptr = sentinel.next;
            while (index > 0) {
                ptr = ptr.next;
                index -= 1;
            }
            return ptr.item;
        }
        return null;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return recursiveHelper(sentinel.next, index);
    }
    private T recursiveHelper(Node<T> node, int index) {
        if (index == 0) {
            return node.item;
        } else {
            return recursiveHelper(node.next, index - 1);
        }
    }
}
