# office-space

## How to build
For Mac:
First, you must install maven wrapper:
```
mvn -N io.takari:maven:0.7.7:wrapper
```
For Mac, use `./mvnw` instead of `mvnw`

To build: 
```$xslt
mvnw package
```

## How to run Dev environment:
After built the project, go to `office-space\office-space-backend\target` then run
```$xslt
java -jar office-space-backend-0.0.1-SNAPSHOT.war
```
to start Project Backend 

Go to `office-space\office-space-frontend\src\main\web\office-space-web`
then run
```$xslt
ng serve
```
to start Project FrontEnd

## How to test:
Project is built and run on Backend, to check it, go to `http://localhost:9090`
Besides, you can go to `http://localhost:4200` to debug and develop frontend

## How to run production:
Build project with 1 built-in file:
```$xslt
mvnw clean package
```
Then go to build folder and run war file
