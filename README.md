# Nova Online Examination System

A Java-based web application for conducting online examinations, built with JSP/Servlets, MySQL, and Apache Tomcat.

## Prerequisites

Before running this application, ensure you have the following installed:

1. **Java Development Kit (JDK) 17** or higher
   - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/#java17) or [OpenJDK](https://openjdk.org/)
   - Verify installation: `java -version`

2. **Apache Maven 3.6+**
   - Download from [Maven Downloads](https://maven.apache.org/download.cgi)
   - Verify installation: `mvn -version`

3. **MySQL 8.0+**
   - Download from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/)
   - Make sure MySQL service is running

4. **Apache Tomcat 10.x**
   - Download from [Tomcat 10 Downloads](https://tomcat.apache.org/download-10.cgi)
   - Extract to a directory (e.g., `C:\apache-tomcat-10.x.x`)

## Setup Instructions

### Step 1: Database Setup

1. Start your MySQL server
2. Open MySQL command line or MySQL Workbench
3. Run the database initialization script:

```bash
mysql -u root -p < db_init.sql
```

Or manually execute the SQL commands from `db_init.sql`:
- Creates database `nova_exam`
- Creates tables: `student`, `question`, `result`
- Sets up foreign key relationships

4. **Important**: Add some sample questions to the database:
```sql
USE nova_exam;

INSERT INTO question (question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
  ('What is 2 + 2?', '3', '4', '5', '6', 'B'),
  ('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Madrid', 'C'),
  ('Which programming language is this app written in?', 'Python', 'JavaScript', 'Java', 'C++', 'C');
```

### Step 2: Configure Database Connection

Update the database credentials in `src/main/java/com/nova/dao/DBConnection.java` if needed:

```java
private static final String URL = "jdbc:mysql://localhost:3306/nova_exam";
private static final String USERNAME = "root";
private static final String PASSWORD = "8238366756@Milan"; // Change this to your MySQL password
```

### Step 3: Build the Application

1. Open a terminal/command prompt in the project root directory
2. Clean and build the project:

```bash
mvn clean package
```

This will:
- Compile the Java source files
- Package the application as a WAR file
- Create `target/NovaOnlineExamSystem.war`

### Step 4: Deploy to Tomcat

#### Option A: Deploy WAR file (Recommended)

1. Copy the WAR file to Tomcat's `webapps` directory:
   ```bash
   copy target\NovaOnlineExamSystem.war C:\apache-tomcat-10.x.x\webapps\
   ```

2. Start Tomcat:
   ```bash
   C:\apache-tomcat-10.x.x\bin\startup.bat
   ```

3. Tomcat will automatically deploy the WAR file
4. Wait for the deployment to complete (check Tomcat logs)

#### Option B: Deploy as Exploded WAR (For Development)

1. Extract the WAR file or copy the `target/NovaOnlineExamSystem` directory to Tomcat's `webapps` folder:
   ```bash
   xcopy /E /I target\NovaOnlineExamSystem C:\apache-tomcat-10.x.x\webapps\NovaOnlineExamSystem
   ```

2. Start Tomcat:
   ```bash
   C:\apache-tomcat-10.x.x\bin\startup.bat
   ```

#### Option C: Use Maven Tomcat Plugin (Alternative)

Add the Tomcat Maven plugin to `pom.xml` and run:
```bash
mvn tomcat7:run
```
*(Note: This requires configuring the plugin in pom.xml)*

### Step 5: Access the Application

1. Open a web browser
2. Navigate to: `http://localhost:8080/NovaOnlineExamSystem/`
   - Default Tomcat port is 8080
   - If you changed the port, use that port number instead

3. You should see the welcome page with options to:
   - Register a new student account
   - Login with existing credentials

## Application Features

- **User Registration**: Students can create new accounts
- **User Login**: Secure authentication for students
- **Exam Taking**: Students can take online exams
- **Result Display**: View exam results with score and feedback
- **Question Review**: Review correct/incorrect answers after exam

## Project Structure

```
NovaOnlineExamSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/nova/
│   │   │       ├── dao/           # Data Access Objects
│   │   │       ├── model/         # Data Models
│   │   │       └── servlet/       # Servlet Controllers
│   │   └── webapp/
│   │       ├── css/               # Stylesheets
│   │       ├── *.html             # HTML pages
│   │       ├── *.jsp              # JSP pages
│   │       └── WEB-INF/
│   │           └── web.xml        # Servlet configuration
├── db_init.sql                    # Database initialization script
└── pom.xml                        # Maven configuration
```

## Troubleshooting

### Port Already in Use
If port 8080 is already in use:
- Change Tomcat port in `conf/server.xml` (look for `<Connector port="8080"`)
- Or stop the service using port 8080

### Database Connection Error
- Verify MySQL is running: `mysql -u root -p`
- Check database credentials in `DBConnection.java`
- Ensure database `nova_exam` exists and has the correct schema

### ClassNotFoundException: com.mysql.cj.jdbc.Driver
- Verify MySQL connector is in the WAR file's `WEB-INF/lib/` directory
- Rebuild the project: `mvn clean package`

### 404 Error on Pages
- Check if the application deployed successfully in Tomcat logs
- Verify the context path: `http://localhost:8080/NovaOnlineExamSystem/`
- Check `web.xml` for correct servlet mappings

## Development

### Rebuild After Code Changes

1. Make your code changes
2. Rebuild: `mvn clean package`
3. Redeploy the WAR file to Tomcat
4. Restart Tomcat (or let it auto-reload if using exploded WAR)

### View Tomcat Logs

Logs are located in: `C:\apache-tomcat-10.x.x\logs\`
- `catalina.out` - Main Tomcat log
- `localhost.log` - Application-specific log

## Default Configuration

- **Database**: `nova_exam`
- **Database User**: `root`
- **Database Password**: `8238366756@Milan` (change this!)
- **Tomcat Port**: `8080`
- **Application Context**: `/NovaOnlineExamSystem`

## Security Notes

⚠️ **Important**: The database password is hardcoded in the source code. For production:
- Use environment variables or configuration files
- Never commit passwords to version control
- Use a dedicated database user with limited permissions
- Implement password hashing for user passwords (currently stored in plain text)

## License

This project is part of the Nova Online Examination System.

