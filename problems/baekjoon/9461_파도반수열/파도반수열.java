// NOTE : 9461
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/9461

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        long[] p = new long[101];
        p[1] = p[2] = p[3] = 1;
        for (int i = 4; i <= 100; i++) {
            p[i] = p[i - 2] + p[i - 3];
        }

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < T; t++) {
            int N = sc.nextInt();
            sb.append(p[N]).append('\n');
        }
        System.out.print(sb.toString());
    }
}
