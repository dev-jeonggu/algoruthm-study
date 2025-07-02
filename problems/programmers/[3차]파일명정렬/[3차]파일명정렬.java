// NOTE : [3차]파일명정렬
// NOTE : 프로그래머스
// NOTE : https://school.programmers.co.kr/learn/courses/30/lessons/17686

import java.util.*;

public class File{
    String head;
    int num;
    String tail;
    String fullName;
    
    public File(String head, int num, String tail, String fullName) {
        this.head = head;
        this.num = num;
        this.tail = tail;
        this.fullName = fullName;
    }
    public String getHead(){
        return head;
    }
    public int getNum(){
        return num;
    }
}

class Solution {
    public String[] solution(String[] files) {
        String[] answer = new String[files.length];
        List<File> list = new ArrayList<>();
        for(int i=0; i<files.length; i++){
            String file = files[i];
            int indexHead = numIndexOf(file);
            String head = file.substring(0,indexHead).toLowerCase();
            int indexNum = numString(file.substring(indexHead, file.length()));
            int num = Integer.parseInt(file.substring(indexHead, indexHead + indexNum+1));
            String tail = file.substring(indexHead + indexNum+1, file.length()).toLowerCase();
            
            list.add(new File(head, num, tail, file));
        }
        list.sort(Comparator.comparing(File::getHead).thenComparingInt(File::getNum));
        for(int i=0; i<list.size(); i++){
            answer[i] = list.get(i).fullName;
        }
        return answer;
    }
    
    int numIndexOf(String str){
        int index = str.length()-1;

        for(int i=0; i<10; i++){
            int tmp = str.indexOf(Integer.toString(i));
            if(tmp>=0){
                index = Math.min(index,tmp);
            }
        }
        
        return index;
    }
    
    int numString(String str){
        String num = "0,1,2,3,4,5,6,7,8,9";
        int result = 0;
        for(int i=0; i<str.length(); i++){
            if(num.indexOf(str.charAt(i))<0){
                break;
            }
            result = i;
        }
        return result; 
    }
}