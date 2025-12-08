// NOTE : 2805_나무자르기
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/2805

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();   // 나무 개수
        int m = sc.nextInt();   // 필요한 나무 길이

        int[] arr = new int[n];
        int max = 0;

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
            max = Math.max(max, arr[i]);
        }

        int left = 0;
        int right = max;
        int answer = 0;

        while (left <= right) {
            int mid = (left + right) / 2;

            long sum = Arrays.stream(arr)
                    .filter(x -> x > mid)
                    .map(x -> x - mid)
                    .asLongStream()
                    .sum();

            if (sum >= m) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        System.out.println(answer);
    }
}
