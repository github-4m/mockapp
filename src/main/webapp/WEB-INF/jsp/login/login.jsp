<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="login.app">
<head>
<title>Sign In to Mockapp . Mockapp.org</title>
<jsp:include page="../dependencies.jsp"/>
<script src="/js/login/login-service.js"></script>
<script src="/js/login/login-controller.js"></script>
<script src="/js/login/login-app.js"></script>
</head>
<body ng-controller="loginCtrl">
  <jsp:include page="../header.jsp"/>
  <div class="login">
    <div class="login-container">
      <div class="login-form" ng-form="loginForm">
        <div class="login-form-title">
          <span>Sign In to Mockapp</span>
        </div>
        <div ng-if="!isEmpty(errors.authentication)" class="login-error">
          <span ng-bind="errors.authentication"></span>
        </div>
        <div class="form-group">
          <label>USERNAME</label>
          <input id="username" name="username" ng-class="{'error' : loginForm.username.$invalid && (loginForm.username.$dirty || !isEmpty(errors.username))}" type="text" ng-model="username" placeholder="Enter Your Username" required/>
        </div>
        <div class="form-group">
          <label>PASSWORD</label>
          <input id="password" name="password" ng-class="{'error' : loginForm.password.$invalid && (loginForm.password.$dirty || !isEmpty(errors.password))}" type="password" ng-model="password" placeholder="Enter Your Password" required/>
        </div>
        <button id="sign-in" type="button" class="btn btn-success" ng-click="clickLogin()" ng-disabled="isLoading('login')">SIGN IN</button>
      </div>
      <div class="register-form">
        <span>Don't have an account?</span>&nbsp;<a id="sign-up" href="/ui/signup">Create an account here</a>
      </div>
    </div>
  </div>
</body>
</html>