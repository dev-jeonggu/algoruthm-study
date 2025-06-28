// NOTE : 숫자의표현
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12924


class Solution {
    public int solution(int n) {
        int answer = 0;
        int sum = 0;
        
        for(int i = 1; i <= n; i++){
            for(int j = i; j <= n; j++){
                sum += j;
                if(sum == n){ answer++; break; }
                else if(sum > n){ break; }
            }
            sum = 0;
        }
        return answer;
    }
}