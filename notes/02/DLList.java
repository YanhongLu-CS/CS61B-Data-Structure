public class DLList {
    public static class Node {
        public Node prev;
        public Node next;
        public Object item;
        public Node(int i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }
    private int size;
    private Node sentinel;

    public DLList() {
        sentinel = new Node(42, null, null);
        size = 0;
    }

    public DLList(int i) {
        Node sentinel = new Node(42, null, null);
        sentinel.next = new Node(i, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    public int size() {
        return size;
    }

    public Node getFirst(){
        return sentinel.next;
    }

    public Node getLast() {
        return sentinel.prev;
    }

    public void addFirst(int i) {
        size += 1;
        sentinel.next = new Node(i, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(int i) {
        size += 1;
        sentinel.prev = new Node(i, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    
}
