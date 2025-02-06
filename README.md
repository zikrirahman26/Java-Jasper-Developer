# Java-Jasper-Developer

## VALIDATE REQUEST
```
name = "Name is required"
email = "Email must be a valid email address"
phone = "Phone must be a valid phone number"
```

## BACKEND-APP 
#### Entity
```
private Long id;
private String name;
private String email;
private String phone;
```
### List Endpoint User :
#### [POST] AddUser : http://localhost:8080/api/users
RequestMessage :
#### [POST] AddUser : `http://localhost:8080/api/users`
```Request Body```
```json
{
    "name": "name",
    "email": "email@gmail.com",
    "phone": "082200119922"
}
```
```Response Body```
```json
{
  "data": {
    "id": 1,
    "name": "name",
    "email": "email@gmail.com",
    "phone": "082200119922"
  },
  "message": "success add user"
}
```

#### [PUT] UpdateUser : http://localhost:8080/api/users/{userId}
```Request Body```
```json
{
    "name": "name1",
    "email": "email1@gmail.com",
    "phone": "082200119923"
}
```
```Response Body```
```json
{
  "data": {
    "id": 1,
    "name": "name1",
    "email": "email1@gmail.com",
    "phone": "082200119923"
  },
  "message": "success update user"
}
```
#### [GET] GetAllUsers : http://localhost:8080/api/users
#### [GET] GetUserById : http://localhost:8080/api/users/{userId}
#### [DELETE] DeleteUser : http://localhost:8080/api/users/{userId}

### List Endpoint Report :
#### [GET] generateReport : http://localhost:8080/reports/export-report
#### [GET] generateReportById : http://localhost:8080/reports/export-report/{userId}

## FRONTEND-APP
### List URL :
#### UsersView : http://localhost:8080/users
#### AddUserView : http://localhost:8080/add-user
#### UpdateUserView : http://localhost:8080/update-user
#### GetUserView : http://localhost:8080/user

## DATBASE
### PostgreSQL
```
spring.application.name=backend-springboot
server.port=8080

spring.datasource.url=jdbc:postgresql://localhost:5432/local_db
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

## REPORT JASPER
### TOOLS JASPERSOFT STUDIO
```
Download AllUsers
path : /resources/reports/report-users.jrxml

Download UserById
path : /resources/reports/report-user-by-id.jrxml

Output File
AllUsers : ReportAllUser_{yyyyMMddHHmmss}.pdf
UserById : ReportUser_{yyyyMMddHHmmss}.pdf
```

