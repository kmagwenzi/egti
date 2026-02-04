# Emmanuel Global Theological Institute (EGTI) – Website & Application System

## Overview

This project is a full‑stack web application for the **Emmanuel Global Theological Institute (EGTI)**. It provides a public‑facing website with information about the institute, its academic programs, tuition fees, and an online application system for prospective students. The backend is built with Spring Boot and manages application submissions, file uploads, and an administrative interface.

## Screenshots

| | |
|:---:|:---:|
| **Home Page** | **Programs Page** |
| ![Home Page](Mock%20Up/Home.jpg) | ![Programs Page](Mock%20Up/Programs.jpg) |
| **Fees Page** | **Profile Page** |
| ![Fees Page](Mock%20Up/Fees.jpg) | ![Profile Page](Mock%20Up/Profile.jpg) |

## Features

### Frontend
- **Responsive static pages** (Home, About Us, Programs, Fees, Apply) built with HTML5, CSS3, and JavaScript.
- **Bootstrap 5.3.2** for consistent styling and mobile‑first design.
- **Interactive forms** with client‑side validation.

### Backend
- **Spring Boot 3.0.6** REST API with Thymeleaf templating.
- **PostgreSQL database** integration via Spring Data JPA (Hibernate).
- **Application CRUD** – create, read, update, delete student applications.
- **File upload** for profile images (stored in `uploads/profile-image/`).
- **Pagination** for viewing submitted applications.
- **Basic HTTP authentication** with Spring Security.
- **Automatic creation of upload directories** on startup.

### Admin Interface
- View all applications with pagination.
- Detailed applicant view.
- Update applicant information.
- Delete applications (with confirmation).

## Technology Stack

| Layer        | Technologies |
|--------------|--------------|
| **Backend**  | Java 21, Spring Boot 3.0.6, Spring Data JPA, Spring Security, Thymeleaf, Lombok |
| **Database** | PostgreSQL (or compatible), Hibernate ORM |
| **Frontend** | HTML5, CSS3, JavaScript, jQuery, Bootstrap 5.3.2 |
| **Build Tool**| Apache Maven |
| **Server**   | Embedded Tomcat (port 8080, configurable via `PORT` environment variable) |

## Project Structure

```
EGTI/
├── assets/                          # Favicons and static images
├── backend/EGTI/                    # Spring Boot project
│   ├── pom.xml                      # Maven configuration
│   ├── src/main/java/zw/co/egti/
│   │   ├── EgtiApplication.java     # Main application class
│   │   ├── controllers/             # ApplicationController, StaticController
│   │   ├── entities/                # JPA entities (Application, enums)
│   │   ├── repositories/            # Spring Data repositories
│   │   ├── services/                # Business logic
│   │   ├── security/                # Spring Security config
│   │   └── utilities/               # Helper classes
│   ├── src/main/resources/
│   │   ├── application.properties   # Database, server, security settings
│   │   ├── static/                  CSS, JS, images, Bootstrap, uploads
│   │   └── templates/               # Thymeleaf HTML templates
│   └── sql/init.sql                 # Database initialization script
├── Mock Up/                         # Website mockup images
├── scripts/                         # Root‑level JavaScript (if any)
└── styles/                          # Root‑level CSS (if any)
```

For a complete file‑by‑file description, see [`EGTI_PROJECT_DOCUMENTATION.md`](EGTI_PROJECT_DOCUMENTATION.md).

## Setup & Installation

### Prerequisites

- **Java 21** (or later) – [Install OpenJDK 21](https://openjdk.org/projects/jdk/21/)
- **PostgreSQL 14+** (or compatible) – [Download PostgreSQL](https://www.postgresql.org/download/) (or use a cloud database like Railway)
- **Maven 3.8+** – [Install Maven](https://maven.apache.org/install.html)
- A modern web browser (Chrome, Firefox, Edge)

### Database Configuration

The application is configured for **PostgreSQL** and uses environment variables for database connection. You can set the following variables:

- `DATABASE_URL`: JDBC URL (e.g., `jdbc:postgresql://localhost:5432/egti`)
- `DB_USERNAME`: Database username (default `postgres`)
- `DB_PASSWORD`: Database password

Alternatively, you can edit `backend/EGTI/src/main/resources/application.properties` directly.

The application will automatically create the necessary tables on startup (`spring.jpa.hibernate.ddl-auto=update`).

### Building the Backend

Navigate to the Spring Boot project directory and run Maven:

```bash
cd backend/EGTI
mvn clean package
```

The compiled JAR will be generated in `target/egti-0.0.1-SNAPSHOT.jar`.

## Running the Application

### Option 1: Run with Maven (Development)
```bash
cd backend/EGTI
mvn spring-boot:run
```

### Option 2: Run the JAR (Production)
```bash
java -jar target/egti-0.0.1-SNAPSHOT.jar
```

The application will start on **port 8080** (configurable via the `PORT` environment variable).

### Access the Website

- **Public website**: [http://localhost:8080](http://localhost:8080)
- **Application form**: [http://localhost:8080/apply](http://localhost:8080/apply)
- **Admin interface** (requires authentication): [http://localhost:8080/applications](http://localhost:8080/applications)

Admin credentials are configured via environment variables (defaults shown):

- `ADMIN_USERNAME` (default: `admin`)
- `ADMIN_PASSWORD` (default: `changeme`)

Alternatively, you can set them directly in `application.properties`.

## Deployment

This project is configured for deployment on **Railway** (or any platform that supports Java/PostgreSQL). For detailed deployment instructions, see [`UPLOAD_TO_RAILWAY.md`](UPLOAD_TO_RAILWAY.md).

Key deployment notes:
- The application uses environment variables for database configuration (see [Database Configuration](#database-configuration)).
- The default port is 8080, but Railway will inject a `PORT` environment variable.
- A PostgreSQL database is required; Railway provides one automatically.

## Usage

### Submitting an Application
1. Navigate to the **Apply** page.
2. Fill in personal details, select a program, and upload a profile picture.
3. Click **Submit**. The application will be saved in the database and the image stored in `uploads/profile-image/`.

### Viewing Applications (Admin)
1. Log in with the credentials above.
2. The **Applications** page displays all submissions with pagination.
3. Click any applicant to see a detailed view.
4. Use the **Edit** or **Delete** buttons to modify records.

### Static Pages
The frontend is served via Thymeleaf templates located in `backend/EGTI/src/main/resources/templates/`. These templates are rendered by the Spring Boot application and provide dynamic content. The static assets (CSS, JavaScript, images) are served from the `static/` directory.

## Contributing

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your‑feature`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your‑feature`).
5. Open a Pull Request.

Please ensure your code follows the existing style and includes appropriate tests.

## Uploading to GitHub

This project is already set up with a local Git repository. To upload it to your GitHub account:

1. **Create a new repository** on GitHub:
   - Go to [github.com/new](https://github.com/new).
   - Choose a name (e.g., `EGTI`), keep it public or private, and **do not** initialize with a README, .gitignore, or license.

2. **Add the remote** (replace `kmagwenzi` with your GitHub username if different):
   ```bash
   git remote add origin https://github.com/kmagwenzi/EGTI.git
   ```

3. **Push the code**:
   ```bash
   git push -u origin main
   ```

4. **Verify** that the repository appears at `https://github.com/kmagwenzi/EGTI`.

After pushing, you can update the remote URL in the future with `git remote set-url origin <new-url>`.

## License

This project is proprietary and intended for use by the Emmanuel Global Theological Institute. All rights reserved.

---

*Documentation generated on 2026‑02‑03.*