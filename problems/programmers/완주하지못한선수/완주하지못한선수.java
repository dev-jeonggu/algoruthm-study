// NOTE : 완주하지못한선수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42576

import java.util.*;

/*
    HashMap의 접근 시간
    - put() → 평균 O(1)
    - get() → 평균 O(1)
    - remove() → 평균 O(1)
    → 전체: O(n) / 해시 기반)
*/
class Solution {
    public String solution1(String[] participant, String[] completion) {
        String answer = "";
        Map<String,Integer> map = new HashMap<>();
        
        for(int i = 0; i < participant.length; i++){
            map.put(participant[i], map.getOrDefault(participant[i], 0) + 1);
        }
        for(int i = 0; i < completion.length; i++){
            map.put(completion[i], map.get(completion[i]) - 1);
            if(map.get(completion[i]) == 0){
                map.remove(completion[i]);
            }
        }
        answer = map.keySet().toString().replaceAll("\\[|\\]","");
        return answer;
    }

    /* 
        시간 초과
        - contains(O(n)) + remove(O(n)) = O(n) + O(n) = O(n)
        - 하지만 remove 내부에서도 탐색하므로 실제로는 더 나쁨.
        - ArrayList.contains() → 내부적으로 처음부터 끝까지 선형 탐색
        → 시간복잡도 : O(n²) / (선형 탐색 + 삭제)
    */ 
    public String solution2(String[] participant, String[] completion) {
        String answer = "";
        List<String> list = new ArrayList<>(Arrays.asList(participant));
        
        for(int i=0; i<completion.length; i++){
            if(list.contains(completion[i])){
                list.remove(completion[i]);
            }
        }
        answer = String.join("", list);;
        return answer;
    }
}