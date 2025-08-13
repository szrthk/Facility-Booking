# Facility Booking
### Learning and progress documentation of this project, being developed by szrthk.
#### Note—This will include everything I have used, why I have used and whatever is useful in this project.

========================================================
#### Date — 13th August 2025

### 1. Project Initialization from Spring Initializr

#### 1.1 Dependencies Added
- **Web**: REST endpoints for HTTP communication
  - *Why needed?* No HTTP layer means you can't call your application externally
- **MongoDB**: Database persistence layer
  - *Why needed?* Without persistence, all data is lost on application restart
- **Validation**: Input validation framework
  - *Why needed?* Provides clean 400 errors for bad input instead of garbage data in DB or random 500s
- **Lombok**: Boilerplate code reduction
  - *Why needed?* Eliminates tons of getters/setters/toString methods
- **Actuator**: Health checks and metrics
  - *Why needed?* Essential for production operations and monitoring
- **Springdoc**: Swagger UI documentation
  - *Why needed?* Makes API testing and discovery much easier

#### 1.2 Folder Structure Created
- **api**: HTTP layer (controllers, error handlers, request/response DTOs)
  - *Why needed?* Without separation, controllers become fat and messy
- **service**: Business logic layer
  - *Why needed?* Prevents business rules from leaking into controllers/repositories
- **repo**: Database access layer only
  - *Why needed?* Keeps database logic centralized instead of scattered everywhere
- **domain**: MongoDB document entities (core business objects)
  - *Why needed?* Provides organized structure for domain types instead of random placement

#### 1.3 Application Configuration
- **application.yml**: Configuration file in `src/main/resources`
  - **Database URI**: `mongodb://localhost:27017/facility_booking_dev`
    - *Why needed?* Spring defaults to localhost/test; if MongoDB isn't running, you get connection errors
  - **Actuator Endpoints**: Limited exposure (health, info, prometheus)
    - *Why needed?* Either nothing is exposed (bad for monitoring) or too much is exposed (security risk)

========================================================
#### Date — Current Implementation Status

### 4. Implemented Core Domain Models

#### 4.1 Facility.java
- **Purpose**: Core entity representing bookable facilities (rooms, courts, equipment)
- **Key Features**:
  - Multi-tenant design with `tenantId` for organization isolation
  - Compound index on `tenantId + name` ensures unique facility names per tenant
  - Nested `Rules` class for business constraints:
    - `maxPerDay`: Maximum bookings per user per day (default: 2)
    - `coolDownMins`: Minimum time between bookings (default: 60 minutes)
  - Configurable operating hours as string format "08:00-20:00"
  - Slot duration in minutes for time slot generation
- **Annotations Used**:
  - `@Document(collection = "facilities")` - MongoDB collection mapping
  - `@CompoundIndex` - Database-level uniqueness constraint
  - `@NotBlank`, `@Positive` - Validation constraints
  - Lombok annotations for boilerplate reduction

#### 4.2 Slot.java
- **Purpose**: Represents individual bookable time slots
- **Key Features**:
  - Compound index on `tenantId + facilityId + start` prevents duplicate slots
  - UTC timestamp storage for timezone consistency
  - Capacity tracking with `maxCapacity` vs `bookedCount`
  - Designed for atomic operations during concurrent bookings
- **Design Decisions**:
  - `bookedCount` field designed for atomic increments
  - UTC timestamps avoid timezone confusion
  - Unique constraint prevents double-booking scenarios

### 5. Implemented Data Transfer Objects (DTOs)

#### 5.1 FacilityCreateRequest.java
- **Purpose**: Input validation for facility creation API
- **Features**:
  - Immutable record structure
  - Jakarta validation annotations (`@NotBlank`, `@Positive`)
  - Clear documentation of expected formats
  - Flattens nested rules into simple fields

#### 5.2 FacilityResponse.java
- **Purpose**: API response format for facility data
- **Features**:
  - Flattens nested `Rules` object for easier consumption
  - Includes MongoDB-generated `id`
  - Immutable record structure

### 6. Implemented Service Layer (Business Logic)

#### 6.1 FacilityService.java
- **Purpose**: Handles facility creation business logic
- **Implementation**:
  - Uses `@RequiredArgsConstructor` for dependency injection
  - Converts DTO to domain entity using Builder pattern
  - Maps flat DTO fields to nested `Rules` object
  - Persists to MongoDB and returns response DTO
- **Design Patterns**:
  - Builder pattern for entity construction
  - DTO-to-Entity mapping
  - Repository pattern for data access

#### 6.2 SlotService.java
- **Purpose**: Generates bookable time slots for facilities
- **Complex Algorithm**:
  1. **Facility Lookup**: Finds facility by ID with error handling
  2. **Time Parsing**: Splits "08:00-20:00" into `LocalTime` objects
  3. **Slot Generation**: Creates slots for specified number of days
     - Iterates through each day starting from today
     - Creates slots from opening to closing time
     - Respects facility's slot duration configuration
  4. **Duplicate Prevention**: Checks existing slots to avoid duplicates
  5. **Batch Operations**: Saves all new slots in one database operation
- **Key Features**:
  - UTC timezone handling for consistency
  - Efficient duplicate detection using time range queries
  - Returns count of actually created slots
- **Business Logic**:
  - Rolling window approach (generates slots for future days)
  - Respects facility operating hours
  - Handles time slot boundaries correctly

### 7. Implemented Data Access Layer

#### 7.1 FacilityRepository.java
- **Purpose**: MongoDB data access for facilities
- **Features**:
  - Extends `MongoRepository<Facility, String>`
  - Custom query method: `findByTenantId(String tenantId)`
  - Inherits CRUD operations from Spring Data MongoDB

#### 7.2 SlotRepository.java
- **Purpose**: MongoDB data access for slots
- **Features**:
  - Custom query: `findByTenantIdAndFacilityIdAndStartBetween()`
  - Used for duplicate slot detection
  - Supports time-range queries for slot generation

### 8. Current Project Status

#### ✅ Completed:
- Core domain models with proper MongoDB mappings
- DTO layer with validation
- Service layer with business logic
- Repository layer with custom queries
- Application configuration
- Proper folder structure following clean architecture

#### 🔄 In Progress:
- Controllers are created but empty (ready for implementation)
- Global exception handler exists but needs implementation

#### 📋 Next Steps:
- Implement REST controllers for facility and slot management
- Add booking functionality
- Implement user management and authentication
- Add QR code generation for check-ins
- Implement no-show handling
- Add concurrent booking race condition handling

### 9. Technical Decisions Made

#### 9.1 Database Design
- **MongoDB**: Chosen for flexibility with nested objects and multi-tenant data
- **Compound Indexes**: Used for data integrity and performance
- **UTC Timestamps**: Consistent timezone handling across the application

#### 9.2 Architecture Patterns
- **Clean Architecture**: Clear separation of concerns
- **Repository Pattern**: Abstracted data access
- **DTO Pattern**: API contract separation
- **Builder Pattern**: Immutable object construction

#### 9.3 Validation Strategy
- **Jakarta Validation**: Bean validation for input sanitization
- **Domain-level constraints**: Business rules embedded in entities
- **Service-level validation**: Business logic validation

#### 9.4 Multi-tenancy Approach
- **Tenant isolation**: All entities include `tenantId`
- **Database-level constraints**: Compound indexes ensure tenant data separation
- **Query optimization**: Tenant-specific queries for performance
