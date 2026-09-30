# WebGoat - Security Remediations & DevSecOps CI/CD Pipelines

## 📌 Project Overview

This repository contains the source code, security vulnerability fixes, unit test coverage, and automated DevSecOps security pipelines for **WebGoat** (OWASP).

The project addresses core web application vulnerabilities through defense-in-depth secure coding practices and implements a four-stage automated security scanning pipeline integrated with GitHub Actions.

---

## 🛡️ Security Vulnerabilities Handled & Mitigations

### 1. SQL Injection (SQLi)
- **Mitigation Approach**: Replaced dynamic SQL string concatenation with secure `PreparedStatement` parameterized queries.
- **Affected Lessons Remediated**:
  - `SqlInjectionLesson6b`: Parameterized user system data and password lookup queries.
  - `SqlInjectionLesson8`: Sanitized audit logging queries.
  - `SqlInjectionLesson9`: Parameterized employee salary search queries.
  - `SqlInjectionLesson10`: Converted dynamic table existence checks to parameterized statements.
  - `SqlInjectionChallenge`: Implemented parameterized SQL execution for user verification.

### 2. Cross-Site Request Forgery (CSRF)
- **Mitigation Approach**: Implemented anti-CSRF token verification and strict origin/header validation.
- **Affected Modules Remediated**:
  - `CSRFFeedback`: Enforced same-origin and custom security header verification.
  - `Forged Reviews`: Added server-side validation for anti-CSRF tokens on review submission endpoints.
  - **Unit Testing**: Added test coverage verifying rejection of forged requests without valid tokens.

### 3. Insecure Direct Object References (IDOR) & Broken Access Control
- **Mitigation Approach**: Implemented server-side identity checks and resource ownership validation before granting access or modification privileges.
- **Affected Modules Remediated**:
  - `UserProfile`: Added ownership verification method to ensure requests belong to the authenticated user.
  - `IDORViewOtherProfile`: Restricted profile viewing to the authenticated session owner.
  - **Unit Testing**: Added automated unit tests to verify access control enforcement.

### 4. Cross-Site Scripting (XSS) - Reflected & Stored
- **Mitigation Approach**: Integrated **OWASP Java HTML Encoder** (`org.owasp.encoder:encoder`) to perform context-aware output encoding on untrusted user inputs.
- **Affected Lessons Remediated**:
  - **Reflected XSS (Lesson 5a)**: Encoded user input fields (e.g., credit card input) before rendering in responses.
  - **Stored XSS (`StoredXssComments`)**: Sanitized and HTML-encoded stored comments prior to persistence and rendering.
  - **Unit Testing**: Added test suite validating HTML entity encoding against script execution payloads.

---

## 🚀 DevSecOps CI/CD Security Pipelines

The repository features 4 automated security workflows under `.github/workflows/`:

| Pipeline | Workflow File | Tools & Scope | Trigger Events |
| :--- | :--- | :--- | :--- |
| **SAST** | [`.github/workflows/sast.yml`](.github/workflows/sast.yml) | **GitHub CodeQL** (Java/Kotlin, JS/TS) & **Semgrep** (OWASP Top 10, CWE Top 25) | Push, PR, Weekly Schedule, Manual |
| **Dependency / SCA** | [`.github/workflows/dependency-scanning.yml`](.github/workflows/dependency-scanning.yml) | **OWASP Dependency-Check**, **Google OSV-Scanner**, **GitHub Dependency Review** | Push, PR, Weekly Schedule, Manual |
| **Secret Scanning** | [`.github/workflows/gitleaks.yml`](.github/workflows/gitleaks.yml) | **Gitleaks** full git history scan for hardcoded credentials & API keys | Push, PR, Manual |
| **Vulnerability & Container** | [`.github/workflows/trivy.yml`](.github/workflows/trivy.yml) | **Aqua Security Trivy** for Filesystem, IaC / Dockerfile misconfiguration, & Container image scanning | Push, PR, Weekly Schedule, Manual |

---

## 👥 Contributors & Work Allocation

| Student ID | Email Address | Vulnerability / Responsibilities Handled |
| :--- | :--- | :--- |
| **`it23158818`** | `it23158818@my.sliit.lk` | **SQL Injection (SQLi)** remediation, dependency configuration, and base setup |
| **`IT-24100603`** | `it24100603@my.sliit.lk` | **Cross-Site Request Forgery (CSRF)** protection & unit test suite |
| **`IT24102386`** | `it24102386@my.sliit.lk` | **IDOR / Broken Access Control** resource ownership validation & tests |
| **`IT24103027`** | `it24103027@my.sliit.lk` | **Cross-Site Scripting (XSS)** mitigation, unit tests, and **DevSecOps CI/CD Pipelines** |

---

## 🛠️ Build and Execution Instructions

### Prerequisites
- **Java 25** (OpenJDK / Eclipse Temurin)
- **Maven 3.9+** (or included Maven Wrapper `./mvnw`)
- **Docker** (optional, for containerized run)

### Building the Project
```bash
# Clone the repository
git clone https://github.com/WebGoat/WebGoat.git
cd WebGoat

# Build with Maven (skipping tests for fast build)
./mvnw clean package -DskipTests

# Run unit tests
./mvnw test
```

### Running WebGoat
```bash
# Start WebGoat via Spring Boot
./mvnw spring-boot:run
```
Once started, access WebGoat at: `http://localhost:8080/WebGoat`
