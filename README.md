# Zeylan ResQ - Disaster Management System 🚨🌍

Zeylan ResQ is a disaster management system designed to streamline the reporting and tracking of disasters. It allows citizens to submit reports, while administrators can manage responses and monitor incidents efficiently.

## Features 🛠️

- **Report Submission**: Citizens can submit disaster reports with details like location, disaster type, urgency level, and impact.
- **Incident Filtering**: View disaster reports based on Province, District, Disaster Type, and Urgency Level.
- **Incident Tracking**: Track and update the status of reports (Pending, In Progress, Resolved).
- **Admin Dashboard**: Manage reports and respond to disaster incidents effectively.
- **User-Friendly UI**: Built with JavaFX for an interactive experience.

## Technologies Used 💻

- **Java (JavaFX)** - Frontend application
- **MySQL** - Database for storing reports and user information
- **JDBC** - Database connectivity

## Setup Instructions 🚀

### Prerequisites
- Java Development Kit (JDK) installed (Java 11 or higher recommended)
- MySQL installed and running
- JavaFX SDK (if not included in your JDK)

### Installation Steps

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/thulanikalatuwawa/Disaster-Coordination-System.git
   ```
2. **Set Up Database**:
   - Create a MySQL database named `zeylan_resq`.
   - Import the provided SQL schema and data into the database.
3. **Update Database Connection**:
   - Open `ViewReportsC.java` and update the database connection URL, username, and password:
     ```java
     Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeylan_resq", "root", "");
     ```
4. **Run the Application**:
   - Use an IDE like IntelliJ IDEA or Eclipse to build and run the JavaFX application.

## Usage 📌

- **Citizens**: Submit disaster reports with relevant details.
- **Administrators**: Filter reports, update statuses, and manage disaster response.

## Troubleshooting 🛠️

- If JavaFX is missing, ensure you have added the JavaFX libraries to your classpath.
- Check MySQL connection settings if the application fails to fetch data.
- Ensure all dependencies are correctly installed.

## Contribution 🤝

Contributions are welcome! Feel free to fork the repository, submit issues, or create pull requests.

## License 📜

This project is licensed under the MIT License.

---

