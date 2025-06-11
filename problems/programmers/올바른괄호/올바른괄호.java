// NOTE : 올바른괄호
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12909?language=java

import java.util.LinkedList;
import java.util.Queue;
class Solution {
    boolean solution(String s) {
        boolean answer = true;
        Queue<Character> queue = new LinkedList<Character>();
        for(int i=0; i<s.length(); i++){
            char in = s.charAt(i);
            if(!queue.isEmpty()) {
                char out = queue.peek();
                if(in == ')' && out == '('){
                    queue.poll();
                }else{
                    queue.add(in);
                }
            } else{
                queue.add(in);
            }
        }
        if(!queue.isEmpty()){
            answer = false;
        }
        return answer;
    }
}