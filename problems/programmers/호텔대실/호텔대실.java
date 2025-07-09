// NOTE : 호텔대실
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/155651

import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        List<int[]> times = new ArrayList<>();
        
        for (String[] time : book_time) {
            int start = toMinutes(time[0]);
            int end = toMinutes(time[1]) + 10;
            times.add(new int[]{start, end});
        }
        
        times.sort((a, b) -> Integer.compare(a[0], b[0]));
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for (int[] t : times) {
            if (!pq.isEmpty() && pq.peek() <= t[0]) {
                pq.poll();
            }
            pq.offer(t[1]);
        }
        
        return pq.size();
    }
    
    private int toMinutes(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}
