package Basic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {

//        int x = 98;
//        int y =0;
//        System.out.println(divideByLBYL(x,y));
//        System.out.println(divideByEAFP(x,y));

        // this will cause crash
        //System.out.println(divide(x,y));

        // will crash if called with non int!!!
        //int x = getInt();

        //manual error handling -- a lot of code.
        //int x = getIntLBYL();

        // using a try catch for invalid input -- much less code!
        int x = getIntEAFP();
        System.out.println("x is " + x );

    }

    private static int getInt(){
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }

    // manual error handling
    private static int getIntLBYL(){
        Scanner s = new Scanner(System.in);
        boolean isValid = true;
        System.out.println("Please enter an integer");
        String input = s.next();

        for (int i=0; i<input.length(); i++){
            if(!Character.isDigit(input.charAt(i))){
                isValid = false;
                break;
            }
        }
        if(isValid){
            return Integer.parseInt(input);
        }
        return 0;
    }

    private static int getIntEAFP(){
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter an int.");

        try{
            return s.nextInt();
        } catch (InputMismatchException e){
            return 0;
        }
    }

    // manual error handling
    private static int divideByLBYL (int x, int y){
        if(y!=0){
            return x /y;
        }else {
            return 0;
        }
    }

    // try catch block to catch exception
    private static int divideByEAFP(int x, int y){
        try {
            return x / y;
        } catch(ArithmeticException e){
            return 0;
        }
    }

    // no error handling, will crash when divided by 0
    private static int divide(int x, int y){
        return x / y;
    }

}
