
// NOTE : 1026_보물
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1026

import java.util.*;

public class Main{
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      int n = sc.nextInt();
      List<Integer> a = new ArrayList<>();
      for(int i=0; i<n; i++){
         int tmp = sc.nextInt();
         a.add(tmp);
      }
      
      List<Integer> b = new ArrayList<>();
      for(int i=0; i<n; i++){
         int tmp = sc.nextInt();
         b.add(tmp);
      }
      
      a.sort(null);
      b.sort(Collections.reverseOrder());

      int sum = 0;
      for(int i=0; i<n; i++){
         sum += (a.get(i) * b.get(i));
      }

      System.out.println(sum);
   }
}