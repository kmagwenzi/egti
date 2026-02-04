# EGTI Project Documentation

## 1. Brief Description

The **EGTI (Emmanuel Global Theological Institute)** project is a web application for a theological institute that provides online course catalogues, program information, and an application system for prospective students. The project consists of a frontend website built with HTML/CSS/JavaScript and a backend Java Spring Boot application that handles application submissions, file uploads, and data management.

### Key Features:
- **Frontend**: Static pages (Home, About Us, Programs, Fees, Apply) with responsive design using custom CSS and Bootstrap.
- **Backend**: Spring Boot REST API with Thymeleaf templating, MySQL database integration, and file upload capabilities.
- **Application Management**: Prospective students can submit applications with personal details, program selection, and profile image upload.
- **Admin Interface**: View submitted applications with pagination, detailed views, and update functionality.
- **Security**: Basic HTTP authentication with Spring Security.

### Technology Stack:
- **Backend**: Java 21, Spring Boot 3.0.6, Spring Data JPA, Spring Security, Thymeleaf, Lombok.
- **Database**: MySQL with Hibernate ORM.
- **Frontend**: HTML5, CSS3, JavaScript, jQuery, Bootstrap 5.3.2.
- **Build Tool**: Apache Maven.
- **Server**: Embedded Tomcat (port 4040).

---

## 2. Project File Hierarchy and Structure

```
/run/media/kmagwenzi/Archive/Graft/The Engineer/Graphic Design/Projects/Emmanuel Global/Emmanuel Global Theological Institute/Website/Exports/EGTI
├── assets/                       # Static assets (favicons, images)
│   ├── favicon/
│   │   ├── android-chrome-192x192.png
│   │   ├── android-chrome-512x512.png
│   │   ├── apple-touch-icon.png
│   │   ├── favicon-16x16.png
│   │   ├── favicon-32x32.png
│   │   ├── favicon-256x256.png
│   │   ├── favicon.ico
│   │   └── site.webmanifest
│   └── images/
│       ├── ECGI Institute Logo.png
│       └── (other image assets)
├── backend/                      # Spring Boot backend project
│   └── EGTI/
│       ├── .gitignore
│       ├── .sdkmanrc
│       ├── HELP.md
│       ├── README.md
│       ├── nbactions.xml
│       ├── pom.xml               # Maven project configuration
│       ├── sql/
│       │   └── init.sql          # Database initialization script
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/zw/co/egti/
│       │   │   │   ├── EgtiApplication.java        # Spring Boot entry point
│       │   │   │   ├── controllers/
│       │   │   │   │   ├── ApplicationController.java  # Handles application CRUD
│       │   │   │   │   └── StaticController.java       # Serves static pages
│       │   │   │   ├── entities/
│       │   │   │   │   ├── Application.java            # JPA entity for applications
│       │   │   │   │   └── enums/
│       │   │   │   │       ├── Gender.java
│       │   │   │   │       ├── MaritalStatus.java
│       │   │   │   │       ├── Program.java
│       │   │   │   │       └── Sponsorship.java
│       │   │   │   ├── repositories/
│       │   │   │   │   └── ApplicationRepository.java   # Spring Data repository
│       │   │   │   ├── security/
│       │   │   │   │   └── SecurityConfiguration.java   # Spring Security config
│       │   │   │   ├── services/
│       │   │   │   │   ├── ApplicationService.java      # Service interface
│       │   │   │   │   └── ApplicationsServiceImplementation.java
│       │   │   │   └── utilities/
│       │   │   │       └── ImageUtility.java            # Image processing helper
│       │   │   └── resources/
│       │   │       ├── application.properties           # Spring configuration
│       │   │       ├── static/
│       │   │       │   ├── bootstrap/                   # Bootstrap distribution
│       │   │       │   ├── images/                      # Backend‑specific images
│       │   │       │   ├── scripts/                     # JavaScript files
│       │   │       │   │   ├── about-us.js
│       │   │       │   │   ├── apply.js
│       │   │       │   │   ├── confirm-delete.js
│       │   │       │   │   ├── fees.js
│       │   │       │   │   ├── index.js
│       │   │       │   │   ├── jquery.js
│       │   │       │   │   ├── programs.js
│       │   │       │   │   └── sweet-alert.js
│       │   │       │   ├── styles/                      # CSS stylesheets
│       │   │       │   │   ├── about-us.css
│       │   │       │   │   ├── apply.css
│       │   │       │   │   ├── bootstrap.min.css
│       │   │       │   │   ├── fees.css
│       │   │       │   │   ├── index.css
│       │   │       │   │   └── programs.css
│       │   │       │   └── uploads/                     # Uploaded profile images
│       │   │       │       └── profile-image/
│       │   │       └── templates/                       # Thymeleaf HTML templates
│       │   │           ├── about-us.html
│       │   │           ├── applicant-detailed-view.html
│       │   │           ├── applications.html
│       │   │           ├── apply.html
│       │   │           ├── fees.html
│       │   │           ├── index.html
│       │   │           ├── paging.html                  # Pagination fragment
│       │   │           ├── programs.html
│       │   │           ├── update-applicant.html
│       │   │           ├── view-applications.html
│       │   │           └── fragments/
│       │   │               └── layouts.html
│       │   └── test/                                    # Unit tests
│       │       └── java/zw/co/egti/
│       │           ├── EgtiApplicationTests.java
│       │           └── egtu/                            # (possibly misnamed)
│       └── target/                                      # Compiled output (generated)
├── Mock Up/                     # Website mockup images
├── scripts/                      # Root-level JavaScript files (if any)
└── styles/                       # Root-level CSS files (if any)
```

