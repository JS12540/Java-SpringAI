/*
=========================================================
OOPConceptsDemo.java
This file explains all major OOPS concepts in Java:

1. Encapsulation
2. Inheritance
3. Polymorphism (Compile-time & Run-time)
4. Abstraction
5. Interfaces
6. Composition
7. Aggregation
8. Association
9. Method Overloading
10. Method Overriding

All concepts are demonstrated in ONE single file.
=========================================================
*/

/*
=========================================================
1. ENCAPSULATION
Encapsulation means wrapping data (variables) and code
(methods) together in a single unit (class).

- Variables are made private
- Access provided through public getter/setter methods
=========================================================
*/

class Student {
    private String name; // private data 
    private int age;

    // Getter method
    public String getName() {
        return name;
    }

    // Setter method

    /*
    ---------------------------------------------------------
    WHAT IS "this" KEYWORD IN JAVA?
    ---------------------------------------------------------

    "this" is a reference variable that refers to the
    current object of the class.

    It is mainly used to:

    1) Differentiate between instance variables and
       local variables (when they have the same name)

    2) Call current class methods

    3) Call current class constructor

    4) Pass current object as argument

    ---------------------------------------------------------
    WHY WE WRITE: this.name = name;
    ---------------------------------------------------------

    Here:

    private String name;  ---> Instance variable (belongs to object)

    public void setName(String name)
    ---> "name" inside parameter is a LOCAL variable

    Both have SAME NAME.

    So:

    name = name;  ❌  (Wrong)
    This would assign the parameter to itself.

    this.name = name;  ✅ (Correct)
    
    Meaning:
    this.name  ---> refers to instance variable
    name       ---> refers to method parameter

    So we are assigning parameter value to object variable.
    ---------------------------------------------------------
    */
    public void setName(String name){
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if(age > 0) {   // validation logic
            this.age = age;
        }
    }
}

/*
=========================================================
2. INHERITANCE
Inheritance allows one class to acquire properties
and behavior of another class using "extends".

Parent class -> Animal
Child class  -> Dog
=========================================================
*/

class Animal{
    void eat(){
        System.out.println("Animal is eating...");
    }

    void makeSound(){
        System.out.println("Animal is making sound");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Dog is barking...");
    }

    // Method Overriding (Run-time Polymorphism)
    @Override
    void makeSound() {
        System.out.println("Dog says: Woof Woof");
    }
}

/*
=========================================================
3. POLYMORPHISM
Polymorphism means "many forms"

Types:
1. Compile-time Polymorphism (Method Overloading)
2. Run-time Polymorphism (Method Overriding)
=========================================================
*/

// Method Overloading Example
class MathOperations {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}

/*
=========================================================
4. ABSTRACTION
Abstraction means hiding implementation details
and showing only essential features.

Achieved using:
- Abstract classes
- Interfaces
=========================================================
*/


// Abstract class
abstract class Shape {

    abstract double calculateArea(); // abstract method

    void display() {
        System.out.println("This is a shape");
    }
}

// Concrete class

class Circle extends Shape{
    double radius;

    Circle(double radius){
        this.radius = radius;
    }

    @Override
    double calculateArea(){
        return 3.14 * radius * radius;
    }
}

/*
=========================================================
5. INTERFACE
Interface is used to achieve 100% abstraction.

- Contains abstract methods by default
- Supports multiple inheritance
- Implemented using "implements"
=========================================================
*/

interface Vehicle {
    void start();
    void stop();
}

class Car implements Vehicle {

    public void start() {
        System.out.println("Car starts with key");
    }

    public void stop() {
        System.out.println("Car stops with brake");
    }
}


/*
=========================================================
6. COMPOSITION
Composition is a "has-a" relationship.

In Composition:
- Contained object cannot exist independently.
- Strong relationship.
=========================================================
*/

class Laptop {
    String brand = "Dell";
}

class Person {
    String name;
    Laptop laptop;  // composition

    Person(String name) {
        this.name = name;
        this.laptop = new Laptop();  // created inside class
    }

    void showLaptopDetails() {
        System.out.println(name + " owns a " + laptop.brand + " laptop.");
    }
}

/*
=========================================================
7. AGGREGATION
Aggregation is also a "has-a" relationship.

But:
- Contained object CAN exist independently.
- Weak relationship.
=========================================================
*/

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Employee {
    String name;
    Address address;  // aggregation

    Employee(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    void display() {
        System.out.println(name + " lives in " + address.city);
    }
}


/*
=========================================================
8. ASSOCIATION
Association represents relationship between two classes.

Example:
Teacher teaches Student
=========================================================
*/

class Teacher {
    String name;

    Teacher(String name) {
        this.name = name;
    }

    void teach(Student student) {
        System.out.println(name + " teaches " + student.getName());
    }
}



public class Main{
    public static void main(String[] args){
        // ================= ENCAPSULATION =================
        System.out.println("----- ENCAPSULATION -----");

        Student student = new Student();
        student.setName("John");
        student.setAge(20);

        System.out.println("Student Name: " + student.getName());
        System.out.println("Student Age: " + student.getAge());

        // ================= INHERITANCE =================
        System.out.println("\n----- INHERITANCE -----");

        Dog dog = new Dog();
        dog.eat();      // inherited method
        dog.bark();     // own method


        // ================= POLYMORPHISM =================
        System.out.println("\n----- POLYMORPHISM -----");

        // Compile-time polymorphism (Method Overloading)
        MathOperations math = new MathOperations();
        System.out.println("Addition int: " + math.add(5, 10));
        System.out.println("Addition double: " + math.add(5.5, 10.5));

        // Run-time polymorphism (Method Overriding)
        Animal animal = new Dog();
        animal.makeSound();   // Dog's version is called


        // ================= ABSTRACTION =================
        System.out.println("\n----- ABSTRACTION -----");

        Shape circle = new Circle(5);
        circle.display();
        System.out.println("Area: " + circle.calculateArea());

        // ================= INTERFACE =================
        System.out.println("\n----- INTERFACE -----");

        Car car = new Car();
        car.start();
        car.stop();

        // ================= COMPOSITION =================
        System.out.println("\n----- COMPOSITION -----");

        Person person = new Person("Alice");
        person.showLaptopDetails();

        // ================= AGGREGATION =================
        System.out.println("\n----- AGGREGATION -----");

        Address address = new Address("New York");
        Employee employee = new Employee("Bob", address);
        employee.display();


        // ================= ASSOCIATION =================
        System.out.println("\n----- ASSOCIATION -----");

        Teacher teacher = new Teacher("Mr. Smith");
        teacher.teach(student);
    }
}

/*
=========================================================
SUMMARY

Encapsulation  -> Data hiding using private variables
Inheritance    -> extends keyword
Polymorphism   -> Overloading & Overriding
Abstraction    -> Abstract class & Interface
Interface      -> implements keyword
Composition    -> Strong has-a relationship
Aggregation    -> Weak has-a relationship
Association    -> Relationship between two objects

=========================================================
*/
