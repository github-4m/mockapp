<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="login.app">
<head>
  <title>Sign in - Mockapp</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Titillium+Web">
	<link rel="stylesheet" href="/css/style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-resource.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-cookies.min.js"></script>
  <script src="/js/security/security-service.js"></script>
  <script src="/js/util/util-service.js"></script>
  <script src="/js/login/login-app.js"></script>
  <script src="/js/login/login-controller.js"></script>
  <script src="/js/login/login-service.js"></script>
  <script>
    var ctx = "${pageContext.request.contextPath}";
  </script>
</head>
<body ng-controller="loginCtrl">
  <div ng-if="isLoading('page')" class="loading page">
  	<div class="loading-image">
  		<img src="/images/loading.svg"/>
  	</div>
  </div>
	<div class="header">
		<div class="header-container">
			<div class="header-block">
				<div class="header-logo">
					<img src="/images/mockapp-logo.png"/>
				</div>
				<div class="header-navigation">
					<div class="header-navigation-item">
						<button class="btn btn-blue btn-oval" type="button" ng-click="clickRegister()">SIGN UP</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="login">
		<div class="login-container">
			<div class="login-title">
				<span>Sign In</span>
			</div>
			<div class="login-form" ng-form="loginForm">
			  <div ng-if="!isEmpty(errors.authentication)" class="login-error">
			    <span ng-bind="errors.authentication"></span>
			  </div>
				<div class="form-group">
					<label>USERNAME</label>
					<input id="username" name="username" type="text" placeholder="Enter your username" ng-model="login.username" ng-class="{'error' : loginForm.username.$invalid && !valid}" required />
				</div>
				<div class="form-group">
					<label>PASSWORD</label>
					<input id="password" name="password" type="password" placeholder="Enter your password" ng-model="login.password" ng-class="{'error' : loginForm.password.$invalid && !valid}" required />
				</div>
				<button class="btn btn-green btn-square" type="button" ng-click="clickLogin()">SIGN IN</button>
			</div>
			<div class="login-lower">
				<span>Don't have an account?&nbsp;</span><a ng-click="clickRegister()">Create one now</a>
			</div>
		</div>
	</div>
	<div class="footer">
		<span>Copyright &#169 2017 Mockapp.org. Version 1.0.0</span>
	</div>
</body>
</html>