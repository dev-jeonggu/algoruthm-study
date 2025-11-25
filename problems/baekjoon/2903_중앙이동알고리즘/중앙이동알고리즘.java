
// NOTE : 2903_중앙이동알고리즘
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/2903

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        int side = (int)Math.pow(2, N) + 1;
        int result = side * side;

        System.out.println(result);
    }
}
