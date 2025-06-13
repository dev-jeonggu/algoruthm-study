// NOTE : 기능개발
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42586?language=java

import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int[] answer = {};
        int size = speeds.length;
        Queue<Integer> queue = new LinkedList<>();
        for(int i=0; i<size; i++){
            int tmp = (int)Math.ceil((100.0 - progresses[i]) / speeds[i]);
            queue.add(tmp);
        }
        
        int max = queue.poll();
        int cnt = 1;
        
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current <= max) {
                cnt++;
            } else {
                list.add(cnt);
                cnt = 1;
                max = current;
            }
        }
        list.add(cnt);
        answer = list.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }
}