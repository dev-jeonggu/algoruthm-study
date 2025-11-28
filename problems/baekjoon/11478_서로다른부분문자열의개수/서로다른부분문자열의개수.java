import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int S = sc.nextLine();

        HashSet<String> set = new HashSet<>();
        for(int i=0; i<S.length(); i++){
            for(int j=i; j<S.length(); j++){
                Strubg sub = S.substring(i,j+1);
                set.add(sub);
            }
        }

        System.out.println(set.size());
    }
}