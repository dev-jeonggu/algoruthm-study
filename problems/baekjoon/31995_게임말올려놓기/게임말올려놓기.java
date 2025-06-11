// NOTE : 31995_게임말올려놓기
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/31995

import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int result = 0;
        if(n!=1 && m!=1){
            result = (m-1) * (n-1) * 2;
        }

        System.out.println(result);
    }
}