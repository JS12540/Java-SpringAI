/*
 * MemoryLab.java
 *
 * This file explains:
 * - Stack vs Heap memory
 * - Arrays
 * - Strings
 * - References (Java version of pointers)
 * - String Pool
 * - Immutability
 * - Garbage Collection basics
 */

public class Main {
    public static void main(String[] args){
        /*
         =========================================================
         1️⃣ STACK vs HEAP MEMORY
         =========================================================

         STACK:
         - Stores primitive variables
         - Stores references (addresses)
         - Method calls live here

         HEAP:
         - Stores actual objects
         - Arrays
         - Strings (mostly)
         */
        // Primitive variable (stored directly in stack)
        int number = 10;

        // Reference variable (stored in stack)
        // Object stored in heap
        String name = "Java";

        System.out.println("Primitive number: " + number);
        System.out.println("Reference variable name: " + name);

        /*
         Memory visualization:

         STACK                HEAP
         -----                -----
         number = 10
         name  ------->       "Java"
         */

        /*

        /*
         =========================================================
         2️⃣ ARRAYS
         =========================================================

         Arrays are OBJECTS in Java.
         They are stored in HEAP memory.
         */

        int[] arr = new int [3]; // creates array in heap

        arr[0] = 10;
        arr[1] = 20;
        arr[2] = 30;

        System.out.println("\nArray Value:");
        for (int i=0; i<arr.length;i++){
            System.out.println(arr[i]);
        }
        /*
         Memory:

         STACK                        HEAP
         -----                        -----
         arr  --------->              [10,20,30]
         */

        /*
         =========================================================
         3️⃣ REFERENCES (Java version of pointers)
         =========================================================

         Java does NOT support direct pointers like C/C++.
         But reference variables behave similarly.
         */

        int[] arr2 = arr; // both point to same array

        arr2[0] = 999;

        System.out.println("\nAfter modifying arr2:");
        System.out.println("arr[0]: " + arr[0]);

        /*
         Why did arr change?

         Because:
         arr and arr2 both store SAME reference address.
         They point to same heap object.
         */

        /*
        =========================================================
         4️⃣ STRINGS
         =========================================================
         */
        // Stored in String Pool
        String s1 = "Hello";
        String s2 = "Hello";

        // Stored in Heap (outside pool)
        String s3 = new String("Hello");

        // == checks reference
        System.out.println("s1 == s2: " + (s1 == s2)); // true (same pool)

        System.out.println("s1 == s3: " + (s1 == s3)); // false (different object)

        // .equals() checks value
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true

        /*
         STRING POOL:
         - Special memory area inside heap
         - Avoids duplicate string objects
         */

        /*
         =========================================================
         5️⃣ STRING IMMUTABILITY
         =========================================================
         */

        String text = "Java";
        text.concat(" Programming");

        System.out.println("\nString immutability:");
        System.out.println(text);

        /*
         Output: Java

         Why?

         Because String is IMMUTABLE.
         concat() creates NEW object.
         It does NOT modify existing one.
         */

        text = text.concat(" Programming");

        System.out.println(text);

        /*
         =========================================================
         6️⃣ GARBAGE COLLECTION
         =========================================================

         When no reference points to an object,
         it becomes eligible for Garbage Collection.
         */

        String garbage = new String("Temporary");
        garbage = null; // no reference now

        /*
         Now object is eligible for GC.
         Java automatically cleans it.
         */

        /*

        =========================================================
         7️⃣ MULTI-DIMENSIONAL ARRAY
         =========================================================
         */

        int[][] matrix = {
            {1,2},
            {3,4}
        };
        System.out.println("\n2D Array:");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        /*
         =========================================================
         FINAL MEMORY SUMMARY
         =========================================================

         Primitive → Stored directly in STACK
         Objects → Stored in HEAP
         Reference variables → Stored in STACK
         String literals → Stored in String Pool
         Arrays → Objects in HEAP
         Java has NO raw pointers
         Garbage Collector manages memory automatically
         */
    }
}
