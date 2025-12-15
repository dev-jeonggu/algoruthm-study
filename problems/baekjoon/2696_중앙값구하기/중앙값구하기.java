// NOTE : 2696_중앙값구하기
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/2696

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int T = sc.nextInt();

        for (int t = 0; t < T; t++) {
            int n = sc.nextInt();
            sb.append((n + 1) / 2).append("\n");

            List<Integer> list = new ArrayList<>();
            int cnt = 0;

            for (int i = 1; i <= n; i++) {
                list.add(sc.nextInt());

                if (i % 2 == 1) {
                    Collections.sort(list);
                    int mid = list.get(i / 2);
                    sb.append(mid).append(" ");
                    cnt++;

                    if (cnt % 10 == 0) sb.append("\n");
                }
            }
            sb.append("\n");
        }

        System.out.print(sb);
    }
}
