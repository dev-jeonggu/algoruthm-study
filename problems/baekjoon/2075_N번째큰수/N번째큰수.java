// NOTE : 2075_N번째큰수
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/2075

import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int num = scanner.nextInt();
                pq.offer(num);
                
                if (pq.size() > n) {
                    pq.poll();
                }
            }
        }

        System.out.println(pq.peek()); 

        scanner.close();
    }
}
