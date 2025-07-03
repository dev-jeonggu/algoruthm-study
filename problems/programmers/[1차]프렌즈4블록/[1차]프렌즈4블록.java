// NOTE : [1차]프렌즈4블록
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/17679

import java.util.*;

class Solution {
    public int solution(int m, int n, String[] board) {
        int answer = 0;
        String[][] str = new String[m][n];

        for(int i = 0; i < m; i++) {
            str[i] = board[i].split("");
        }

        while(true){        
            boolean[][] marked = new boolean[m][n];
            boolean found = false;

            for(int i = 0; i < m - 1; i++){
                for(int j = 0; j < n - 1; j++){
                    String current = str[i][j];
                    if(!current.equals("") && current.equals(str[i+1][j]) && current.equals(str[i][j+1]) && current.equals(str[i+1][j+1])) {
                        marked[i][j] = marked[i+1][j] = marked[i][j+1] = marked[i+1][j+1] = true;
                        found = true;
                    }
                }
            }

            if(!found) break;

            for(int i = 0; i < m; i++){
                for(int j = 0; j < n; j++){
                    if(marked[i][j]) {
                        str[i][j] = "";
                        answer++;
                    }
                }
            }

            for(int j = 0; j < n; j++){
                List<String> stack = new ArrayList<>();
                for(int i = m - 1; i >= 0; i--){
                    if(!str[i][j].equals("")){
                        stack.add(str[i][j]);
                    }
                }
                for(int i = m - 1, idx = 0; i >= 0; i--, idx++){
                    if(idx < stack.size()){
                        str[i][j] = stack.get(idx);
                    } else {
                        str[i][j] = "";
                    }
                }
            }
        }

        return answer;
    }
}