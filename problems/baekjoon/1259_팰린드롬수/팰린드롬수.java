// NOTE : 1259_팰린드롬수
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1259

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        while(true){
            String S = sc.nextLine();
            if(Integer.parseInt(S) == 0){
                break;
            }

            sb.append(check(S)).append("\n");
        }
        System.out.println(sb.toString());
    }
    
    public static String check(String S){
        for(int i=0; i<S.length()/2; i++){
            if(S.charAt(i) != S.charAt(S.length()-i-1)){
                return "no";
            }
        }
        return "yes";
    }
}