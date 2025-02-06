# Java-Jasper-Developer

## VALIDATE REQUEST
```
name = "Name is required"
email = "Email must be a valid email address"
phone = "Phone must be a valid phone number"
```

## BACKEND-APP 
### List Endpoint User :
#### [POST] AddUser : http://localhost:8080/api/users
RequestMessage :
#### [POST] AddUser : `http://localhost:8080/api/users`
```json
{
    "name": "name",
    "email": "email@gmail.com",
    "phone": "082200119922"
}
```
#### [PUT] UpdateUser : http://localhost:8080/api/users/{userId}
```json
{
    "name": "name1",
    "email": "email1@gmail.com",
    "phone": "082200119923"
}
```
#### [GET] GetAllUsers : http://localhost:8080/api/users
#### [GET] GetUserById : http://localhost:8080/api/users/{userId}
#### [DELETE] DeleteUser : http://localhost:8080/api/users/{userId}

### List Endpoint Report :
#### [GET] generateReport : http://localhost:8080/reports/export-report
#### [GET] generateReportById : http://localhost:8080/reports/export-report/{userId}

## FRONT-END
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
Print AllUsers
path : /resources/reports/report-users.jrxml

Print UserById
path : /resources/reports/report-user-by-id.jrxml
```

