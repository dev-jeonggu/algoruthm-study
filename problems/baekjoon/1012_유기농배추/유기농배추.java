// NOTE : 1012_유기농배추
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1012

import java.util.*;

class Main {
    static int M, N, K;
    static int[][] field;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) {
            M = sc.nextInt(); // 가로
            N = sc.nextInt(); // 세로
            K = sc.nextInt(); // 배추 개수

            field = new int[N][M];
            visited = new boolean[N][M];

            for (int i = 0; i < K; i++) {
                int x = sc.nextInt(); // 열
                int y = sc.nextInt(); // 행
                field[y][x] = 1;
            }

            int worms = 0;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    if (field[r][c] == 1 && !visited[r][c]) {
                        dfs(r, c);
                        worms++;
                    }
                }
            }
            System.out.println(worms);
        }
    }

    static void dfs(int r, int c) {
        visited[r][c] = true;
        for (int i = 0; i < 4; i++) {
            int nr = r + dy[i];
            int nc = c + dx[i];
            if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                if (field[nr][nc] == 1 && !visited[nr][nc]) {
                    dfs(nr, nc);
                }
            }
        }
    }
}
