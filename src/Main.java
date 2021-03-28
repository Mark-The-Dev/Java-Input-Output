public class Main {
    public static void main(String[] args) {

        int x = 98;
        int y =0;

        System.out.println(divideByLBYL(x,y));
        System.out.println(divideByEAFP(x,y));
        System.out.println(divide(x,y));


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