---

## 3. File Index with Short Descriptions

### Frontend Pages (Thymeleaf Templates)
The frontend is served via Thymeleaf templates located in `backend/EGTI/src/main/resources/templates/`. These templates are rendered by the Spring Boot application.

| File | Description |
|------|-------------|
| `index.html` | Home page of the institute, showcasing mission, vision, and course catalogue. |
| `about-us.html` | “About Us” page with institute history, leadership, and values. |
| `applications.html` | Admin page for viewing submitted applications (requires authentication). |
| `apply.html` | Application form for prospective students. |
| `fees.html` | Tuition and fee structure page. |
| `programs.html` | Overview of academic programs (degree, diploma, masters, PhD). |

### Assets
- `assets/favicon/` – Favicon files in various sizes for different platforms.
- `assets/images/` – Logo and other static images used across the site.

### Backend (Spring Boot)
#### Configuration & Build
- `pom.xml` – Maven project file defining dependencies (Spring Boot, Thymeleaf, MySQL, Lombok, etc.).
- `application.properties` – Spring configuration: database connection, server port, multipart file settings, security credentials.
- `.sdkmanrc` – SDKMAN runtime configuration.
- `nbactions.xml` – NetBeans IDE actions.

#### Source Code
- `EgtiApplication.java` – Main Spring Boot application class; creates upload directory on startup.
- **Controllers**:
  - `ApplicationController.java` – REST controller for application submission, file upload, and CRUD operations.
  - `StaticController.java` – Serves static Thymeleaf templates (pages).
- **Entities**:
  - `Application.java` – JPA entity representing a student application with fields for personal details, program, sponsorship, etc.
  - `enums/` – Enumeration types for gender, marital status, program, and sponsorship.
- **Repositories**:
  - `ApplicationRepository.java` – Spring Data JPA repository for `Application` entities.
- **Services**:
  - `ApplicationService.java` & `ApplicationsServiceImplementation.java` – Business logic for application persistence.
- **Security**:
  - `SecurityConfiguration.java` – Configures HTTP basic authentication.
- **Utilities**:
  - `ImageUtility.java` – Helper for image processing (resizing, etc.).

#### Resources
- `static/` – Frontend assets served by Spring Boot.
  - `bootstrap/` – Bootstrap 5.3.2 distribution (CSS & JS).
  - `images/` – Header images, thumbnails, icons used in templates.
  - `scripts/` – JavaScript files for each page (form validation, interactivity).
  - `styles/` – Page‑specific CSS styles.
  - `uploads/profile-image/` – Directory where uploaded profile pictures are stored.
- `templates/` – Thymeleaf HTML templates (mirroring root HTML pages but with dynamic features).
  - `fragments/layouts.html` – Reusable layout fragment.

#### Database
- `sql/init.sql` – SQL script for initial database schema (if needed).

#### Tests
- `EgtiApplicationTests.java` – Basic Spring Boot test.
- `egtu/` – Possibly a misnamed test package.

---

## 4. Summary

The EGTI project is a full‑stack web application that serves both a public‑facing website and an administrative backend. The frontend provides information about the institute and its programs, while the backend handles application processing with a secure, database‑driven workflow. The codebase is organized following standard Spring Boot conventions, with clear separation of concerns between controllers, services, entities, and repositories. The use of modern Java (21), Spring Boot 3, and a responsive Bootstrap frontend makes the project maintainable and extensible.

*Document generated on 2026‑02‑03.*