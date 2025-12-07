// NOTE : 1021_회전하는큐
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1021

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();  // 큐 크기
        int M = sc.nextInt();  // 뽑아야 할 숫자 개수

        LinkedList<Integer> deque = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            deque.add(i);
        }

        int totalMoves = 0;

        for (int i = 0; i < M; i++) {
            int target = sc.nextInt();

            int idx = deque.indexOf(target);     // target이 현재 위치한 인덱스
            int leftMoves = idx;                 // 왼쪽 회전 횟수
            int rightMoves = deque.size() - idx; // 오른쪽 회전 횟수

            if (leftMoves <= rightMoves) {
                // 왼쪽으로 돌리는 게 빠름
                for (int j = 0; j < leftMoves; j++) {
                    deque.addLast(deque.removeFirst());
                    totalMoves++;
                }
            } else {
                // 오른쪽으로 돌리는 게 빠름
                for (int j = 0; j < rightMoves; j++) {
                    deque.addFirst(deque.removeLast());
                    totalMoves++;
                }
            }

            // target 제거 (연산 1)
            deque.removeFirst();
        }

        System.out.println(totalMoves);
    }
}
