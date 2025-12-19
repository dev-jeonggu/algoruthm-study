// NOTE : 더맵게
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42626

import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        Arrays.stream(scoville).forEach(pq::offer);

        while (pq.size() >= 2 && pq.peek() < K) {
            int n = pq.poll();
            int m = pq.poll();
            int tmp = n + m * 2;
            pq.offer(tmp);
            answer++;
        }

        return pq.peek() >= K ? answer : -1;
    }
}