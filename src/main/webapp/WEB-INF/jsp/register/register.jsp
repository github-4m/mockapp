<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="register.app">
<head>
  <title>Sign up - Mockapp</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Titillium+Web">
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.7/sweetalert2.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-resource.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-cookies.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.7/sweetalert2.min.js"></script>
  <script src="/js/security/security-service.js"></script>
  <script src="/js/util/util-service.js"></script>
  <script src="/js/register/register-app.js"></script>
  <script src="/js/register/register-controller.js"></script>
  <script src="/js/register/register-service.js"></script>
  <script>
    var ctx = "${pageContext.request.contextPath}";
  </script>
</head>
<body ng-controller="registerCtrl">
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
						<button class="btn btn-green btn-oval" type="button" ng-click="clickLogin()">SIGN IN</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="register">
		<div class="register-container">
			<div class="register-title">
				<span>Sign Up</span>
			</div>
			<div class="register-form" ng-form="registerForm">
			  <div ng-if="!isEmpty(errors.registration)" class="register-error">
			    <span ng-bind="errors.registration"></span>
			  </div>
				<div class="form-group">
					<label>USERNAME</label>
					<input id="username" name="username" type="text" placeholder="Enter your username" ng-model="register.username" ng-class="{'error' : registerForm.username.$invalid && !valid}" required />
				</div>
				<div class="form-group">
					<label>EMAIL</label>
					<input id="email" name="email" type="text" placeholder="Enter your email" ng-model="register.email" ng-class="{'error' : registerForm.email.$invalid && !valid}" required />
				</div>
				<div class="form-group">
					<label>NAME</label>
					<input id="name" name="name" type="text" placeholder="Enter your name" ng-model="register.name" ng-class="{'error' : registerForm.name.$invalid && !valid}" required />
				</div>
				<div class="form-group">
					<label>PASSWORD</label>
					<input id="password" name="password" type="password" placeholder="Enter your password" ng-model="register.password" ng-class="{'error' : registerForm.password.$invalid && !valid}" required />
				</div>
				<button class="btn btn-blue btn-square" type="button" ng-click="clickRegister()">SIGN UP</button>
			</div>
			<div class="register-lower">
				<span>Already have an account?&nbsp;</span><a ng-click="clickLogin()">Sign in</a>
			</div>
		</div>
	</div>
	<div class="footer">
		<span>Copyright © 2017 Mockapp.org. Version 1.0.0</span>
	</div>
</body>
</html>