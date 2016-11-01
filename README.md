# jwt-implementation

This is an example project of JWT implementation, this project use spring-boot and java config.

You can run this project directly with maven command (mvn spring-boot:run), but don't forget to use postgresql database. You can use other database, but you need to change pom.xml (add/change database dependency) and application.properties (database configuration)

This project has 2 endpoint which ignored by spring-security, login and signup endpoint. You need to signup first, and then you can login with your username

If you want to access other endpoint, please put JWT token (you get that when you login) in "Authorization" header request

Features :
<ul>
<li>CORS Configuration : You can change CORS configuration as you want in FilterConfiguration.corsFilter() method</li>
<li>Single Session : Last session will kicked if endpoint of login called again. It's not really kicked out, but you can't use old token</li>
</ul>
