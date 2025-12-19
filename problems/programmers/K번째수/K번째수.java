// NOTE : K번째수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42748

import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int n = commands.length;
        int[] answer = new int[n];
        for(int i=0; i<n; i++){
            int[] tmp = commands[i];
            int[] arr = Arrays.copyOfRange(array, tmp[0]-1, tmp[1]);
            Arrays.sort(arr);
            answer[i] = arr[tmp[2]-1];
        }

        return answer;
    }
}