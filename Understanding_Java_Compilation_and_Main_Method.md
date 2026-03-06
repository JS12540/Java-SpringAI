# Java Execution & `main()` Method — Complete Beginner Guide

---

## 📌 Table of Contents

1. Why We Compile with `javac` First
2. Why We Run Using `java` After
3. What is the `main()` Method?
4. Why `main()` is Required
5. Why `main()` is `public static void`
6. Where `main()` Should Be Present
7. How Java Execution Actually Works Internally
8. How This Changes in Spring Boot
9. Java vs Python Execution Comparison
10. Summary

---

# 1️⃣ Why We Compile with `javac` First

Java is a **compiled language**.

When you write:

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

This is called **source code** (`.java` file).

Computers do NOT understand Java directly.

So we must convert it into **bytecode**.

That is done using:

```bash
javac Main.java
```

This creates:

```
Main.class
```

### What is `.class` file?

It contains **bytecode** — special instructions that the **JVM (Java Virtual Machine)** understands.

---

# 2️⃣ Why We Run Using `java` After

After compilation, we run:

```bash
java Main
```

⚠️ Important:

* We DO NOT write `java Main.java`
* We DO NOT write `java Main.class`

We write only:

```bash
java ClassName
```

### What happens internally?

1. JVM loads `Main.class`
2. JVM looks for:

```java
public static void main(String[] args)
```

3. Execution starts from there.

---

# 3️⃣ What is the `main()` Method?

```java
public static void main(String[] args)
```

This is the **entry point** of a Java program.

Java programs always start execution from this method.

If this method is missing → program cannot start.

---

# 4️⃣ Why `main()` is Required

When you run:

```bash
java Main
```

The JVM asks:

> "Where should I begin execution?"

It specifically looks for:

```java
public static void main(String[] args)
```

If not found → error:

```
Error: Main method not found in class Main
```

So yes — it is required in the class you want to execute.

---

# 5️⃣ Why `main()` is `public static void`

Let’s break it down:

### 🔹 public

JVM must access it from outside the class.

If it’s private → JVM cannot call it.

---

### 🔹 static

JVM does NOT create an object before starting.

If it wasn’t static, JVM would need:

```java
Main obj = new Main();
obj.main();
```

But JVM doesn’t do that.

So it must be static.

---

### 🔹 void

It returns nothing.

The program just runs and exits.

---

### 🔹 main

This name is special.

JVM specifically looks for a method named `main`.

---

### 🔹 String[] args

This allows command-line arguments.

Example:

```bash
java Main hello 25
```

Inside program:

```java
System.out.println(args[0]); // hello
System.out.println(args[1]); // 25
```

---

# 6️⃣ Where Should `main()` Be Present?

Only in the class you want to run.

Example project:

```
JavaLab/
│
├── Main.java      ← contains main()
├── Person.java    ← no main()
├── Car.java       ← no main()
```

Only `Main.java` needs `main()`.

Other classes are helper classes.

---

# 7️⃣ How Java Execution Actually Works Internally

### Step 1 — Write Code

You write `.java` file.

### Step 2 — Compile

```bash
javac FileName.java
```

Converts to bytecode (`.class`).

### Step 3 — Run

```bash
java FileName
```

### Step 4 — JVM Process

* JVM loads class
* Finds `main()` method
* Starts execution
* Executes code line by line
* Program ends

---

# 8️⃣ How This Works in Spring Boot

In Spring Boot, you STILL need a `main()` method.

Example:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### What happens here?

1. JVM starts from `main()`
2. `SpringApplication.run()` starts:

   * Embedded server (Tomcat)
   * Spring container
   * Dependency injection
   * Auto configuration

So even in Spring Boot:

👉 `main()` is still the entry point
👉 It just delegates control to Spring framework

---

# 9️⃣ Java vs Python Execution Comparison

| Java           | Python                      |
| -------------- | --------------------------- |
| Compiled       | Interpreted                 |
| Needs `javac`  | No compile step             |
| Runs via JVM   | Runs via Python interpreter |
| Needs `main()` | Not required                |
| Strict typing  | Dynamic typing              |

Python:

```bash
python main.py
```

Java:

```bash
javac Main.java
java Main
```

---

# 🔟 Summary

✔ Java must be compiled using `javac`
✔ `javac` creates `.class` bytecode
✔ JVM runs the `.class` file using `java`
✔ `main()` is the entry point
✔ JVM always looks for `public static void main(String[] args)`
✔ Only the starting class needs `main()`
✔ Spring Boot still uses `main()` to start application

---

# 🚀 Final Understanding

Think of Java program like a movie:

* `javac` = Editing the movie into final format
* `.class` = Final movie file
* `java` = Pressing play
* `main()` = The START button

Without `main()`, the movie cannot begin.
