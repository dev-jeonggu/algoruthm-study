// NOTE : 베스트앨범
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/42579

import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {

        Map<String, Integer> sumMap = new HashMap<>();
        Map<String, Map<Integer, Integer>> map = new HashMap<>();

        for (int i = 0; i < genres.length; i++) {

            Map<Integer, Integer> temp =
                    map.getOrDefault(genres[i], new HashMap<>());
            temp.put(i, plays[i]);

            int sumTemp = sumMap.getOrDefault(genres[i], 0);
            sumTemp += plays[i];

            sumMap.put(genres[i], sumTemp);
            map.put(genres[i], temp);
        }

        List<String> genreOrder = new ArrayList<>(sumMap.keySet());
        genreOrder.sort((a, b) -> sumMap.get(b) - sumMap.get(a));

        List<Integer> result = new ArrayList<>();

        for (String g : genreOrder) {
            List<Map.Entry<Integer, Integer>> list =
                    new ArrayList<>(map.get(g).entrySet());

            list.sort((a, b) -> {
                if (a.getValue().equals(b.getValue()))
                    return a.getKey() - b.getKey(); 
                return b.getValue() - a.getValue();
            });

            result.add(list.get(0).getKey());
            if (list.size() > 1)
                result.add(list.get(1).getKey());
        }

        return result.stream().mapToInt(i -> i).toArray();
    }
}