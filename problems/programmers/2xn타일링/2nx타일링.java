// NOTE : 2xn타일링
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12900

class Solution {
    public int solution(int n) {
        int answer = 0;
        int[] arr = new int[n+1];
        arr[1] = 1;
        arr[2] = 2;
        
        for(int i=3; i<=n; i++){
            int sum = arr[i-1] + arr[i-2];
            arr[i] = sum%1000000007;
        }
        
        answer = arr[n];
        
        return answer;
    }
}