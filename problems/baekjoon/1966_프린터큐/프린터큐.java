// NOTE : 1966_프린터큐
// NOTE : 백준
// NOTE : https://www.acmicpc.net/problem/1966

import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
    static class Task {
        int number;       // 데이터
        int priority;     // 우선순위
    
        Task(int number, int priority) {
            this.number = number;
            this.priority = priority;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++){
            int m = sc.nextInt();
            int index = sc.nextInt();
            
            Queue<Task> queue = new LinkedList<>();
            for(int j=0; j<m;j++){
                int tmp = sc.nextInt();
                queue.add(new Task(tmp, j));
            }
            
            int cnt = 0;
            while(true){
                if (!queue.isEmpty()) {
                    Task task = queue.peek();

                    Task maxTask = Collections.max(queue, Comparator.comparingInt(t -> t.number));
                    
                    if (task.number < maxTask.number) {
                        queue.add(task);
                    } else{
                        cnt++;
                        if(index == task.priority){
                            break;
                        }
                    }
                    queue.poll();
                }else{
                    break;
                }
            }
            sb.append(cnt).append("\n");
        }
        System.out.println(sb.toString());
    }
}