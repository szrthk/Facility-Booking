# Facility Booking System

A multi-tenant facility booking platform designed for colleges, co-working spaces, and organizations to manage courts, rooms, and equipment bookings. The system enforces business rules, handles concurrent bookings, and provides a robust foundation for facility management.

## 🚀 Features

### Core Functionality
- **Multi-tenant Architecture**: Isolated data per organization/tenant
- **Facility Management**: Create and configure bookable facilities with custom rules
- **Time Slot Generation**: Automated slot creation based on facility operating hours
- **Business Rules Enforcement**: Capacity limits, cooldown periods, daily booking limits
- **Concurrent Booking Support**: Designed for race condition handling

### Technical Features
- **RESTful API**: Clean HTTP endpoints with proper validation
- **MongoDB Integration**: Flexible document storage with compound indexes
- **Spring Boot 3.5.4**: Modern Java framework with latest features
- **Swagger Documentation**: Interactive API documentation
- **Health Monitoring**: Actuator endpoints for production monitoring
- **Input Validation**: Comprehensive request validation with clean error responses

## 🏗️ Architecture

The project follows **Clean Architecture** principles with clear separation of concerns:

```
src/main/java/com/szrthk/facility_booking/
├── api/                    # HTTP layer (controllers, DTOs, error handling)
├── domain/                 # Core business entities (Facility, Slot)
├── Service/               # Business logic layer
└── repo/                  # Data access layer (repositories)
```

### Key Design Patterns
- **Repository Pattern**: Abstracted data access
- **DTO Pattern**: API contract separation
- **Builder Pattern**: Immutable object construction
- **Multi-tenancy**: Tenant isolation at data level

## 📊 Current Status

### ✅ Completed
- **Domain Models**: Facility and Slot entities with MongoDB mappings
- **Data Transfer Objects**: Request/Response DTOs with validation
- **Service Layer**: Business logic for facility creation and slot generation
- **Repository Layer**: MongoDB integration with custom queries
- **Application Configuration**: Database and actuator setup
- **Project Structure**: Clean architecture implementation

### 🔄 In Progress
- **REST Controllers**: HTTP endpoints (created but need implementation)
- **Global Exception Handler**: Error handling (exists but needs implementation)

### 📋 Planned Features
- **Booking Management**: User booking functionality
- **User Authentication**: Security and user management
- **QR Code Integration**: Check-in system with QR codes
- **No-show Handling**: Automated penalty and notification system
- **Concurrent Booking**: Race condition resolution
- **Reporting**: Analytics and usage reports

## 🛠️ Technology Stack

- **Java 17**: Modern Java with latest features
- **Spring Boot 3.5.4**: Application framework
- **Spring Data MongoDB**: Database integration
- **MongoDB**: Document database
- **Lombok**: Boilerplate reduction
- **Jakarta Validation**: Input validation
- **Springdoc OpenAPI**: API documentation
- **Spring Actuator**: Health monitoring

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+ (running on localhost:27017)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd facility-booking
   ```

2. **Start MongoDB**
   ```bash
   # Make sure MongoDB is running on localhost:27017
   mongod
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the application**
   - **Application**: http://localhost:8080
   - **Swagger UI**: http://localhost:8080/swagger-ui.html
   - **Health Check**: http://localhost:8080/actuator/health

### Configuration

The application uses `application.yml` for configuration:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/facility_booking_dev

management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
```

## 📚 API Documentation

### Core Entities

#### Facility
Represents a bookable facility with configurable rules:
- **Multi-tenant**: Each facility belongs to a tenant
- **Business Rules**: Daily limits, cooldown periods
- **Operating Hours**: Configurable time windows
- **Capacity**: Maximum concurrent bookings

#### Slot
Represents individual bookable time slots:
- **Time-based**: UTC timestamps for consistency
- **Capacity Tracking**: Available vs booked capacity
- **Atomic Operations**: Designed for concurrent access

### Key Endpoints (Planned)
- `POST /api/facilities` - Create new facility
- `GET /api/facilities` - List facilities by tenant
- `POST /api/slots/generate` - Generate time slots
- `GET /api/slots` - List available slots
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - List user bookings

## 🏢 Multi-tenancy

The system is designed for multi-tenant usage:
- **Tenant Isolation**: All data includes `tenantId` for separation
- **Database Constraints**: Compound indexes ensure data integrity
- **Query Optimization**: Tenant-specific queries for performance
- **Scalability**: Designed to handle multiple organizations

## 🔒 Business Rules

### Facility Rules
- **Max Per Day**: Maximum bookings per user per day (default: 2)
- **Cooldown Period**: Minimum time between bookings (default: 60 minutes)
- **Operating Hours**: Configurable time windows (e.g., "08:00-20:00")
- **Slot Duration**: Configurable time slot length in minutes

### Booking Constraints
- **Capacity Limits**: Respects facility maximum capacity
- **Time Validation**: Only allows bookings within operating hours
- **Duplicate Prevention**: Prevents double-booking scenarios

## 🧪 Development

### Running Tests
```bash
./mvnw test
```

### Building the Application
```bash
./mvnw clean package
```

### Code Quality
- **Lombok**: Reduces boilerplate code
- **Validation**: Comprehensive input validation
- **Clean Architecture**: Clear separation of concerns
- **Documentation**: Inline code documentation

## 📈 Monitoring

The application includes Spring Actuator for monitoring:
- **Health Checks**: `/actuator/health`
- **Application Info**: `/actuator/info`
- **Metrics**: `/actuator/prometheus`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**szrthk** - Initial work

## 📝 Project Documentation

For detailed learning notes and implementation details, see [learning.md](learning.md).

---

**Note**: This is a work in progress. The core architecture is complete, and the next phase involves implementing the REST controllers and booking functionality.
