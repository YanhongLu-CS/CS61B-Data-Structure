public class SLList {
    public static class IntNode {
        //If the nested class has no need to use any of the instance methods
        //  or variables of SLList, you may declare the nested class static
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;
    private int size;

    public SLList() {
        first = null;
        size = 0;
    }

    public SLList(int x){
        first = new IntNode(x, null);
        size = 1;
    }
    public void addFirst(int x) {
        first = new IntNode(x, first);
        size += 1;
    }
    public int getNode() {
        return first.item;
    }

    public void addLast(int x) {
        size += 1;
        if (first == null) {
            first = new IntNode(x, null);
            return;
        }

        IntNode p = first;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    // private static int size(IntNode p) {
    //     if (p == null) {
    //         return 0;
    //     } else {
    //         return 1 + size(p.next);
    //     }
    // }
    // public int size() {
    //     return size(first);
    // }
    // /*
    // Consider the size method we wrote above. Suppose size takes 2 seconds on a list of size 1,000. 
    // We expect that on a list of size 1,000,000, the size method will take 2,000 seconds, 
    // since the computer has to step through 1,000 times as many items in the list to reach the end. 
    // Having a size method that is very slow for large lists is unacceptable, since we can do better.
    // */

    public int size() {
        return size;
    }


}