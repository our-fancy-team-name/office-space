# office-space
[![Build Status](https://travis-ci.com/our-fancy-team-name/office-space.svg?branch=master)](https://travis-ci.com/our-fancy-team-name/office-space)
### [Back-end](https://sonarcloud.io/dashboard?id=our-fancy-team-name_office-space)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=office-space-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=office-space-java)

[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=office-space-java&metric=ncloc)](https://sonarcloud.io/dashboard?id=office-space-java)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=office-space-java&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=office-space-java)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=office-space-java&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=office-space-java)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=office-space-java&metric=coverage)](https://sonarcloud.io/dashboard?id=office-space-java)

### [Front-end](https://sonarcloud.io/dashboard?id=office-space-web)
[![DeepScan grade](https://deepscan.io/api/teams/8125/projects/10280/branches/140197/badge/grade.svg)](https://deepscan.io/dashboard#view=project&tid=8125&pid=10280&bid=140197)


[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=office-space-web&metric=ncloc)](https://sonarcloud.io/dashboard?id=office-space-web)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=office-space-web&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=office-space-web)


## Technical Specs:

IDE:
 1. [IntelliJ community](https://www.jetbrains.com/idea/)
 1. [Visual studio code](https://code.visualstudio.com/)
 
Technical stack
 1. Back end
  * Languages: [Java 15](https://www.oracle.com/java/technologies/javase-downloads.html)
  * Framework: [spring boot 2.4.2](https://spring.io/)
  * CI-CD: [Github action](https://github.com/features/actions)
  * Quality check: [sonarcloud](https://sonarcloud.io/) and [deepscan](https://deepscan.io/)
  * Build tool: [maven](https://maven.apache.org/)
 1. Front end
  * Languages: typescript/js
  * Package manager: [npm](https://www.npmjs.com/)
  * Framework: [Angular 9.0.5](http://angular.io/)
  * Components library: [material angular](https://material.angular.io/)
  
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

Script (for windows)
```
📦script
 ┣ 📂build
 ┃ ┣ 📜build-w-test.cmd             // build with test
 ┃ ┗ 📜build_wo_test.cmd            // build without test
 ┣ 📂db
 ┃ ┣ 📜docker-compose-mysql.yml
 ┃ ┣ 📜start.cmd                    // start mysql
 ┃ ┗ 📜stop.cmd                     // stop mysql
 ┗ 📂utils
 ┃ ┣ 📜git.cmd                      // git note
 ┃ ┣ 📜jacoco-report.cmd            // generate jacoco report
 ┃ ┗ 📜javadoc.cmd                  // generate javadocs
```
