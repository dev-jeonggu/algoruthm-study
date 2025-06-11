// NOTE : 1541_잃어버린괄호
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1541

import java.util.*;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tokens = sc.nextLine().split("-");

        int result = 0;

        for (int i = 0; i < tokens.length; i++) {
            String[] plusTokens = tokens[i].split("\\+");
            int sum = 0;
            for (String s : plusTokens) {
                sum += Integer.parseInt(s);
            }

            if (i == 0) {
                result += sum;
            } else {
                result -= sum;
            }
        }

        System.out.println(result);
    }
}
