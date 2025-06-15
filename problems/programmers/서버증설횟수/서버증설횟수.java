// NOTE : 서버증설횟수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/389479

import java.util.*;

class Solution {
    public int solution(int[] players, int m, int k) {
        int answer = 0;
        
        Queue<Integer> queue = new ArrayDeque<>();
        
        for (int i=0; i<players.length; i++) {
            while (!queue.isEmpty() && i == queue.element()) {
                queue.poll();
            }
            int cur = players[i];
            if (cur / m <= queue.size()) continue;
            else {
                int n = cur / m - queue.size();
                for (int j=0; j<n; j++) {
                    queue.add(i+k);
                    answer++;
                }
            }
        }
        return answer;
    }
}