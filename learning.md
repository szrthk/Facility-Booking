# Facility Booking
### Learning and progress documentation of this project, being developed by szrthk.
#### Note—This will include everything I have used, why I have used and whatever is useful in this project.

========================================================
#### Date — 13th August 2025

### Created the project from spring initializr. 

1. Added Dependencies - 


      1. Web → you get REST endpoints. skip? no HTTP, can’t call your app. 
      2. MongoDB → persistence. skip? all data lost on restart. 
      3. Validation → clean 400s for bad input. skip? garbage enters DB / random 500s later. 
      4. Lombok → less boilerplate. skip? tons of getters/setters/toString. 
      5. Actuator → health/metrics. skip? can’t show “prod-ish” ops. 
      6. Springdoc → Swagger UI docs. skip? testing/discovery is painful.
      
      
2. Created Folder Layout 


      1. api = HTTP layer (controllers, error handler, request/response DTOs). skip? controllers get fat + messy.
      2. service = business logic. skip? rules leak into controllers/repositories.
      3. repo = DB access only. skip? DB logic scattered everywhere.
      4. domain = your Mongo documents (core entities). skip? types live randomly.

3. Added application.yml file intro src/main/resources


   Why?
   1. uri picks DB + name (facility_booking_dev). skip? Spring tries default localhost/test; if Mongo isn’t there → connection errors.
   2. exposing only a few actuator endpoints. skip? either nothing exposed or too much (bad practice).
