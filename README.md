# SimpleBlog
A simple blog application created using Spring Boot and Angular 13 that allows user to create and read blog posts.

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
