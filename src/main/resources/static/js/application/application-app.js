angular.module('application.app', ['ui.bootstrap', 'security.service', 'util.service', 'interceptor', 'application.controller', 'application.service'])
.config(function($httpProvider) {
  $httpProvider.interceptors.push('RequestInterceptorFactory');
});