# Sweet Shop Management System

## Overview
This project is a full-stack **Sweet Shop Management System**. It allows users to view, search, purchase sweets, and for admins to add, update, delete, and restock sweets.  

- **Backend:** Java Spring Boot  
- **Frontend:** React.js  
- **Authentication:** JWT-based token authentication  

---

## Features

### User
- Register & Login  
- View all available sweets  
- Search sweets by name, category, or price range  
- Purchase sweets (quantity decreases on purchase)  

### Admin
- Add new sweets  
- Update sweet details  
- Delete sweets  
- Restock sweets (quantity increases)  

---

## Setup Instructions

### Backend
1. Navigate to backend folder:  
   ```bash
   cd sweet-management-system
2. Build and run the Spring Boot application:
   ./mvnw spring-boot:run


3. API runs on http://localhost:8080

Frontend

1. Go to frontend folder:
   cd sweetshop-frontend
   
2. Install dependencies:
   npm install

3. Run the React app:
   npm start

4. Frontend runs on http://localhost:3000

API Endpoints
Auth

=> POST /api/auth/register - Register new user

=> POST /api/auth/login - Login user

Sweets (Protected)

=> GET /api/sweets - Get all sweets

=> GET /api/sweets/search - Search sweets

=> POST /api/sweets - Add sweet (Admin)

=> PUT /api/sweets/:id - Update sweet (Admin)

=> DELETE /api/sweets/:id - Delete sweet (Admin)

Inventory (Protected)

=> POST /api/sweets/:id/purchase - Purchase sweet

=> PUT /api/sweets/:id/restock - Restock sweet (Admin)

Test Cases
Backend Tests

=> User Registration & Login

=> Get All Sweets & Search

=> Add, Update, Delete Sweets (Admin)

=> Purchase & Restock Sweets

Frontend Tests

=> Render Sweet List

=> Purchase Button disabled when quantity = 0

=> Admin Panel visible only for ROLE_ADMIN

=> Add, Delete, Restock Sweet functionality

My AI Usage

AI tools were used to:

Generate Test Cases That help to Indentify Best Test Cases


Reflection: AI saved time in Indentify Test Cases.

Run frontend and backend concurrently.

Admin-only endpoints require a JWT token with ROLE_ADMIN.



