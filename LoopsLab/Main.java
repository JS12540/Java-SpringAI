/*
 * LoopsLab.java
 *
 * Complete guide to all loop types in Java.
 */

public class Main {
    public static void main(String[] args){
        // =========================================================
        // 1️⃣ WHILE LOOP
        // =========================================================
        System.out.println("----- WHILE LOOP -----");

        int i=1;
        while(i<=5){
            System.out.println("i= "+ i);
            i++;
        }
        // =========================================================
        // 2️⃣ DO-WHILE LOOP
        // =========================================================
        System.out.println("\n----- DO-WHILE LOOP -----");

        int j = 1;
        do {
            System.out.println("j = " + j);
            j++;
        } while (j <= 5);
        // =========================================================
        // 3️⃣ FOR LOOP
        // =========================================================
        System.out.println("\n----- FOR LOOP -----");

        for (int k = 1; k <= 5; k++) {
            System.out.println("k = " + k);
        }

        // =========================================================
        // 4️⃣ ENHANCED FOR LOOP (FOR-EACH)
        // =========================================================
        System.out.println("\n----- ENHANCED FOR LOOP -----");

        int[] numbers = {10, 20, 30, 40};

        for (int num : numbers){
            System.out.println("Value: "+ num);
        }
        // =========================================================
        // 5️⃣ NESTED LOOPS
        // =========================================================
        System.out.println("\n----- NESTED LOOP -----");

        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                System.out.print("* ");
            }
            System.out.println();
        }

        // =========================================================
        // 6️⃣ BREAK STATEMENT
        // =========================================================
        System.out.println("\n----- BREAK -----");

        for (int x = 1; x <= 10; x++) {
            if (x == 5) {
                break;
            }
            System.out.println("x = " + x);
        }
        // =========================================================
        // 7️⃣ CONTINUE STATEMENT
        // =========================================================
        System.out.println("\n----- CONTINUE -----");

        for (int y = 1; y <= 5; y++) {
            if (y == 3) {
                continue;
            }
            System.out.println("y = " + y);
        }
        // =========================================================
        // 8️⃣ INFINITE LOOP (Controlled with break)
        // =========================================================
        System.out.println("\n----- INFINITE LOOP -----");

        int z = 1;
        while (true) {
            System.out.println("z = " + z);
            if (z == 3) {
                break;
            }
            z++;
        }

        // =========================================================
        // 9️⃣ LOOP THROUGH STRING
        // =========================================================
        System.out.println("\n----- LOOP THROUGH STRING -----");

        String text="JAVA";

        for (int index=0; index<text.length();index++){
            System.out.println("Character: "+ text.charAt(index));
        }
        // =========================================================
        // 🔟 LABELED LOOP (ADVANCED)
        // =========================================================
        System.out.println("\n----- LABELED LOOP -----");

        outerLoop:
        for (int a = 1; a <= 3; a++) {
            for (int b = 1; b <= 3; b++) {
                if (a == 2 && b == 2) {
                    break outerLoop;
                }
                System.out.println("a=" + a + " b=" + b);
            }
        }
    }
}
