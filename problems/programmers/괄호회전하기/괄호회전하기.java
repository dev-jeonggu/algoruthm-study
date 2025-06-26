// NOTE : 괄호회전하기
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/76502?language=java

import java.util.*;

class Solution {
    public int solution(String s) {
        int answer = 0;
        
        String[] arr = s.split("");
        List<String> list = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            list.add(arr[i]);
        }
        
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < s.length(); i++){
            stack.push(list.get(0));
            for(int j = 1; j < s.length(); j++){
                if(!stack.isEmpty() && ((stack.peek().equals("(") && list.get(j).equals(")"))
                                    || (stack.peek().equals("[") && list.get(j).equals("]"))
                                    || (stack.peek().equals("{") && list.get(j).equals("}")))){
                    stack.pop();
                } else{
                    stack.push(list.get(j));
                }
            }
            if(stack.size() == 0) answer++;
            list.add(0, list.get(s.length()-1));
            list.remove(s.length());
            stack.clear();
        }
        return answer;
    }
}