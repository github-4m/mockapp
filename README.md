# jwt-implementation

This is an example project of JWT implementation, this project use spring-boot and java config.
You can run this project directly with maven command (mvn spring-boot:run), but don't forget to use postgresql database. You can use other database, but you need to change pom.xml (add/change database dependency) and application.properties (database configuration)
This project has 2 endpoint which ignored by spring-security, login and signup endpoint. You need to signup first, and then you can login with your username
