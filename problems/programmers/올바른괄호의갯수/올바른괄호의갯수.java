// NOTE : 올바른괄호의갯수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12929

class Solution {
    public int solution(int n) {
        int answer = 0;
        int[] arr = new int[n+1];
        arr[0] = 1;
        arr[1] = 1;
        if(n == 2){
            answer =2;
        }else{
            for(int i=2; i<=n; i++){
                for(int j=0; j<i; j++){
                    arr[i] += arr[j] * arr[i-1-j];
                }
            }
            answer = arr[n];
        }
        return answer;
    }
}