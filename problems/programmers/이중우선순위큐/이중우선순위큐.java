// NOTE : 이중우선순위큐
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42628

import java.util.*;
class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;
        for(String str : operations){
            String[] tmp = str.split(" ");
            if(tmp[0].equals("I")){
                list.add(Integer.parseInt(tmp[1]));
            }else if(tmp[0].equals("D")){
                if(list.size()>0){
                    list.sort(null);
                    if(tmp[1].equals("1")){
                        list.remove(list.size()-1);
                    }else{
                        list.remove(0);
                    }
                }
            }
        }
        if(list.size()>0){
            list.sort(null);
            answer[0]=list.get(list.size()-1);
            answer[1]=list.get(0);
        }else{
            answer[0]=0;
            answer[1]=0;
        }
        return answer;
    }
}