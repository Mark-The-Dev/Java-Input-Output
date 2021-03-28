package Basic;

import java.util.Scanner;

public class StackTrace {
    public static void main(String[] args) {
        int result = divide();
        System.out.println(result);
    }

    // sends a stack trace if wrong input.
    private static int divide(){
        int x = getInt();
        int y = getInt();
        System.out.println("x is " + x + " y is " + y);
        return x/y;
    }

    private static int getInt(){
        Scanner s = new Scanner(System.in);
        System.out.println("please enter an integer");
        return s.nextInt();
    }
}
