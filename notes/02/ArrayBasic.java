// Unlike classes, Array do not have methods.
public class ArrayBasic {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = a;
        a[2] = 1;
        System.out.println(b[2]);
    }
}
