# office-space

## How to build
For Mac: first, you must install maven wrapper:
```
mvn -N io.takari:maven:0.7.7:wrapper
```
Noted: for Mac, we use `./mvnw` instead of `mvnw`

To build: 
```$xslt
mvnw package
```

## How to run Dev environment:
After built the project, go to `office-space\office-space-backend\target` then run command below to start Project Backend 
```$xslt
java -jar office-space-backend-0.0.1-SNAPSHOT.war
```

Go to `office-space\office-space-frontend\src\main\web\office-space-web` then run command below to start Project FrontEnd
then run
```$xslt
ng serve
```

## How to test:
Project is built and run on Backend, to check it, go to `http://localhost:9090`
Besides, you can go to `http://localhost:4200` to debug and develop frontend

## How to run production:
Build project with 1 built-in file:
```$xslt
mvnw clean package
```
Go to build folder and run war file
```$xslt
java -jar <filename>
```
