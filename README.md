# office-space
##### Version: 0.0.1-SNAPSHOT

[![Build Status](https://travis-ci.org/our-fancy-team-name/office-space.svg?branch=master)](https://travis-ci.org/our-fancy-team-name/office-space)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=our-fancy-team-name_office-space&metric=alert_status)](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)
 
## Project Master branch analyst
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=our-fancy-team-name_office-space&metric=ncloc)](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=our-fancy-team-name_office-space&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=our-fancy-team-name_office-space&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=our-fancy-team-name_office-space&metric=coverage)](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)

## Technical Specs:

IDE:
 1. [IntelliJ community](https://www.jetbrains.com/idea/)
 1. [Visual studio code](https://code.visualstudio.com/)
 
Technical stack
 1. Back end
  * Languages: Java 1.8
  * Framework: [spring](https://spring.io/)
  * DB schema management: [liquibase](https://www.liquibase.org/)
  * Plugin: [lombok](https://projectlombok.org/)
  * CI-CD: [travis](travis-ci.org)
  * Quality check: [sonarcloud](https://sonarcloud.io/)
  * Build tool: [maven](https://maven.apache.org/)
 1. Front end
  * Languages: typescript/js
  * Package manager: [npm](https://www.npmjs.com/)
  * Framework: [Angular 9.0.5](http://angular.io/)
  * Components library: [mdbootstrap](https://mdbootstrap.com/docs/angular/) and [material angular](https://material.angular.io/)
  
## Start development process:
 1. Make sure you use the same IDE listed above. It all free and it match with our code base so we can format code the same.
 1. IntelliJ should automatically use `.editorconfig`, if not you must find the way to use `.editorconfig` file
 1. Start office space backend on IntelliJ
 1. Start angular app located on `office-space-frontend/src/main/web/office-space-web` by visual studio code

## How to build
For Mac: first, you must install maven wrapper:
```
mvn -N io.takari:maven:0.7.7:wrapper
```
Noted: for Mac, we use `./mvnw` instead of `mvnw`

To build:
```$xslt
mvnw clean package 
```
The artifact available at `office-space-backend/target`
