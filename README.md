# Spring-boot-email-automation-system
A robust Spring Boot application that automates sending personalized emails with dynamically generated PDF attachments. It processes employee data from a CSV file, creating either promotion letters or appraisal letters based on the 'letterType' field, and then emails the relevant PDF to each employee.

Here's a GitHub description and a README file for your Spring Boot Email System project:

GitHub Description Spring Boot Email System with Dynamic PDF Generation and CSV Processing

A robust Spring Boot application that automates sending personalized emails with dynamically generated PDF attachments. It processes employee data from a CSV file, creating either promotion letters or appraisal letters based on the 'letterType' field, and then emails the relevant PDF to each employee.

README.md Spring Boot Email System with Dynamic PDF and CSV This project is a Spring Boot application designed to automate the process of sending personalized emails with dynamically generated PDF attachments to employees. It reads employee data from a CSV file, generates either a Promotion Letter or an Appraisal Letter based on the letterType specified in the CSV, and then emails the respective PDF to each employee.

Features CSV Data Ingestion: Upload an employee CSV file to process multiple email sends.

Dynamic PDF Generation: Generates personalized PDF documents (Promotion Letters or Appraisal Letters) on the fly for each employee.

Email Automation: Sends emails with the generated PDF as an attachment using Jakarta Mail.

Thymeleaf Integration: Provides a simple and intuitive web interface for CSV file uploads.

Robust Error Handling: Includes basic error handling for file processing and email sending.

Technologies Used Spring Boot: Framework for building the application.

Thymeleaf: Server-side Java template engine for web interfaces.

Apache Commons CSV: For parsing CSV files efficiently.

iTextPDF: For creating and manipulating PDF documents.

Jakarta Mail (com.sun.mail:jakarta.mail): For sending emails.

Maven: Dependency management and build automation tool.

Java 17: Programming language version.

Getting Started These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites Java 17 or higher

Maven

An IDE (e.g., IntelliJ IDEA, VS Code, Eclipse)

A Gmail account with App Passwords enabled for sending emails. (Refer to the "Gmail App Password Setup" section below.)

Installation Clone the repository:

Bash

git clone https://github.com/your-username/emailsystem.git cd emailsystem Configure Gmail App Password:

Go to your Google Account.

Navigate to Security.

Under "How you sign in to Google," select 2-Step Verification and ensure it's ON.

Once 2-Step Verification is enabled, an App passwords option will appear below it. Click on it.

Generate a new app password. This 16-character password is what you will use in your application, not your regular Gmail password.

Update EmailService: Open src/main/java/com/example/emailsystem/service/EmailService.java and update the from email and password with your Gmail address and the 16-character App Password you generated:

Java

@Service public class EmailService { public void sendEmailWithAttachment(Employee emp, byte[] pdfBytes) { final String from = "your_gmail_address@gmail.com"; // Your Gmail final String password = "your_16_char_app_password"; // 16-char App Password // ... rest of the code } } Build the project:

Bash

mvn clean install Run the application:

Bash

mvn spring-boot:run The application will start on http://localhost:8080.

Usage Open your web browser and navigate to http://localhost:8080.

You will see an "Upload Employee CSV" form.

Click "Choose File" and select your CSV file.

Click "Upload and Send Emails".

CSV File Format Your CSV file should have the following headers (case-insensitive and leading/trailing spaces will be trimmed):

name

email

department

doj (Date of Joining)

newDesignation (Required for promotion letters)

newSalary (Required for appraisal letters)

effectiveDate

letterType (Accepts "promotion", "appraisal", or "increment". "increment" will be normalized to "appraisal".)

Example CSV: Code snippet

name,email,department,doj,newDesignation,newSalary,effectiveDate,letterType John Doe,john.doe@example.com,Engineering,2020-01-15,Senior Engineer,,2025-08-01,promotion Jane Smith,jane.smith@example.com,Marketing,2021-03-10,,65000,2025-08-15,appraisal Peter Jones,peter.jones@example.com,HR,2019-06-20,,72000,2025-09-01,increment Project Structure ├── src │ ├── main │ │ ├── java │ │ │ └── com │ │ │ └── example │ │ │ └── emailsystem │ │ │ ├── Application.java # Main Spring Boot application entry point │ │ │ ├── controller │ │ │ │ └── UploadController.java # Handles web requests (upload, home page) │ │ │ ├── model │ │ │ │ └── Employee.java # Employee data model │ │ │ └── service │ │ │ ├── CSVProcessor.java # Utility for parsing CSV files │ │ │ ├── EmailService.java # Handles sending emails │ │ │ └── PDFGenerator.java # Utility for generating PDF documents │ │ └── resources │ │ ├── static # Static assets (CSS, JS, images - though not used much here) │ │ └── templates │ │ └── upload.html # Thymeleaf template for the upload form │ └── test │ └── java │ └── com │ └── example │ └── emailsystem │ └── ApplicationTests.java # Spring Boot tests └── pom.xml # Maven project object mod
