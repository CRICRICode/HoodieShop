# E-commerce Website with Shopping Cart
## Overview

This project is a web-based e-commerce application built as part of an exam. The website allows users to browse products, filter them, add items to a shopping cart, and proceed with checkout. It also handles concurrent operations, user login, and sign-up. Admins can manage the product catalog, adding or removing items. The back-end is powered by a PostgreSQL database, and user authentication is managed through Keycloak.

# Features

Product Filtering: Users can filter products based on categories or specific attributes.
Shopping Cart: Users can add products to the cart, modify quantities, and proceed to checkout.
Concurrent Operations: The site is built to handle multiple users and actions simultaneously without data conflicts.
User Authentication: Includes both sign-up and login functionality for users.
Admin Panel: Admins can add or remove products, maintaining the store catalog.
Secure User Management: Login and authentication are managed through Keycloak.


### Example Screenshots
1. Login Page
![Screenshot 2024-09-29 alle 4 20 40 PM](https://github.com/user-attachments/assets/2e67b80b-b1c4-49b9-a5f4-ee8d39468144)

2. Sign-Up Page
![Screenshot 2024-09-29 alle 4 20 22 PM](https://github.com/user-attachments/assets/bf089a7c-da55-4c65-93fd-4dc90902d217)

3. Product Filters
![Screenshot 2024-09-29 alle 4 20 48 PM](https://github.com/user-attachments/assets/8df5086f-d095-4aef-888b-f35fe626c6dd)

4. Product Selection
![Screenshot 2024-09-29 alle 4 20 04 PM](https://github.com/user-attachments/assets/13895a86-7563-4af7-98da-e33cd4ece096)

5. Product Listings
![Screenshot 2024-09-29 alle 4 19 44 PM](https://github.com/user-attachments/assets/e5bfa86d-c0bb-4e93-a11f-df1c74b0e27b)

# Installation and Setup

To get started with the project, follow these steps:

## 1. Clone the repository
> git clone [https://github.com/your-username/your-repo.git](https://github.com/CRICRICode/HoodieShop)

> cd your-repo
## 2. Set up the Database
The application uses PostgreSQL for database management. Make sure to install PostgreSQL if you don't have it already.
> PostgreSQL Port: 7140

### Database Setup:
Create a new database for the project.
Update the config.json or .env file with your PostgreSQL credentials and set the port to 7140.

> psql -U your-username -p 7140 -c "CREATE DATABASE your_db;"

## 3. Configure Keycloak for Authentication
The project uses Keycloak to handle user authentication. Follow these steps to set up Keycloak:

Download and install Keycloak from keycloak.org.
Create a new realm and client for the application.
Configure roles and permissions for admin and user accounts.
Update the keycloak.json file with the necessary configuration details.

## 4. Install Dependencies
Navigate to the project directory and install the necessary packages.

# For the backend
> cd backend

> npm install

# For the frontend
> cd ../frontend

> npm install

## 5. Start the Application

Backend (API Server): Run the following command from the backend folder:
> npm run start

Frontend (UI Server): Run the following command from the frontend folder, ensuring the UI is served on port 4200:
> ng serve --port 4200

## 6. Access the Application
UI: The frontend is accessible at http://localhost:4200.
API: The API server can be accessed on the port specified during setup (default: http://localhost:7140).
