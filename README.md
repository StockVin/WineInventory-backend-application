# WineInventory | Back-End Application

## Summary

WineInventory is a specialized inventory management solution designed specifically for liquor store owners and suppliers. Key features include:

- Efficient management of liquor stock across multiple warehouses.
- User-friendly interface for tracking stock levels.
- Comprehensive sales analytics and reporting.
- Integration with suppliers for seamless order management.

## Features

The application includes the following documentation:

- CRUD operations for managing liquor stock.
- User authentication and authorization.
- Role-based access control for different user types (liquor store owners and suppliers).
- Real-time stock updates and notifications.
- Detailed sales reports and analytics.
- Profile management for users.

## Bounded Contexts

This version of WineInventory is focused on the stock management aspect of the application, managing the profiles of the users and allow user authentication. It includes the following bounded contexts:

### Authentication Context

This context handles user authentication and authorization, ensuring secure access to the application. It includes the following features:

- User registration and login.
- Role-based access control (producer, distributor).
- Password hashing and validation.
- Token-based authentication (JWT).
- Request filtering for bearer tokens.

This context includes also an anticorruption layer to ensure that the authentication process is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Validating user credentials.
- Generating and validating authentication tokens.
- Ensuring secure password storage and management.
- Injecting authenticated identities into the security context for downstream requests.

### Profile Management Context

This context manages user profiles, allowing users to view and update their personal and business information. It includes the following features:

- User profile retrieval and updates.
- Business contact data maintenance (name, email, phone).

### Reporting and Care Guide context

This context provides reports and care guides to help users make informed decisions and handle products properly. It includes the following features:

- Sales and loss reporting.
- Care guides creation and retrieval for liquor products.
- Report queries filtered by product, type, or owner.

### Alerts and Notifications Context

This context manages real-time notifications, ensuring users are informed about inventory conditions. It includes the following features:

- Stock condition alert creation with severity and type.
- Marking alerts as read/acknowledged.

This context also includes an anticorruption layer to ensure that the alert and notification system is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Providing a facade so other contexts (e.g., Inventory Management) can publish alerts without tight coupling.
- Translating cross-context commands into alert aggregates for persistence and auditing.

### Inventory Management Context

This context focuses on managing liquor stock across multiple warehouses, allowing users to track stock levels, add new products, and manage inventory efficiently. It includes the following features:

- Warehouse management (add, update).
- Product management (create, update, delete with image handling).
- Stock operations (add, reduce, transfer between warehouses).
- Product best-before/expiration tracking.
- Minimum stock thresholds and alert triggers.

This context also includes an anticorruption layer to ensure that the inventory management system is secure and does not interfere with the core business logic of the application. Its capabilities include:

- Managing multiple warehouses and their stock levels.
- Tracking product entries, exits, and transfers with best-before dates.
- Triggering alerts when minimum stock thresholds are breached via a facade.

### Order Management Context

This context handles sales order processing, allowing users to manage order lifecycles. It includes the following features:

- Sales order creation with items, delivery info, taxes, and notes.
- Order listing and detail retrieval.
- Order status updates (pending, processing, completed, cancelled).
- Order deletion for obsolete entries.