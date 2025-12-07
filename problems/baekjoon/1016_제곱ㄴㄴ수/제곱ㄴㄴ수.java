// NOTE : 1016_제곱ㄴㄴ수
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1016

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long min = sc.nextLong();
        long max = sc.nextLong();

        int size = (int)(max - min + 1);
        boolean[] check = new boolean[size];

        for(long i = 2; i * i <= max; i++){
            long square = i * i;

            long start = (min + square - 1) / square * square;

            for(long j = start; j <= max; j += square){
                check[(int)(j - min)] = true;
            }
        }

        int count = 0;
        for(int i = 0; i < size; i++){
            if(!check[i]) count++;
        }

        System.out.println(count);
    }
}
