<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="register.app">
<head>
<title>Create an Account . Mockapp.org</title>
<jsp:include page="../dependencies.jsp"/>
<script src="/js/register/register-service.js"></script>
<script src="/js/register/register-controller.js"></script>
<script src="/js/register/register-app.js"></script>
</head>
<body ng-controller="registerCtrl">
  <jsp:include page="../header.jsp"/>
  <div class="register">
    <div class="register-container">
      <div class="register-form" ng-form="registerForm">
        <div class="register-form-title">
          <span>Create an Account</span>
        </div>
        <div class="form-group">
          <label>USERNAME</label>
          <input id="username" name="username" type="text" ng-model="register.username" placeholder="Enter Your Username" required/>
        </div>
        <div class="form-group">
          <label>PASSWORD</label>
          <input id="password" name="password" type="password" ng-model="register.password" placeholder="Enter Your Password" required/>
        </div>
        <div class="form-group">
          <label>NAME</label>
          <input id="name" name="name" type="text" ng-model="register.name" placeholder="Enter Your Name" required/>
        </div>
        <div class="form-group">
          <label>EMAIL</label>
          <input id="email" name="email" type="text" ng-model="register.email" placeholder="Enter Your Email" required/>
        </div>
        <button id="sign-up" type="button" class="btn btn-success" ng-disabled="isLoading('register')">SIGN UP</button>&nbsp;
        <span>Already have an account?</span>&nbsp;<a id="sign-in" href="/ui/login">Sign in here</a>
      </div>
    </div>
  </div>
</body>
</html>