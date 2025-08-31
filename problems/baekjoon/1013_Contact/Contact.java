// NOTE : 1013_잃어버린괄호
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1013

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int T = sc.nextInt();
        sc.nextLine(); // 개행 소비

        // 정규식: (100+1+ | 01)+
        Pattern p = Pattern.compile("^(100+1+|01)+$");

        for (int i = 0; i < T; i++) {
            String s = sc.nextLine().trim();
            if (p.matcher(s).matches()) {
                sb.append("YES\n");
            } else {
                sb.append("NO\n");
            }
        }

        System.out.print(sb);
        sc.close();
    }
}
