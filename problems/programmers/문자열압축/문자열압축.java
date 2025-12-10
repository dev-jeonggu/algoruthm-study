// NOTE : 문자열압축
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/60057
import java.util.*;

class Solution {
    public static int solution1(String s) {
        int answer = Integer.MAX_VALUE;
        // 문자 갯수
        for(int i=1; i<=s.length()/2+1;i++){
            StringBuilder sb = new StringBuilder();
            int cnt = 1;
            List<String> list = new ArrayList<>();
            for(int j=i; j<=s.length(); j+=i){
                // 주어진 길이보다 짧을때
                if(s.length()-j <i){
                    sb.append(s.substring(j));
                }
                String tmp = s.substring(j-i,j);
                if(list.size()>0 && list.get(list.size()-1).equals(tmp)){
                    cnt++;
                } else{
                    sb.append(tmp);
                    list.add(tmp);
                    if(cnt > 1){
                        sb.append(cnt);
                    }
                    cnt=1;
                }
                
            }
            if(cnt > 1){
                sb.append(cnt);
            }
            answer = Math.min(answer,sb.toString().length());
        }
        return answer;
    }
    public static int solution2(String s) {
        int answer = Integer.MAX_VALUE;

        for (int size = 1; size <= s.length() / 2 + 1; size++) {

            StringBuilder sb = new StringBuilder();

            String prev = "";  // 이전 조각
            int cnt = 1;     // 반복 횟수

            for (int j = 0; j < s.length(); j += size) {

                int end = Math.min(j + size, s.length());
                String cur = s.substring(j, end);

                if (cur.equals(prev)) {
                    cnt++;
                } else {
                    // 이전 문자열 처리
                    if (!prev.equals("")) {
                        if (cnt > 1) sb.append(cnt);
                        sb.append(prev);
                    }
                    prev = cur;
                    cnt = 1;
                }
            }

            // 마지막 남은 prev 처리
            if (cnt > 1) sb.append(cnt);
            sb.append(prev);

            answer = Math.min(answer, sb.length());
        }

        return answer;
    }
}