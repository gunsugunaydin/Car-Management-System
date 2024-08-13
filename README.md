# Car Management System

Car Management System is a Spring Boot-based web application designed to manage user profiles and car information. It utilizes PostgreSQL for data storage and JWT for secure authentication. The application provides endpoints for user registration, login, profile management, and car operations, making it a comprehensive solution for user and vehicle management.

## Technologies

- **Spring Boot**: Backend development
- **JWT**: Authentication and authorization
- **OAuth2**: Secure access
- **Spring Security**: Security configuration
- **PostgreSQL**: Data management
- **Swagger**: API documentation

## Setup and Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/gunsugunaydin/Car-Management-System.git
    ```

2. **Install Project Dependencies**:
    ```bash
    cd Car-Management-System
    ./mvnw install
    ```

3. **Database Configuration**:
   Configure your PostgreSQL database with the following settings:

   - **Database URL**: `jdbc:postgresql://localhost:5432/dbcaruser`
   - **Username**: `postgres`
   - **Password**: `admin`

4. **Run the Application**:
    ```bash
    ./mvnw spring-boot:run
    ```

## Usage

- **User Registration**: Use the `/api/v1/users/add` endpoint to register new users.
- **User Login**: Use the `/api/v1/token` endpoint to log in and obtain a JWT token.
- **View Profile**: Use the `/api/v1/users/profile` endpoint to view the current user's profile.
- **Update Profile**: Use the `/api/v1/users/profile/update` endpoint to update user profile information.
- **Delete Profile**: Use the `/api/v1/users/profile/delete` endpoint to delete a user's profile. Note that deleting a user will also remove all cars associated with that user.
- **List Cars**: Use the `/api/v1/cars/list` endpoint to list cars associated with the user.
- **Add Car**: Use the `/api/v1/cars/add` endpoint to add a new car.
- **Update Car**: Use the `/api/v1/cars/{car_id}/update` endpoint to update an existing car.
- **Delete Car**: Use the `/api/v1/cars/{car_id}/delete` endpoint to delete a car.


## API Documentation

Access the API documentation through Swagger by navigating to `http://localhost:8080/swagger-ui/index.html`.

## Important Notes

- **Authority Management:** This setup anticipates future enhancements for access control, even though authorities are not yet used for securing endpoints.
- **Data Entry Guidelines:** Ensure all input meets specified validation criteria, such as setting `mileage` to a value greater than 0.0.

Thank you for following these guidelines to maintain system integrity.
