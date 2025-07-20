# Motorph Login System

This project is a GUI-based login system for managing employee data and attendance for "motorph". It allows users to log in and access employee information and attendance records.

## Project Structure

```
motorph-login-system
├── src
│   ├── com
│   │   └── motorph
│   │       ├── Main.java
│   │       ├── gui
│   │       │   ├── LoginFrame.java
│   │       │   └── DashboardFrame.java
│   │       ├── model
│   │       │   ├── Employee.java
│   │       │   └── Attendance.java
│   │       ├── service
│   │       │   ├── EmployeeService.java
│   │       │   └── AttendanceService.java
│   │       └── util
│   │           └── DatabaseUtil.java
├── README.md
├── pom.xml
└── .gitignore
```

## Setup Instructions

1. **Clone the repository**:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the project directory**:
   ```
   cd motorph-login-system
   ```

3. **Build the project**:
   Use Maven to build the project:
   ```
   mvn clean install
   ```

4. **Run the application**:
   Execute the main class:
   ```
   java -cp target/motorph-login-system-1.0-SNAPSHOT.jar com.motorph.Main
   ```

## Usage Guidelines

- Upon launching the application, the login frame will be displayed. Enter your username and password to log in.
- After a successful login, the dashboard will show employee data and attendance records.
- Ensure that the database is properly configured in `DatabaseUtil.java` for data retrieval.

## Dependencies

This project uses Maven for dependency management. The required dependencies are specified in the `pom.xml` file.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.