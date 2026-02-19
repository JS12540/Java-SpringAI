# Spring AI Demo Project Setup Guide

This document explains the complete setup process:

* Install Java 21 (LTS)
* Configure `JAVA_HOME`
* Install Gradle (Binary distribution)
* Configure environment variables
* Create project using Spring Initializr
* Build and run the project using Gradle Wrapper

---

# 1️⃣ Install Java 21 (LTS)

## Step 1: Download Java 21

Go to:

[https://adoptium.net/](https://adoptium.net/)

Download:

* Version: **21 (LTS)**
* OS: Windows
* Package: MSI Installer

Install it normally.

---

## Step 2: Verify Installation

Open PowerShell:

```powershell
java --version
```

Expected output:

```
openjdk 21.x.x
```

---

# 2️⃣ Set JAVA_HOME (Using PowerShell)

Find your Java 21 installation path. Example:

```
C:\Program Files\Eclipse Adoptium\jdk-21.0.x
```

---

## Set JAVA_HOME (Administrator PowerShell)

```powershell
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-21.0.x" /M
```

---

## Add Java to PATH

```powershell
setx PATH "%PATH%;%JAVA_HOME%\bin" /M
```

---

## Restart Terminal

Close all terminals and reopen PowerShell.

Verify:

```powershell
echo $env:JAVA_HOME
```

```powershell
where java
```

---

# 3️⃣ Install Gradle (Binary Distribution)

## Step 1: Download Gradle

Go to:

[https://gradle.org/releases/](https://gradle.org/releases/)

Download:

* **Binary-only (bin)** ZIP

Example:

```
gradle-9.3.1-bin.zip
```

---

## Step 2: Extract Gradle

1. Create folder:

```
C:\Gradle
```

2. Extract ZIP into:

```
C:\Gradle\gradle-9.3.1
```

---

## Step 3: Configure Gradle Environment Variable

### Option A (Recommended)

Set GRADLE_HOME:

```powershell
setx GRADLE_HOME "C:\Gradle\gradle-9.3.1" /M
```

Add to PATH:

```powershell
setx PATH "%PATH%;%GRADLE_HOME%\bin" /M
```

---

Restart PowerShell.

Verify:

```powershell
gradle -v
```

---

# 4️⃣ Create Project Using Spring Initializr

Go to:

[https://start.spring.io/](https://start.spring.io/)

Use the following configuration:

| Setting       | Value  |
| ------------- | ------ |
| Project       | Gradle |
| Language      | Java   |
| Spring Boot   | 4.0.2  |
| Packaging     | Jar    |
| Java          | 21     |
| Configuration | YAML   |

Dependencies:

* Spring Web
* Lombok (optional)
* Spring Boot DevTools (optional)

Click **Generate** and download the project.

---

# 5️⃣ Open Project in VS Code

1. Extract the ZIP.
2. Open VS Code.
3. Click:

```
File → Open Folder
```

4. Select the project folder.

Wait for Gradle to import.

---

# 6️⃣ Build Project Using Gradle Wrapper

Inside project root:

```powershell
.\gradlew clean build
```

Expected output:

```
BUILD SUCCESSFUL
```

---

# 7️⃣ Run the Application

```powershell
.\gradlew bootRun
```

Expected output:

```
Tomcat started on port 8080
```

Test in browser:

```
http://localhost:8080
```

---

# 8️⃣ Important Notes

## Why Use Gradle Wrapper?

Every Spring Boot project includes:

```
gradlew
gradlew.bat
```

This ensures:

* Correct Gradle version
* No global Gradle dependency
* Consistent builds across machines

---

## Common Commands

Build project:

```powershell
.\gradlew build
```

Clean project:

```powershell
.\gradlew clean
```

Run application:

```powershell
.\gradlew bootRun
```

Run tests:

```powershell
.\gradlew test
```

---

# 9️⃣ Project Structure

```
spring-ai-demo
 ├── build.gradle
 ├── settings.gradle
 ├── gradlew
 ├── gradlew.bat
 ├── gradle/
 └── src/
     └── main/
         ├── java/
         └── resources/
```

---

# 🔟 Next Steps

After basic setup works:

* Add Spring AI dependency
* Configure OpenAI API
* Create REST controller
* Dockerize application
* Deploy to cloud or Kubernetes

---

# Environment Summary

* Java: 21 (LTS)
* Gradle: 9.x (Binary Distribution)
* Spring Boot: 4.0.2
* Build Tool: Gradle Wrapper
* IDE: VS Code

---

Setup Complete.
