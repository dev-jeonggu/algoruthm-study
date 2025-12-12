// NOTE : 9093_단어뒤집기
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/9093

import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        
        StringBuilder sb = new StringBuilder();
        for(int j=0; j<n; j++){
            String str = sc.nextLine();
            String[] arr = str.split(" ");
            for(int i=0; i<arr.length; i++){
                String tmp = new StringBuilder(arr[i]).reverse().toString();
                sb.append(tmp).append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}