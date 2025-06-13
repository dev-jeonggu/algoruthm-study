// NOTE : 5430_AC
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/5430

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            String command = sc.nextLine();
            int m = Integer.parseInt(sc.nextLine());
            String input = sc.nextLine();

            // 배열 파싱
            input = input.substring(1, input.length() - 1);
            Deque<Integer> deque = new ArrayDeque<>();
            if (!input.isEmpty()) {
                String[] nums = input.split(",");
                for (String num : nums) {
                    deque.add(Integer.parseInt(num));
                }
            }

            boolean isReversed = false;
            boolean isError = false;

            for (char ch : command.toCharArray()) {
                if (ch == 'R') {
                    isReversed = !isReversed;
                } else {
                    if (deque.isEmpty()) {
                        sb.append("error\n");
                        isError = true;
                        break;
                    } else {
                        if (isReversed) {
                            deque.pollLast();
                        } else {
                            deque.pollFirst();
                        }
                    }
                }
            }

            if (!isError) {
                sb.append("[");
                while (!deque.isEmpty()) {
                    sb.append(isReversed ? deque.pollLast() : deque.pollFirst());
                    if (!deque.isEmpty()) {
                        sb.append(",");
                    }
                }
                sb.append("]\n");
            }
        }

        System.out.print(sb);
    }
}
