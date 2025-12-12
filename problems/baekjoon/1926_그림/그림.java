// NOTE : 1926_그림
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1926

import java.util.*;

public class Main {
    static int n, m;
    static int[][] map;
    static boolean[][] visited;
    static int sum;

    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();

        map = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        int count = 0;   // 그림 개수
        int maxSum = 0; // 최대 넓이

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    sum = 0;
                    dfs(i, j);
                    count++;
                    maxSum = Math.max(maxSum, sum);
                }
            }
        }

        System.out.println(count);
        System.out.println(maxSum);
    }

    static void dfs(int x, int y) {
        visited[x][y] = true;
        sum++;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                if (map[nx][ny] == 1 && !visited[nx][ny]) {
                    dfs(nx, ny);
                }
            }
        }
    }
}
