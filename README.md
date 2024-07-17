
# Employee Management System (EMS)

EMS is a Spring Boot application designed for managing employees. It utilizes Thymeleaf and Bootstrap 4 for the frontend and connects to a MySQL database.

## Prerequisites

Before you begin, ensure you have the following installed:

- JDK 17
- Maven 
- MySQL
- Git
- Lombok (installed in your IDE)

## Setup Instructions

1. **Set MySQL database**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ems_liquibase
   ```
   - Create a MySQL database named **ems_liquibase**.
     
2. **Set MySQL Environment Variables**
   - Open Command Prompt
   - Navigate to Your Project Directory
      ```cmd
     cd path\to\your\project
      ```
   
    **WINDOWS**
     ```cmd
     set MYSQL_USERNAME=your_mysql_username
     set MYSQL_PWD=your_mysql_password
     ```
    **LINUX**

     ```bash
     export MYSQL_USERNAME=your_mysql_username
     export MYSQL_PWD=your_mysql_password
     ```

3. **Build and Run the Application**
      - Open Command Prompt navigate to your project and run the following commands:
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

4. **Access the Application**
   - Once the application is running, you can access it at `http://localhost:3002/hello`.
   - this is public api to check whether the project is running you can see message "hi this is ems application"

