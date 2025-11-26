// NOTE : 10250
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/10250

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        while (T-- > 0) {
            int H = sc.nextInt();
            int W = sc.nextInt();
            int N = sc.nextInt();

            int floor = N % H;
            int room = N / H + 1;

            if (floor == 0) {
                floor = H;
                room = N / H;
            }

            int result = floor * 100 + room;
            System.out.println(result);
        }
    }
}
