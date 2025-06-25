// NOTE : 피보나치수
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/12945

class Solution {
    public int solution(int n) {
        int answer = 0;
        long[] sums = new long[n+1];
        sums[0] = 0; sums[1] = 1;

        for(int i = 2; i <= n; i++){
                sums[i] = (sums[i-1] + sums[i-2]) % 1234567;
            }

        answer= (int) sums[sums.length-1];
        return answer;
    }
}