// NOTE : 주차요금계산
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/92341?language=java

import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> result = new HashMap<>();

        for (int i = 0; i < records.length; i++) {
            String[] record = records[i].split(" ");
            String[] time = record[0].split(":");
            int minute = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            String number = record[1];
            String inout = record[2];

            if (inout.equals("IN")) {
                map.put(number, minute);
            } else {
                int inMinute = map.remove(number);
                int duration = minute - inMinute;
                result.put(number, result.getOrDefault(number, 0) + duration);
            }
        }

        for (String number : map.keySet()) {
            int inMinute = map.get(number);
            int duration = (23 * 60 + 59) - inMinute;
            result.put(number, result.getOrDefault(number, 0) + duration);
        }

        List<String> carNums = new ArrayList<>(result.keySet());
        Collections.sort(carNums);

        int[] answer = new int[carNums.size()];
        for (int i = 0; i < carNums.size(); i++) {
            int totalTime = result.get(carNums.get(i));
            answer[i] = culcurate(totalTime, fees);
        }

        return answer;
    }

    public static int culcurate(int totalTime, int[] fees) {
        int baseTime = fees[0];
        int baseFee = fees[1];
        int unitTime = fees[2];
        int unitFee = fees[3];

        if (totalTime <= baseTime) return baseFee;
        return baseFee + (int) Math.ceil((totalTime - baseTime) / (double) unitTime) * unitFee;
    }
}