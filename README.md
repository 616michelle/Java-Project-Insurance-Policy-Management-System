# InsureRight - Insurance Policy Management System

A Java console application for managing clients and their insurance policies.

## Features
- Register clients with a unique ID (format: CL-XXXX) and monthly income
- Add up to 3 types of policies per client: Health, Motor, and Home
- Automatically calculates monthly premiums based on policy details
- Enforces a coverage rule: total premiums cannot exceed 40% of a client's monthly income
- Remove policies by index
- View a single client's full policy summary or print all registered clients
- Input validation and exception handling throughout

## Classes
- `InsureRight` – Main class, runs the menu-driven interface
- `Client` – Stores client data and manages their policies (implements `Assessable`)
- `Policy` – Abstract base class for all policy types
- `HealthPolicy` – Premium based on age and pre-existing conditions
- `MotorPolicy` – Premium based on vehicle value and years no claims
- `HomePolicy` – Premium based on property size and flood risk
- `Assessable` – Interface with `isWithinCoverageLimit()` and `printSummary()`

## How to Run
1. Clone or download the repository
2. Open in Eclipse IDE
3. Run `InsureRight.java` as a Java Application
4. Follow the on-screen menu

## Built With
- Java
- Eclipse IDE
