# **Fleet Scheduling Service**

This project is a Spring Boot application that provides a service for scheduling trucks to charging stations based on the remaining charging capacity and the trucks' charging requirements. It employs an algorithm to assign trucks to chargers in the most efficient way based on the **closest sum** of charging needs.

## **Table of Contents**
- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Technologies Used](#technologies-used)
- [Configuration](#configuration)
- [Usage](#usage)
- [API](#api)
- [License](#license)

## **Overview**
This Spring Boot application calculates an optimal schedule for charging trucks. The scheduling is based on a specific algorithm (the "Closest Sum Algorithm"), which pairs trucks with chargers in such a way that minimizes charging waste. The system takes into account the number of hours the chargers will be active, the trucks' charging needs, and the available charger capacity.

## **Features**
- **Scheduling** of trucks to chargers.
- **Closest sum algorithm** to match trucks with chargers.
- **Dynamic assignment** of trucks based on charger capacity.
- **Flexibility** to handle edge cases such as no available chargers or trucks.
  
## **Getting Started**
These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### **Prerequisites**
- **JDK 11 or above**
- **Maven**
- **Spring Boot**
- IDE like IntelliJ IDEA or Eclipse (optional but recommended)

### **Installing**

1. **Clone the repository** to your local machine:
    ```bash
    git clone https://github.com/yourusername/fleet-scheduling.git
    cd fleet-scheduling
    ```

2. **Build the project**:
    ```bash
    mvn clean install
    ```

3. **Run the application**:
    ```bash
    mvn spring-boot:run
    ```

The application will start running on `http://localhost:8080` (or another port, if configured).

## **Technologies Used**
- **Spring Boot**: Framework for building Java-based applications.
- **Maven**: Dependency management and build automation tool.
- **Java 11**: Language used for the application development.
  
## **Configuration**

You can configure the number of hours for charging or any other parameters via the application properties file (`application.properties`). For example:

```properties
charging.hours=5
