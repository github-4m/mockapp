<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="application.app">
<head>
  <title>Application - Mockapp</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Titillium+Web">
	<link rel="stylesheet" href="/css/style.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.7/sweetalert2.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-resource.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.6/angular-cookies.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/2.5.0/ui-bootstrap-tpls.min.js"></script>
	<script src="https://use.fontawesome.com/1bc7e8e2c7.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.6.7/sweetalert2.min.js"></script>
  <script src="/js/security/security-service.js"></script>
  <script src="/js/util/util-service.js"></script>
  <script src="/js/interceptor/interceptor.js"></script>
  <script src="/js/application/application-app.js"></script>
  <script src="/js/application/application-controller.js"></script>
  <script src="/js/application/application-service.js"></script>
  <script>
    var ctx = "${pageContext.request.contextPath}";
  </script>
</head>
<body ng-controller="applicationCtrl">
  <div ng-if="isLoading('page')" class="loading page">
  	<div class="loading-image">
  		<img src="/images/loading.svg"/>
  	</div>
  </div>
	<div class="header">
 		<div class="header-block">
 			<div class="header-logo">
 				<img src="/images/mockapp-logo.png"/>
 			</div>
 			<div class="header-option">
 				<div class="header-option-item">
 					<button class="btn btn-blue btn-square" type="button" ng-click="clickCreateModule()">CREATE MODULE</button>
 				</div>
 				<div class="header-option-user">
 					<div class="header-option-user-info">
 						<span>fathan.mustaqiim</span>&nbsp;<i class="fa fa-chevron-down"></i>
 					</div>
 					<div class="header-option-user-dropdown">
 						<ul>
							<li><a ng-click="clickLogout()">Sign out</a></li>
 						</ul>
 					</div>
 				</div>
  		</div>
 		</div>
	</div>
	<div class="application">
    <div class="side-endpoint">
      <div ng-if="isEmpty(endpoint)">
        <span>No endpoint selected</span>
      </div>
      <div ng-if="!isEmpty(endpoint)">
        <div class="side-endpoint-title">
          <span ng-bind="endpoint.url"></span>
        </div>
        <div class="side-endpoint-information">
          <div class="side-endpoint-subtitle">
            <span>DETAILS</span>
          </div>
          <div class="form-group">
            <label>MODULE NAME</label>
            <span ng-bind="endpoint.module.name"></span>
          </div>
          <div class="form-group">
            <label>HOW TO CALL</label>
            <span ng-bind="('/api/endpoint/mockup/' + endpoint.module.contextPath + '/' + endpoint.url)"></span>
          </div>
          <div class="form-group">
            <label>ACTIVE</label>
            <input id="endpoint-active" class="ios-checkbox" type="checkbox" ng-model="endpoint.enable" ng-click="clickActivate('endpoint', endpoint)"/>
            <label for="endpoint-active"></label>
          </div>
          <div class="form-group">
            <label>REQUEST METHOD</label>
            <span class="tag" ng-class="{'tag-green' : 'POST' === endpoint.requestMethod, 'tag-blue' : 'GET' === endpoint.requestMethod}" ng-bind="endpoint.requestMethod"></span>
          </div>
          <div class="form-group">
            <label>REQUEST PARAMETERS</label>
            <div class="group-tag" ng-if="!isEmpty(endpoint.requestParams)">
              <span ng-repeat="requestParam in endpoint.requestParams"><span class="tag tag-grey" ng-bind="requestParam"></span>&nbsp;</span>
            </div>
            <div ng-if="isEmpty(endpoint.requestParams)">
              <span>-</span>
            </div>
          </div>
          <div class="form-group">
            <label>PATH VARIABLES</label>
            <div class="group-tag" ng-if="!isEmpty(endpoint.pathVariables)">
              <span ng-repeat="pathVariable in endpoint.pathVariables"><span class="tag tag-grey" ng-bind="pathVariable"></span>&nbsp;</span>
            </div>
            <div ng-if="isEmpty(endpoint.pathVariables)">
              <span>-</span>
            </div>
          </div>
          <div class="form-group">
            <label>RESPONSE BODY</label>
            <textarea ng-model="endpoint.responseBody" placeholder="This endpoint doesn't have response body" disabled></textarea>
          </div>
          <button class="btn btn-red btn-square" type="button" ng-click="clickDeleteEndpoint(endpoint)">DELETE</button>
        </div>
      </div>
    </div>
    <div class="module">
      <div ng-repeat="module in modules" class="module-data">
        <div class="module-title">
          <span ng-bind="module.name"></span>
        </div>
        <div class="module-information">
          <div class="module-subtitle">
            <span>DETAILS</span>
          </div>
          <div class="module-expand" ng-click="clickToggle('details', module)">
            &nbsp;<i class="fa" ng-class="{'fa-chevron-up' : module.toggle.details, 'fa-chevron-down' : !module.toggle.details}"></i>
          </div>
          <div ng-if="module.toggle.details">
            <div class="form-group">
              <label>ACTIVE</label>
              <input id="module-active" class="ios-checkbox" type="checkbox" ng-model="module.enable" ng-click="clickActivate('module', module)"/>
              <label for="module-active"></label>
            </div>
            <div class="form-group">
              <label>CONTEXT PATH</label>
              <span ng-bind="module.contextPath"></span>
            </div>
            <button class="btn btn-red btn-square" type="button" ng-click="clickDeleteModule(module)">DELETE</button>
            <button class="btn btn-green btn-square" type="button" ng-click="clickCreateEndpoint(module)">CREATE ENDPOINT</button>
          </div>
        </div>
        <div class="module-information">
          <div class="module-subtitle">
            <span>ENDPOINTS</span>
          </div>
          <div class="module-expand" ng-click="clickToggle('endpoints', module)">
            &nbsp;<i class="fa" ng-class="{'fa-chevron-up' : module.toggle.endpoints, 'fa-chevron-down' : !module.toggle.endpoints}"></i>
          </div>
          <div ng-if="module.toggle.endpoints">
            <div>
              <div ng-repeat="endpoint in module.endpoints" class="endpoint-data" ng-click="clickEndpoint(module, endpoint)">
                <i class="fa fa-circle" ng-class="{'green' : endpoint.enable, 'red' : !endpoint.enable}"></i>&nbsp;<span ng-bind="endpoint.url"></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
	</div>
</body>
</html>