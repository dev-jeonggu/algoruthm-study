// NOTE : 모음사전
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/84512

import java.util.*;

class Solution {
    List<String> list = new ArrayList<>();
    String[] vowels = {"A", "E", "I", "O", "U"};

    public int solution1(String word) {
        dfs("", 0);
        return list.indexOf(word) + 1;
    }

    void dfs(String current, int depth) {
        if (depth > 5) return;
        if (!current.isEmpty()) list.add(current);

        for (String v : vowels) {
            dfs(current + v, depth + 1);
        }
    }

    public int solution2(String word) {
        char[] letters = {'A','E','I','O','U'};
        
        Map<Character, Integer> idx = new HashMap<>();
        for(int i=0; i<letters.length; i++){
            idx.put(letters[i], i);
        }
        
        int answer = 0;
        for(int i = 0; i < word.length(); i++){
            int remain = 5 - 1 - i;
            int w = (int)((Math.pow(5, remain + 1) - 1) / 4);
            
            int pos = idx.get(word.charAt(i));
            answer += pos * w + 1;
        }
        
        return answer;
    }
}

