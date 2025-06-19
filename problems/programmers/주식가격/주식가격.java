// NOTE : 주식가격
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42584?language=java

import java.util.*;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Queue<Integer> Q = new LinkedList<>();
        for(int x : prices) Q.offer(x);
        int index = 0;
        while(!Q.isEmpty()){
            int target = Q.poll();
            for(int x : Q){
                answer[index]++;
                if(target > x) break;
            }
            index++;
        }
        return answer;
    }
}