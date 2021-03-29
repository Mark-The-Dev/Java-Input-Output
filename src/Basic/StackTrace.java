package Basic;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StackTrace {
    public static void main(String[] args) {

        // catches all errors
        //could remove the try catch from divide and catch(ArithmeticException | NoSuchElementException e) in main!
        try {
            int result = divide();
            System.out.println(result);
        } catch(ArithmeticException e){
            System.out.println(e.toString());
            System.out.println("Unable to perform division, autopilot shutting down!");
        }



    }

    // sends a a specific exception based on bad input.
    private static int divide(){
       int x, y;
        try {
           x = getInt();
           y = getInt();
           System.out.println("x is " + x + " y is " + y);
            return x/y;
       } catch(NoSuchElementException e){
           throw new ArithmeticException("no suitable input");
       } catch(ArithmeticException e){
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
