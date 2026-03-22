# Order Management System

A desktop application built with **Java (Swing)** and **PostgreSQL** for managing clients, products, and processing orders. The project utilizes a multi-layered architecture (Presentation, Business Logic, and Data Access) and features a reflection-based Generic DAO for robust and efficient database operations.

## Features
- **Client Management:** Add, update, view, and delete client records cleanly.
- **Product Inventory:** Manage product details, pricing, and track stock levels in real-time.
- **Order Processing:** 
  - Place orders for clients.
  - Automatically verify stock validity and update product stock thresholds.
  - Safely handles relational database constraints (prevents deletion of products/clients that are linked to existing orders).
- **Billing System:** Automatically generate immutable bills/receipts upon successful order placement, kept safely in a database log table.

## Architecture
The application is structured into distinct layers to separate concerns:
- **Presentation:** Contains the Swing graphical user interface components (`ClientWindow`, `ProductWindow`, `OrderWindow`, `BillWindow`, etc.).
- **Business Logic:** Enforces application rules and constraints (e.g., checking stock availability before placing an order).
- **Data Access:** Manages database operations. Uses a centralized, reflection-based `AbstractDAO<T>` to generically and dynamically handle CRUD operations for any data model class by mapping fields to database columns.
- **Data Model:** Contains the entity classes that map directly to the database tables (`Client`, `Product`, `Order`, `Bill`).
- **Connection:** Manages the PostgreSQL JDBC database connections.

## Requirements
- **Java 21** or higher
- **Maven**
- **PostgreSQL** (Running locally on the default port `5432`)

## Database Setup
Ensure you have a PostgreSQL database named `ordersmanagement` running locally. 
The application expects the following tables to be created: `client`, `product`, `orders`, and `log` (for bills).

Default connection credentials used:
- **Username:** `postgres`
- **Password:** `your_password`

*(If your local PostgreSQL setup uses different credentials or a different port, make sure to update the `DBURL`, `USER`, and `PASS` constants inside the `ConnectionFactory.java` file).*

## How to Build and Run
1. Open a terminal in the project's root directory.
2. Compile the project using Maven:
   ```bash
   mvn clean compile
   ```
3. Run the application by executing the `MainApp` class from your preferred Java IDE (such as IntelliJ IDEA, Eclipse, or VS Code), which will launch the Swing graphical interface.
   - Using Maven: `mvn exec:java -Dexec.mainClass="com.example.ordermanagement.presentation.MainApp"`
