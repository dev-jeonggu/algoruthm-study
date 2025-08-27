// NOTE : 12865
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/12865

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] W = new int[N];
        int[] V = new int[N];
        for (int i = 0; i < N; i++) {
            W[i] = sc.nextInt();
            V[i] = sc.nextInt();
        }

        int[] dp = new int[K + 1];

        for (int i = 0; i < N; i++) {
            int w = W[i], v = V[i];
            for (int cap = K; cap >= w; cap--) {
                dp[cap] = Math.max(dp[cap], dp[cap - w] + v);
            }
        }

        System.out.println(dp[K]);
        sc.close();
    }
}
