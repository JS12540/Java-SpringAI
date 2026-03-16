# API Development Lab 🚀

A simple REST API built with **Java 21**, **Spring Boot 3.4.3**, and **Gradle**.

---

## Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.3 |
| Gradle | 9.3.1 |
| Tomcat (embedded) | 10.1.x |

---

## Project Structure

```
api-development-lab/
├── src/
│   └── main/
│       └── java/
│           └── com/jayshah/apilab/
│               ├── controller/
│               │   ├── HelloController.java
│               │   └── UserController.java
│               ├── model/
│               │   └── User.java
│               ├── service/
│               │   └── UserService.java
│               ├── util/
│               │   └── DataStore.java
│               └── ApiDevelopmentLabApplication.java
├── build.gradle
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## API Endpoints

### Hello
| Method | URL | Description |
|---|---|---|
| GET | `/hello` | Health check |

### Users
| Method | URL | Description |
|---|---|---|
| GET | `/users` | Get all users |
| POST | `/users` | Create a new user |

---

## Setup on a New Laptop

### Prerequisites
Make sure you have the following installed:
- **Java 21** → https://adoptium.net/
- **Git** → https://git-scm.com/

> Gradle does NOT need to be installed separately — the project includes a Gradle wrapper.

### Steps

**1. Clone the project**
```bash
git clone <your-repo-url>
cd api-development-lab
```

**2. Build the project**
```bash
./gradlew build
```
> On Windows use: `./gradlew build`
> On Mac/Linux use: `./gradlew build`

**3. Run the project**
```bash
./gradlew bootRun
```

**4. Test it**

Open your browser and go to:
```
http://localhost:8080/hello
```

You should see:
```
API Lab Running 🚀
```

---

## Testing the API

### Using curl

**GET all users:**
```bash
curl -X GET http://localhost:8080/users
```

**POST create a user:**
```bash
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d "{\"id\":1,\"name\":\"Jay Shah\",\"age\":25}"
```

### Using Postman

**GET /users**
```
Method: GET
URL: http://localhost:8080/users
```

**POST /users**
```
Method: POST
URL: http://localhost:8080/users
Headers: Content-Type: application/json
Body (raw JSON):
{
    "id": 1,
    "name": "Jay Shah",
    "age": 25
}
```

---

## Running with Docker

### Prerequisites
- **Docker** → https://www.docker.com/products/docker-desktop

### Steps

**1. Build and run with Docker Compose**
```bash
docker-compose up --build
```

**2. Stop the container**
```bash
docker-compose down
```

The app will be available at `http://localhost:8080`

---

## Notes

- Data is stored **in-memory** (resets on restart)
- No database is used in this version
- Default port is **8080**