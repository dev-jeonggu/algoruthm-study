// NOTE : 16500_문자열판별
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/16500

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.nextLine();
        int N = sc.nextInt();
        sc.nextLine();

        String[] words = new String[N];
        for (int i = 0; i < N; i++) {
            words[i] = sc.nextLine();
        }

        boolean[] dp = new boolean[S.length() + 1];
        dp[0] = true;

        for (int i = 0; i < S.length(); i++) {
            if (!dp[i]) continue;

            for (String w : words) {
                int len = w.length();
                if (i + len <= S.length()
                    && S.startsWith(w, i)) {
                    dp[i + len] = true;
                }
            }
        }

        System.out.println(dp[S.length()] ? "1" : "0");
    }
}

