import java.util.Scanner;

public class DrawTriangle2 {
    public static void drawTriangle(int N) {
        int row = 0;
        while (row < N) {
            int col = 0;
            while (col <= row) {
                System.out.print('*');
                col = col + 1;
            }
            System.out.println();
            row = row + 1;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        drawTriangle(n);
    }
}
