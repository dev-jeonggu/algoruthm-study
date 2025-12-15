// NOTE : 하노이의탑
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12946

import java.util.*;

class Solution {
    List<int[]> answer = new ArrayList<>();

    public int[][] solution(int n) {
        hanoi(n, 1, 2, 3);

        int[][] result = new int[answer.size()][2];
        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }
        return result;
    }

    void hanoi(int n, int from, int via, int to) {
        if (n == 1) {
            answer.add(new int[]{from, to});
            return;
        }

        hanoi(n - 1, from, to, via);   // 작은 것들 치우기
        answer.add(new int[]{from, to}); // 큰 것 이동
        hanoi(n - 1, via, from, to);   // 다시 얹기
    }
}
