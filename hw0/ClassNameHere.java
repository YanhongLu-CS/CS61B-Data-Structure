public class ClassNameHere {
    public static int max(int[] m) {
        int M = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i] > M) {
                M = m[i];
            }
        }
        return M;
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(max(numbers));
    }
}
