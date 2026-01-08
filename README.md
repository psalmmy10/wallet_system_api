# wallet_system_api
A backend system that allows user to create wallet , fund, debit and get the wallet balance

Wallet System API

A Spring Boot-based wallet management system that allows users to create, fund, and debit wallets. The system uses H2 in-memory database for development and supports Swagger/OpenAPI documentation.

Features

Create Wallet: Create a wallet for a specific user.

Fund Wallet: Add funds to an existing wallet.

Debit Wallet: Withdraw funds from an existing wallet.

Get Wallet Details: Retrieve wallet balance and details.

Validation: Ensures valid amounts and handles insufficient balance.

API Documentation: Interactive Swagger/OpenAPI UI available.

Technologies Used

Java 21

Spring Boot 3

Spring Data JPA

H2 Database (in-memory)

Springdoc OpenAPI (Swagger)

Hibernate Validator

Lombok

Getting Started
Prerequisites

Java 21+

Maven 3+

IDE (IntelliJ, VSCode, etc.)

Optional: Postman for API testing

Running the Application

Clone the repository:

git clone <repository-url>
cd wallet_system


Build the project:

mvn clean install


Run the application:

mvn spring-boot:run


The application will start on http://localhost:8080.

H2 Database Console

Enabled at: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:walletdb

Username: sa

Password: (leave empty)

Swagger/OpenAPI Documentation

Access Swagger UI: http://localhost:8080/swagger-ui.html

Provides interactive API testing for all endpoints.

API Endpoints
Wallet Management
Method	Endpoint	Description
POST	/api/v1/wallets/create-wallet	Create a wallet for a user
POST	/api/v1/wallets/{walletId}/fund	Fund a wallet
POST	/api/v1/wallets/{walletId}/debit	Debit a wallet
GET	/api/v1/wallets/{walletId}	Get wallet details
Example Payloads

Fund Wallet:

{
  "amount": 500.00
}


Debit Wallet:

{
  "amount": 200.00
}

Error Handling

WalletNotFoundException: Thrown when the wallet does not exist.

BusinessRuleException: Thrown on insufficient balance or invalid operations.

Validation errors for invalid amounts are handled with HTTP 400 responses.


Notes

The current database is in-memory; all data is lost on application restart. For persistence, configure H2 with a file-based URL:

spring.datasource.url=jdbc:h2:file:./walletdb

A Spring Boot-based wallet management system that allows users to create, fund, and debit wallets. The system uses H2 in-memory database for development and supports Swagger/OpenAPI documentation.