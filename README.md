# **Internet-Banking-Microservices-Project**
 
## **Table of Contents**  
- [Overview](#overview)  
- [Architecture](#architecture)  
- [Tech Stack](#tech-stack)  
- [Services](#services)  
  - [1. Banking Core Service](#1-banking-core-service)  
  - [2. Transaction Service](#2-transaction-service)  
  - [3. User Service](#3-user-service)  
  - [4. API Gateway](#4-api-gateway)  
- [Database Schema](#database-schema)  
- [API Endpoints](#api-endpoints)  
 
---
 
## **Overview**  
This is a microservices-based **Internet Banking System** that enables users to:  
✔ Create an account  
✔ Deposit and withdraw money  
✔ Check balance  
✔ View transaction history  
 
The system follows a distributed architecture where each service handles a specific domain. Inter-service communication is managed via **OpenFeign**, and service discovery/load balancing is handled using **Spring Cloud** components.  
 
---
 
## **Architecture**  
The system follows a **microservices-based architecture** with independent services for banking operations.  
 
![Architecture Diagram-bank-microservices](https://github.com/user-attachments/assets/2cdfbb1f-25eb-4368-990d-4714988d9d37)
  
 
**Microservices:**  
1. **Banking Core Service:** Manages accounts and balances.  
2. **Transaction Service:** Handles deposits and withdrawals.  
3. **User Service:** Manages user authentication and accounts.  
4. **API Gateway:** Routes requests and provides security.  
 
**Inter-Service Communication:**  
- Uses **OpenFeign** to call services.  
- API Gateway forwards requests to respective services.  
- Transactions update balance in Banking Core Service.  
 
---
 
## **Tech Stack**  
- **Backend:** Spring Boot 3.4.2, Java 17  
- **API Communication:** OpenFeign  
- **Database:** MySQL  
- **Service Discovery:** Spring Cloud 
 
---
 
## **Services**  
 
### **1. Banking Core Service**  
- Manages account creation, balance updates, and retrieval.  
- Exposes REST APIs for transaction operations.  
 
### **2. Transaction Service**  
- Records deposits and withdrawals.  
- Communicates with Banking Core Service via **OpenFeign**.  
 
### **3. User Service**  
- Manages user registration and authentication.  
- Uses JWT for security.  
 
### **4. API Gateway**  
- Centralized entry point for all services.  
- Handles authentication, logging, and request forwarding.  
 
---
 
## **Database Schema**  
### **Account Table**
| Column         | Type       | Description                 |
|---------------|-----------|-----------------------------|
| id            | Long      | Primary key                 |
| accountNumber | String    | Unique account identifier  |
| balance       | Double    | Available account balance  |
| userId        | Long      | Foreign key to User table  |
 
### **Transaction Table**
| Column         | Type       | Description                 |
|---------------|-----------|-----------------------------|
| id            | Long      | Primary key                 |
| accountNumber | String    | Associated account number  |
| amount        | Double    | Transaction amount         |
| type          | String    | DEPOSIT / WITHDRAWAL       |
| timestamp     | DateTime  | Time of transaction        |
 
---
 
## **API Endpoints**  
 
### **User Service**
| Method | Endpoint               | Description              |
|--------|------------------------|--------------------------|
| POST   | /users/register        | Registers a new user    |
| GET    | /users/{id}            | Fetch user details      |
 
### **Banking Core Service**
| Method | Endpoint                        | Description                    |
|--------|---------------------------------|--------------------------------|
| POST   | /accounts/create               | Creates a new account         |
| GET    | /accounts/balance/{accountNumber} | Retrieves account balance    |
| PUT    | /accounts/addBalance/{accountNumber}/{amount} | Adds money to account |
| PUT    | /accounts/deductBalance/{accountNumber}/{amount} | Withdraws money |
 
### **Transaction Service**
| Method | Endpoint                      | Description                  |
|--------|-------------------------------|------------------------------|
| POST   | /transactions/addMoney        | Deposits money into account |
| POST   | /transactions/withdraw        | Withdraws money             |
| GET    | /transactions/{accountNumber} | Fetches transaction history |
 
---
 
## **Setup and Installation**  
 
### **Prerequisites**  
- Java 17  
- Spring Boot  
- MySQL Workbench  
- Docker (Optional for containerization)  
 
### **Clone the Repository**  
```bash
git clone https://github.com/your-username/internet-banking-microservices.git
cd internet-banking-microservices
