public class Main {
    public static void main(String []args){
        byte byteVal = 10;
        short shortVal = 100;
        int intVal = 1000;
        long longVal = 100000L;

        float floatVal = 10.5f;
        double doubleVal = 20.99;

        char charVal = 'A';
        boolean boolVal = true;

        // ===============================
        // 2️⃣ Printing like Python style
        // ===============================

        // Way 1: Simple println()
        System.out.println("----- Using println() -----");
        System.out.println("Byte: " + byteVal);
        System.out.println("Short: " + shortVal);
        System.out.println("Int: " + intVal);
        System.out.println("Long: " + longVal);
        System.out.println("Float: " + floatVal);
        System.out.println("Double: " + doubleVal);
        System.out.println("Char: " + charVal);
        System.out.println("Boolean: " + boolVal);

        // ===============================
        // Way 2: print() (no new line)
        // ===============================
        System.out.println("\n----- Using print() -----");
        System.out.print("Int: ");
        System.out.print(intVal);
        System.out.print(" | Double: ");
        System.out.print(doubleVal);
        System.out.println(); // manual new line

        // ===============================
        // Way 3: printf() (like Python f-strings)
        // ===============================
        System.out.println("\n----- Using printf() -----");
        System.out.printf("Int value = %d\n", intVal);
        System.out.printf("Double value = %.2f\n", doubleVal);
        System.out.printf("Char = %c and Boolean = %b\n", charVal, boolVal);

        // ===============================
        // Way 4: String.format() (Very clean way)
        // ===============================
        System.out.println("\n----- Using String.format() -----");
        String formatted = String.format("Int: %d, Double: %.2f", intVal, doubleVal);
        System.out.println(formatted);

        // ===============================
        // Way 5: Multiple values in one line
        // ===============================
        System.out.println("\n----- Multiple values -----");
        System.out.println(byteVal + " " + shortVal + " " + intVal);

        // ===============================
        // Way 6: Escape characters
        // ===============================
        System.out.println("\n----- Escape Characters -----");
        System.out.println("Hello\nWorld");
        System.out.println("Hello\tJava");
        System.out.println("She said \"Hello\"");

    }
}
