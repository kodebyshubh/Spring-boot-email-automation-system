📧 Spring Boot Email Automation System

Automate sending personalized emails with dynamically generated PDF attachments using Spring Boot.


✨ Features

✅ CSV Data Ingestion – Upload employee CSVs to process multiple records 📂
✅ Dynamic PDF Generation – Create Promotion or Appraisal letters 📄
✅ Email Automation – Sends customized emails with PDF attachments 📧
✅ Thymeleaf UI – Simple web form for uploading CSV 🌐
✅ Error Handling – Robust handling for file parsing & email sending ⚡

🛠️ Tech Stack

Spring Boot – Backend framework 🌱

Thymeleaf – Server-side template engine 🎨

Apache Commons CSV – Parse CSV efficiently 📊

iTextPDF – Generate dynamic PDFs 📝

Jakarta Mail – Email service integration ✉️

Maven – Dependency management ⚙️

Java 17 – Core language ☕

🚀 Getting Started
🔧 Prerequisites

Java 17+

Maven

IDE (IntelliJ, VS Code, Eclipse)

Gmail account with App Passwords enabled 🔐

📥 Installation
# Clone repository
git clone https://github.com/your-username/emailsystem.git
cd emailsystem

# Build project
mvn clean install

# Run application
mvn spring-boot:run


📍 The app will start at: http://localhost:8080

📌 Gmail App Password Setup

Go to Google Account → Security 🔒

Enable 2-Step Verification ✅

Navigate to App Passwords 🔑

Generate a 16-character password

Update EmailService.java with your Gmail & App Password

final String from = "your_gmail@gmail.com";
final String password = "your_16_char_app_password";

🎯 Usage

Open browser → http://localhost:8080
 🌐

Upload your employee CSV file 📂

Click Upload & Send Emails 🚀

📊 CSV Format

Your CSV must include these headers:

name,email,department,doj,newDesignation,newSalary,effectiveDate,letterType


📌 Example:

John Doe,john.doe@example.com,Engineering,2020-01-15,Senior Engineer,,2025-08-01,promotion
Jane Smith,jane.smith@example.com,Marketing,2021-03-10,,65000,2025-08-15,appraisal
Peter Jones,peter.jones@example.com,HR,2019-06-20,,72000,2025-09-01,increment

📂 Project Structure
├── src
│   ├── main
│   │   ├── java/com/example/emailsystem
│   │   │   ├── Application.java        # Main entry point
│   │   │   ├── controller/UploadController.java
│   │   │   ├── model/Employee.java
│   │   │   └── service/
│   │   │       ├── CSVProcessor.java   # Parse CSV
│   │   │       ├── EmailService.java   # Send emails
│   │   │       └── PDFGenerator.java   # Generate PDFs
│   │   └── resources/templates/upload.html
│   └── test/java/com/example/emailsystem/ApplicationTests.java
└── pom.xml

📸 Screenshots

✨ Upload CSV Page

✨ Generated PDF Example

🤝 Contributing

Pull requests are welcome! 🚀 If you’d like to contribute, fork the repo & submit a PR.

📜 License

📝 This project is licensed under the MIT License.
