# SimpleBlog
A simple blog application, created using Spring Boot, Spring Security, and Spring Data JPA, on the backend, as well as Angular 13, Boostrap, and Toastr, on the frontend, that allows users to register, login, and logout using JWT, with authentication and access tokens, as well as to create and read blog posts.

## Installation
### Backend - Spring Boot
Go to ..\src\main\resources\application.properties and modify the datasource configuration and JWT keystore properties as required.
Replace ..\src\main\resources\simpleblog.jks if using your own keystore.
### Frontend - Angular 13
Install Angular CLI using the npm package manager:
```bash
npm install -g @angular/cli
```
Install packages and dependencies:
```bash
cd ng-simple-blog-frontend
ng install
```

## Running
### Backend - Spring Boot
Run the project:
```bash
mvn spring-boot:run
```
### Frontend - Angular 13
Run the project:
```bash
cd ng-simple-blog-frontend
ng serve
```
