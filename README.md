# AccessControlList — Group 1
**Moravian University | CSCI 121**
> Determines who is allowed to use the PWMS system and what they have access to.

[![Baseline](https://img.shields.io/badge/Baseline-B1.0-green)]()
[![Tests](https://img.shields.io/badge/Tests-31%2F31%20PASS-brightgreen)]()
[![Java](https://img.shields.io/badge/Java-21-blue)]()

---

## Team

| Role | Name |
|---|---|
| Project Engineer | Riley |
| SCM Manager | Mohammad |
| SQA Manager | Daniel |
| Test Manager | David |

---

## What This Project Does

The `AccessControlList` component manages user access-control records for the
PWMS multi-subsystem application. It stores username, password, subsystem, and
role for each user in a persistent binary file and exposes methods to add,
find, delete, and count users. It uses the **Singleton** design pattern.

This repository contains the complete unit-test deliverable for that component,
produced under formal Configuration Management.

---

## Repository Structure

```
AccessControlListGroup1/
├── README.md                              ← SCM-owned (Mohammad)
├── CONTRIBUTING.md                        ← SCM-owned (Mohammad)
├── .gitignore                             ← SCM-owned (Mohammad)
├── .github/
│   ├── CODEOWNERS                         ← SCM-owned (Mohammad)
│   └── PULL_REQUEST_TEMPLATE.md           ← SCM-owned (Mohammad)
├── Interfaces/
│   └── src/
│       ├── Main.java
│       ├── Presentation/
│       │   ├── AASBoundary.java
│       │   ├── AASConnector.java
│       │   ├── AASInterface.java
│       │   ├── ACSBoundary.java
│       │   ├── ACSConnector.java
│       │   ├── ACSInterface.java
│       │   ├── SubsystemEnums.java
│       │   └── SubsystemRoles.java
│       ├── Transport/
│       │   └── (transport layer classes)
│       ├── Application/
│       │   └── AccessControlList.java     ← Unit under test (v2.0)
│       └── Test/
│           └── AccessControlListTest.java ← Test driver, 31 cases (v1.0)
└── docs/
    ├── TestProcedure.docx                 ← TP-ACL-2025-001 v1.0 (David)
    ├── TestReport.docx                    ← TR-ACL-2025-001 v1.0 (David)
    ├── CMP-ACL-2025-001.docx              ← CM Plan v1.0 (Mohammad)
    ├── BaselineLog.docx                   ← BL-ACL-2025-001 v1.0 (Mohammad)
    └── javadoc/
        └── index.html                     ← Generated API docs (Riley)
```

---

## Quick Start — Compile and Run Tests

### Prerequisites
- Java 21 (OpenJDK or Oracle JDK)
- No external libraries required

### 1. Clone the repository
```bash
git clone https://github.com/your-org/AccessControlListGroup1.git
cd AccessControlListGroup1
```

### 2. Compile
```bash
javac -d out -sourcepath Interfaces/src \
  Interfaces/src/Presentation/SubsystemEnums.java \
  Interfaces/src/Presentation/SubsystemRoles.java \
  Interfaces/src/Application/AccessControlList.java \
  Interfaces/src/Test/AccessControlListTest.java
```

### 3. Run the test suite
```bash
java -cp out Test.AccessControlListTest
```

### 4. Expected output
```
=======================================================
 AccessControlList — Black-Box Unit Test Suite
 Moravian University  CSCI 121  —  SCM Team
=======================================================
...
  TOTAL: 31   PASSED: 31   FAILED: 0
=======================================================
```

### 5. View Javadoc
Open `docs/javadoc/index.html` in any browser.

---

## Deliverables

| Document | ID | Version | Owner |
|---|---|---|---|
| Test Procedure | TP-ACL-2025-001 | 1.0 | David (Test Manager) |
| Test Report | TR-ACL-2025-001 | 1.0 | David (Test Manager) |
| CM Plan | CMP-ACL-2025-001 | 1.0 | Mohammad (SCM Manager) |
| Baseline Log | BL-ACL-2025-001 | 1.0 | Mohammad (SCM Manager) |
| Javadoc | — | 1.0 | Riley (Project Engineer) |

---

## Current Baseline

| Baseline | Git Tag | Date | Status |
|---|---|---|---|
| B1.0 | `B1.0` | April 2025 | **Active** |

---

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for the full GitHub workflow.

> **All changes must go through a Pull Request reviewed by Mohammad (SCM Manager).
> No direct pushes to `main`.**

---

*Repository maintained by Mohammad — SCM Manager | Moravian University CSCI 121*
