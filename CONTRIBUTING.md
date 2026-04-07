# Contributing to AccessControlListGroup1
**GitHub Workflow Guide | SCM Manager: Mohammad | CSCI 121 | Moravian University**

> This document defines the **only** approved way to contribute changes
> to this repository. All team members must follow it without exception.
> Mohammad (SCM Manager) enforces these rules through branch protection
> and CODEOWNERS.

---

## Team Quick Reference

| Role | Name | GitHub Responsibility |
|---|---|---|
| SCM Manager | Mohammad | Reviews ALL PRs, merges to main, owns docs/ and .github/ |
| SQA Manager | Daniel | Reviews PRs for correctness, approves before Mohammad merges |
| Test Manager | David | Authors TestProcedure.docx, TestReport.docx, test driver |
| Project Engineer | Riley | Authors source code, Javadoc |

---

## Golden Rules

```
✅ Always work on a feature branch — NEVER directly on main
✅ Always open a Pull Request to merge into main
✅ Always wait for Daniel (SQA) to approve AND Mohammad (SCM) to approve
✅ Never merge your own Pull Request
✅ Follow the commit message and PR title conventions exactly
✅ Update version numbers when you change a file
```

---

## Step-by-Step Workflow for Every Team Member

### Step 1 — Always start from the latest main

```bash
git checkout main
git pull origin main
```

### Step 2 — Create your feature branch

```bash
git checkout -b feature/your-branch-name
```

Use the naming convention in the table below:

| Who | Task | Branch Name |
|---|---|---|
| Riley | Source code / Javadoc changes | `feature/source-javadoc` |
| David | Test driver | `feature/test-driver` |
| David | Test Procedure document | `feature/docs-procedure` |
| David | Test Report document | `feature/docs-report` |
| Mohammad | CM Plan, Baseline Log, README | `feature/scm-setup` |
| Anyone | Bug fix | `feature/fix-short-description` |

### Step 3 — Make your changes, then compile and test

```bash
javac -d out -sourcepath Interfaces/src \
  Interfaces/src/Presentation/SubsystemEnums.java \
  Interfaces/src/Presentation/SubsystemRoles.java \
  Interfaces/src/Application/AccessControlList.java \
  Interfaces/src/Test/AccessControlListTest.java

java -cp out Test.AccessControlListTest
```

Last line must say: `TOTAL: 31   PASSED: 31   FAILED: 0`

### Step 4 — Commit using the correct format

```bash
git add <your files>
git commit -m "[SCI-XX] vX.X: Short description of what you changed"
```

Examples:
```
[SCI-01] v2.0: Add Javadoc comments to AccessControlList
[SCI-04] v1.0: Add 31 black-box test cases
[SCI-05] v1.0: Initial Test Procedure document
```

### Step 5 — Push your branch

```bash
git push origin feature/your-branch-name
```

### Step 6 — Open a Pull Request on GitHub

1. Go to the repo on GitHub
2. Click **"Compare & pull request"**
3. Set base: `main`, compare: your branch
4. Fill in the PR template completely
5. Assign **Daniel** as reviewer
6. Mohammad is automatically added as required reviewer via CODEOWNERS
7. Click **"Create pull request"**

### Step 7 — Wait for approvals

- **Daniel (SQA)** reviews for correctness and approves
- **Mohammad (SCM)** reviews for CM compliance and merges

You do **not** merge your own PR.

---

## PR Title Convention

```
[SCI-XX] Short description — vX.X
```

Examples:
```
[SCI-01] Add Javadoc to AccessControlList — v2.0
[SCI-04] Initial test driver 31 cases — v1.0
[SCI-05] Add Test Procedure document — v1.0
[SCI-08] Initial CM Plan — v1.0
```

---

## Who Reviews What

| Files Changed | Required Reviewer | Who Merges |
|---|---|---|
| `Interfaces/src/**` | Mohammad (auto via CODEOWNERS) + Daniel | Mohammad |
| `docs/*.docx` | Mohammad (auto via CODEOWNERS) + Daniel | Mohammad |
| `docs/javadoc/**` | Mohammad (auto via CODEOWNERS) | Mohammad |
| `README.md` | Daniel + Riley | Mohammad |
| `CONTRIBUTING.md` | Daniel + Riley | Mohammad |
| `.github/**` | Daniel | Mohammad |

---

## Reporting a Defect or Change Request

### Found a bug in a baselined file?
1. Open a GitHub Issue
2. Title: `[DEFECT][SCI-XX] Short description`
3. Label: `defect`
4. Describe what went wrong and steps to reproduce
5. Mohammad will triage it

### Want to propose a change?
1. Open a GitHub Issue
2. Title: `[CR][SCI-XX] Short description`
3. Label: `change-request`
4. Describe the change and why
5. Mohammad and the team will approve or reject it

**Do not implement any change without an approved GitHub Issue.**

---

## SCI Reference Table

| SCI ID | File | Owner |
|---|---|---|
| SCI-01 | Interfaces/src/Application/AccessControlList.java | Riley |
| SCI-02 | Interfaces/src/Presentation/SubsystemEnums.java | Riley |
| SCI-03 | Interfaces/src/Presentation/SubsystemRoles.java | Riley |
| SCI-04 | Interfaces/src/Test/AccessControlListTest.java | David |
| SCI-05 | docs/TestProcedure.docx | David |
| SCI-06 | docs/TestReport.docx | David |
| SCI-07 | docs/javadoc/ | Riley |
| SCI-08 | docs/CMP-ACL-2025-001.docx | Mohammad |
| SCI-09 | docs/BaselineLog.docx | Mohammad |
| SCI-10 | README.md | Mohammad |
| SCI-11 | CONTRIBUTING.md | Mohammad |
| SCI-12 | .github/CODEOWNERS | Mohammad |

---

*Maintained by Mohammad — SCM Manager | Moravian University CSCI 121*
*All changes to this file require Mohammad and Daniel approval.*
