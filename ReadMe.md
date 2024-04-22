# MedMX: Patient Management System

## Features:
Manages the patient list  
Manages appointments  
Manages notes  

## Tools
#### Front-End: HTML, CSS, Bootstrap, Javascript (DOM manipulation, fetch)
#### Back-End: Java (JDK 17), Springboot (3.2.3), Thymeleaf
#### Dependencies from spring initializr: Spring Web, Lombok, Spring Boot DevTools, Spring Data JPA, MySQL Driver, Spring Security, Thymeleaf
#### Database: MySQL
#### 5 models
#### 7 html templates

## Model Schema:
<img src="medmx-schema.jpg" alt="schema" width="400px">

## Model Relationship:
### Unidirectional (<> Many-to-Many; < One-to-Many)
#### "physician"."id" <> "role"."physId"
#### "physician"."id" <> "patient"."physId"
#### "physician"."id" < "note"."physId"
#### "patient"."id" < "note"."patId"
#### "physician"."id" < "schedule"."physId"
#### "patient"."id" < "schedule"."patId"

## API RestController Routes (fetch(route, {method}))
`GET /api/physicians/{id}`: Get the physician by id  
`GET /api/physicians`: Get all physicians  
`PUT /api/physicians/{id}`: Edit specific physician  
`DELETE /api/physicians/{id}`: Delete specific physician  
`GET /api/patients/{id}`: Get the patient by id  
`GET /api/patients`: Get all patients  
`GET /api/physicians/{id}/patients`: Get all patients of a specific physician  
`POST /api/patients` : Add a patient to database  
`PUT /api/patients/{id}`: Edit specific patient  
`DELETE /api/patients/{id}`: Delete specific patient  
`GET /api/physicians/{id}/schedules`: All schedules for specific physician  
`GET /api/physicians/{id}/schedules?date={date}`: Schedules for specific physician and date  
`POST /api/schedules` : Add a schedule to database  
`PUT /api/schedules/{id}` : Edit a schedule with specified id  
`DELETE /api/schedules/{id}` : Delete a schedule with specified id  
`GET /api/notes/patients/{patientId}` : Get all the notes for the patient with specified id  
`GET /api/notes/{id}` : Get the note with specified id  
`POST /api/notes` : Add a note to the database  
`PUT /api/notes/{id}` : Edit the note with specified id  
`DELETE /api/notes/{id}` : Delete the note with specified id  

## Controller Routes (th:href="@{route}")
`GET /`: index.html  
`GET /register`: register.html  
`POST /register/save`: index.html (with success); register.html(with error);  
`GET /login`: login.html  
`GET /patientlist`: patientlist.html  
`GET /addpatient`: createpatient.html  
`GET /schedule`: schedule-page.html  
`GET /patientDetail/{id}`: patient-detail.html  

## Page Layout:
### Login Page
### Sign Up Page
### Home page:
Has navs to Patients page and Schedule page

### Patients list page
View all my patients
Add, edit, delete patients
View patient with specific name
View patient with specific patient id

### Schedule page
View/Schedule patient appointment by date
Access patient from appointment


### Patient page (individual):
#### Gen info tab:
Name, DOB, Address, Phone number

#### Notes tab:
Access the notes (create, read, edit, delete)

#### Prescription tab:
Not available (work in progress)
