<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="login.app">
<head>
<jsp:include page="../dependencies.jsp"/>
<script src="/js/login/login-service.js"></script>
<script src="/js/login/login-controller.js"></script>
<script src="/js/login/login-app.js"></script>
</head>
<body ng-controller="loginCtrl">
  <jsp:include page="../header.jsp"/>
  <div class="login">
    <div class="login-container">
      <div class="login-form">
        <div class="login-form-title">
          <span>Sign In to Mockapp Account</span>
        </div>
        <div ng-if="!isEmpty(errors.authentication)" class="login-error">
          <span ng-bind="errors.authentication"></span>
        </div>
        <div class="form-group">
          <label>USERNAME</label>
          <input id="username" name="username" type="text" ng-model="username" placeholder="Enter Your Username"/>
        </div>
        <div class="form-group">
          <label>PASSWORD</label>
          <input id="password" name="password" type="password" ng-model="password" placeholder="Enter Your Password"/>
        </div>
        <button id="sign-in" type="button" class="btn btn-success" ng-click="clickLogin()" ng-disabled="isLoading('login')">SIGN IN</button>
      </div>
    </div>
  </div>
</body>
</html>