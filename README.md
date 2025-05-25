# Smart Contact Manager

A comprehensive contact management system built with Spring Boot that provides users with a secure and efficient way to manage their contacts across multiple devices.

## 🌟 Features

### Core Functionality
- **CRUD Operations**: Complete Create, Read, Update, and Delete operations for user accounts and contacts
- **Contact Management**: View, update, delete, search, and filter contacts by name, email, or phone number
- **Cloud Storage**: Upload and retrieve contact profile pictures using cloud-based storage services
- **Responsive Design**: Optimized user experience across desktop and mobile devices

### Authentication & Security
- **OAuth2 Integration**: Sign in with Google or GitHub accounts for seamless authentication
- **Spring Security**: Comprehensive security framework protecting application resources
- **Email Verification**: Mandatory email verification before accessing full application functionality
- **Form Validation**: Input validation with helpful error messages and guidance

### User Experience
- **Light/Dark Mode**: Toggle between light and dark themes based on user preference
- **Responsive UI**: Built with Tailwind CSS for consistent experience across all screen sizes
- **Cross-Platform Access**: Access contacts from both mobile devices and desktop computers

## 🛠️ Technical Stack

### Backend
- **Java** - Primary programming language
- **Spring Boot** - Core application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA with Hibernate** - Object-Relational Mapping
- **MySQL** - Database management system

### Frontend
- **Thymeleaf** - Server-side template engine
- **HTML/CSS/JavaScript** - Standard web technologies
- **Tailwind CSS** - Utility-first CSS framework for responsive design

### Authentication
- **OAuth2** - Protocol for Google and GitHub integration

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- MySQL database
- Maven (for dependency management)
- Google OAuth2 credentials (for Google login)
- GitHub OAuth2 credentials (for GitHub login)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Cubanx00td/smartcontactmanager.git
   cd smartcontactmanager
   ```

2. **Configure Database**
   - Create a MySQL database for the application
   - Update `application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

3. **Configure OAuth2 Providers**
   - Set up Google OAuth2 credentials in Google Cloud Console
   - Set up GitHub OAuth2 app in GitHub Developer Settings
   - Add credentials to `application.properties`:
     ```properties
     spring.security.oauth2.client.registration.google.client-id=your_google_client_id
     spring.security.oauth2.client.registration.google.client-secret=your_google_client_secret
     
     spring.security.oauth2.client.registration.github.client-id=your_github_client_id
     spring.security.oauth2.client.registration.github.client-secret=your_github_client_secret
     ```

4. **Configure Email Service**
   - Set up email configuration for verification emails:
     ```properties
     spring.mail.host=your_smtp_host
     spring.mail.port=587
     spring.mail.username=your_email
     spring.mail.password=your_email_password
     ```

5. **Configure Cloud Storage**
   - Set up your preferred cloud storage service (AWS S3, Google Cloud Storage, etc.)
   - Add necessary configuration properties

6. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

7. **Access the Application**
   - Open your browser and navigate to `http://localhost:8080`

## 📱 Usage

### User Registration & Authentication
1. Register a new account or sign in with existing credentials
2. Alternatively, use Google or GitHub OAuth2 for quick authentication
3. Verify your email address to unlock full functionality

### Contact Management
1. **Add Contacts**: Create new contacts with detailed information
2. **Upload Images**: Add profile pictures to your contacts
3. **Search & Filter**: Find contacts quickly using various search criteria
4. **Update Information**: Edit contact details as needed
5. **Organize**: Manage your contact list efficiently

### Customization
- Toggle between light and dark modes in settings
- Access your contacts from any device with responsive design

## 🏗️ Project Structure

```
SCM/
├── .mvn/
│   └── wrapper/
├── .vscode/
├── node_modules/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/scm/scm/
│   │   │       ├── config/         # Configuration classes
│   │   │       ├── controllers/    # REST controllers
│   │   │       ├── entities/       # JPA entities
│   │   │       ├── forms/          # Form handling classes
│   │   │       ├── helper/         # Utility classes
│   │   │       ├── repositories/   # Data access layer
│   │   │       ├── services/       # Business logic
│   │   │       └── ScmApplication.java
│   │   └── resources/
│   └── test/                       # Unit and integration tests
├── target/
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── package-lock.json
├── package.json
├── pom.xml                         # Maven dependencies
└── tailwind.config.js
```

## 🔒 Security Features

- **Spring Security**: Comprehensive security framework
- **OAuth2 Integration**: Secure third-party authentication
- **Email Verification**: Mandatory email verification process
- **Input Validation**: Protection against malicious inputs
- **Session Management**: Secure session handling

## 📄 License

This project is open source
