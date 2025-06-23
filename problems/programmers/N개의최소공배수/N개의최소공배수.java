// NOTE : N개의최소공배수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12953?language=java

class Solution {   
    public int solution(int[] arr) {
        int answer = arr[0];
        int max = 0;
        int min = 0;
        int temp = 0;
        
        for(int i = 1; i < arr.length; i++){
            max = Math.max(answer, arr[i]);
            min = Math.min(answer, arr[i]);
            
            while(max % min != 0){
                temp = max % min;
                max = min;
                min = temp;
            }
            
            answer = answer * arr[i] / min;
        }
        
        return answer;
    }
}