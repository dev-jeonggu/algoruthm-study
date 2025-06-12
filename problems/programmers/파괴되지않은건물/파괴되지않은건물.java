// NOTE : 파괴되지않은건물
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/92344?language=java

import java.util.*;

class Solution {
    public int solution(int[][] board, int[][] skill) {
        int n = board.length;
        int m = board[0].length;
        int[][] sum = new int[n + 1][m + 1];

        for (int[] s : skill) {
            int type = s[0];
            int r1 = s[1], c1 = s[2], r2 = s[3], c2 = s[4];
            int degree = type == 1 ? -s[5] : s[5];

            sum[r1][c1] += degree;
            sum[r1][c2 + 1] -= degree;
            sum[r2 + 1][c1] -= degree;
            sum[r2 + 1][c2 + 1] += degree;
        }

        for (int i = 0; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                sum[i][j] += sum[i][j - 1];
            }
        }

        for (int j = 0; j <= m; j++) {
            for (int i = 1; i <= n; i++) {
                sum[i][j] += sum[i - 1][j];
            }
        }

        int answer = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] += sum[i][j];
                if (board[i][j] > 0) answer++;
            }
        }

        return answer;
    }
}
