// NOTE : 큰수만들기
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42883?language=java

import java.util.*;

class Solution {
    public String solution(String number, int k) {
        int size = number.length();
        int[] arr = new int[size];
        
        Deque<Integer> queue = new LinkedList<>();
        StringBuilder answer = new StringBuilder();
        
        for(int i=0; i<size; i++){
            arr[i] = Character.getNumericValue(number.charAt(i));
        }
                
        for (int i = 0; i < size; i++) {
            while (!queue.isEmpty() && k > 0 && queue.peekLast() < arr[i]) {
                queue.pollLast();
                k--;
            }
            queue.add(arr[i]);
        }
        
        while(k-- > 0){
            queue.pollLast();
        }
        for(Integer n : queue){
            answer.append(n);
        }
        
        return answer.toString();
    }
}