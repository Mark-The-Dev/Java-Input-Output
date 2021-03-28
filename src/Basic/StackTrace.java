package Basic;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StackTrace {
    public static void main(String[] args) {
        int result = divide();
        System.out.println(result);
    }

    // sends a a specific exception based on bad input.
    private static int divide(){
       int x, y;
        try {
           x = getInt();
           y = getInt();
       } catch(NoSuchElementException e){
           throw new ArithmeticException("no suitable input");
       }
        System.out.println("x is " + x + " y is " + y);

        try {
            return x/y;
        } catch(ArithmeticException er){
            throw new ArithmeticException("Attempt to divide by zero");
        }

    }

    private static int getInt(){
        Scanner s = new Scanner(System.in);
        System.out.println("please enter an integer");

        while(true){
            try {
                return s.nextInt();
            } catch(InputMismatchException e){
                // go around again. Read each part of line in input first
                s.nextLine();
                System.out.println("Please enter a number only using digits");
            }
        }

    }
}
