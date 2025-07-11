// NOTE : [1차]셔틀버스
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/17678

import java.util.*;

class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        List<Integer> crewTimes = new ArrayList<>();
        for (String time : timetable) {
            String[] parts = time.split(":");
            int minutes = Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
            crewTimes.add(minutes);
        }

        Collections.sort(crewTimes);

        int busTime = 540; 
        int idx = 0;

        for (int i = 0; i < n; i++) {
            int cnt = 0;
            while (idx < crewTimes.size() && crewTimes.get(idx) <= busTime && cnt < m) {
                idx++;
                cnt++;
            }

            if (i == n - 1) {
                if (cnt < m) {
                    return toTime(busTime);
                } else {
                    return toTime(crewTimes.get(idx - 1) - 1);
                }
            }

            busTime += t;
        }

        return "";
    }

    private String toTime(int time) {
        int hour = time / 60;
        int min = time % 60;
        return String.format("%02d:%02d", hour, min);
    }
}
