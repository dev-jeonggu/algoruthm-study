// NOTE : 짝지어제거하기
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12973

import java.util.*;

class Solution
{
    public int solution(String s)
    {
        int answer = 0;
        Deque<Character> queue = new LinkedList<>();
        int index = 0;
        
        while(true){
            char ch = s.charAt(index);
            if(!queue.isEmpty() && queue.peekLast() == ch){
                 queue.pollLast();
            }else{
                queue.add(ch);
            }

            index++;

            if(index > s.length() -1){
                break;
            }
        }

        if(queue.size() == 0){
            answer = 1;
        }
        return answer;
    }
}