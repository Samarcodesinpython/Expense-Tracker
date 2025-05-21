# Expense Tracker

## Description

ExpensAI is a desktop expense tracking application built with JavaFX. It allows users to track their expenses, categorize them, and visualize spending through charts. Key features include:

- User authentication (Login/Registration)
- Expense recording with category, date, amount, description, and optional receipt photo.
- Category management.
- Dashboard overview with spending charts.
- Receipt scanning using Tesseract OCR.
- PDF report generation using iText (placeholder).
- Dark and Light theme support.

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 17 or later
*   Apache Maven

### Building and Running

1.  Clone the repository:

    ```bash
    git clone <repository_url>
    cd Expense-Tracker
    ```

2.  Build the project using Maven:

    ```bash
    mvn clean package
    ```

3.  Run the application:

    ```bash
    mvn javafx:run
    ```

    Alternatively, you can run the packaged JAR file located in the `target` directory:

    ```bash
    java -jar target/expensai-1.0-SNAPSHOT.jar
    ```

### Default User

The application comes with a default user for testing:

*   **Username:** `demo`
*   **Password:** `password`

## Technologies Used

*   JavaFX
*   SQLite (Database)
*   Maven (Build Tool)
*   Tess4J (Tesseract OCR Library)
*   iText (PDF Library)

## Project Structure

(Based on Project Structure.txt)

```
src/
├── main/
│   ├── java/                     // Java source files
│   │   └── com/expensai/
│   │       ├── ExpensAIApplication.java       // Main application class
│   │       ├── controller/                    // UI controllers
│   │       ├── model/                         // Data models
│   │       ├── service/                       // Business logic (DatabaseService, etc.)
│   │       └── dao/                           // Data Access Objects
│   └── resources/                // Resource files (FXML, CSS, images)
│       ├── com/expensai/view/                     // FXML files
│       ├── css/                             // CSS stylesheets
│       └── images/                          // Images
└── test/                         // Unit tests
```

## Future Enhancements (Ideas)

*   Advanced reporting and export options (CSV, PDF).
*   Income tracking.
*   Recurring transactions.
*   More detailed budgeting features.
*   Improved receipt scanning accuracy.
*   Cloud synchronization.

## Contributing

(Placeholder for contribution guidelines if this were an open-source project)

Feel free to fork this repository and contribute! 