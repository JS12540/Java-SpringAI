/*
 * FunctionsLab.java
 *
 * This file explains:
 * - All types of methods in Java
 * - Static vs Non-static
 * - Return types
 * - Parameters
 * - Method overloading
 * - Recursion
 * - Varargs
 * - Pass by value
 *
 * Also explains which style is most used in production.
 */

public class Main {
    // =========================================================
    // 1️⃣ SIMPLE VOID METHOD (Static)
    // =========================================================
    static void greet(){
        System.out.println("Hello from greet()");
    }

    // =========================================================
    // 2️⃣ METHOD WITH RETURN TYPE
    // =========================================================
    static int add(int a, int b) {
        return a + b;
    }

    // =========================================================
    // 3️⃣ METHOD WITH MULTIPLE PARAMETERS
    // =========================================================
    static double multiply(double x, double y) {
        return x * y;
    }

    // =========================================================
    // 4️⃣ METHOD OVERLOADING
    // Same method name, different parameters
    // =========================================================
    static int square(int num) {
        return num * num;
    }

    static double square(double num) {
        return num * num;
    }

    // =========================================================
    // 5️⃣ VARARGS (Variable arguments)
    // Allows multiple inputs
    // =========================================================
    static int sumAll(int... numbers){
        int sum=0;
        for(int n: numbers){
            sum+=n;
        }
        return sum;
    }

    // =========================================================
    // 6️⃣ RECURSION
    // Function calling itself
    // =========================================================
    static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    // =========================================================
    // 7️⃣ NON-STATIC METHOD
    // Requires object to call
    // =========================================================
    String getMessage(String name) {
        return "Welcome " + name;
    }

    // =========================================================
    // 8️⃣ PASS BY VALUE DEMO
    // Java is ALWAYS pass-by-value
    // =========================================================
    static void changeValue(int x) {
        x = 100;
    }

    static void changeArray(int[] arr) {
        arr[0] = 999;
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================
    public static void main(String[] args){
        System.out.println("===== FUNCTIONS LAB =====");

        // 1️⃣ Void method
        greet();

        // 2️⃣ Method with return
        int result = add(5, 3);
        System.out.println("Addition: " + result);

        // 3️⃣ Multiple parameters
        System.out.println("Multiplication: " + multiply(2.5, 4.0));

        // 4️⃣ Overloading
        System.out.println("Square int: " + square(4));
        System.out.println("Square double: " + square(5.5));

        // 5️⃣ Varargs
        System.out.println("SumAll: " + sumAll(1, 2, 3, 4, 5));

        // 6️⃣ Recursion
        System.out.println("Factorial 5: " + factorial(5));

        Main obj = new Main();
        System.out.println(obj.getMessage("Java is awesome"));

        // 8️⃣ Pass by value
        int number = 10;
        changeValue(number);
        System.out.println("After changeValue(): " + number);

        int[] array = {10, 20, 30};
        changeArray(array);
        System.out.println("After changeArray(): " + array[0]);

        /*
         Explanation:
         number remains 10 because primitives are passed by value.
         array changes because reference value is copied,
         but both point to same heap object.
         */

        System.out.println("===== END =====");
    }
}
