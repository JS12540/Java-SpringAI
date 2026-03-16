/*--------------------------------------------------
  Student Class - Demonstrating Different Constructors
---------------------------------------------------*/

class Student{
    String name;
    int age;
    String city;

    // 1️⃣ Default Constructor
    Student(){
        name = "unknown";
        age=0;
        System.out.println("Default Constructor called");
    }

    // 2️⃣ Parameterized Constructor
    Student(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Parameterized Constructor Called");
        System.out.println(name + " " + age);
    }

    // 3️⃣ Constructor Overloading
    Student(String name) {
        this.name = name;
        System.out.println("Constructor Overloading Example");
        System.out.println("Name: " + name);
    }

    // 4️⃣ Copy Constructor
    Student(Student s) {
        this.name = s.name;
        this.age = s.age;
        System.out.println("Copy Constructor Called");
        System.out.println("Copied: " + name + " " + age);
    }

    // 5️⃣ Constructor Chaining
    Student(String name, int age, String city) {
        this(name, age); // calls parameterized constructor
        this.city = city;
        System.out.println("Constructor Chaining Example");
        System.out.println(name + " " + age + " " + city);
    }

}

/*--------------------------------------------------
 Private Constructor Example (Singleton Pattern)
---------------------------------------------------*/
class Singleton {

    private static Singleton instance;

    // private constructor
    private Singleton() {
        System.out.println("Private Constructor Called");
    }

    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}


/*--------------------------------------------------
 Destructor Concept (Garbage Collection)
---------------------------------------------------*/
class DestructorDemo {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Object is being garbage collected (Destructor concept)");
    }

}

public class Main{
    public static void main(String[] args) {
        System.out.println("---- Default Constructor ----");
        Student s1 = new Student();

        System.out.println("\n---- Parameterized Constructor ----");
        Student s2 = new Student("Jay", 25);

        System.out.println("\n---- Constructor Overloading ----");
        Student s3 = new Student("Rahul");

        System.out.println("\n---- Copy Constructor ----");
        Student s4 = new Student(s2);

        System.out.println("\n---- Constructor Chaining ----");
        Student s5 = new Student("Amit", 22, "Mumbai");

        System.out.println("\n---- Private Constructor Example ----");
        Singleton obj = Singleton.getInstance();

        System.out.println("\n---- Destructor Concept (Garbage Collection) ----");
        DestructorDemo d = new DestructorDemo();
        d = null;  // object eligible for GC
        System.gc(); // request garbage collection
    }
}
