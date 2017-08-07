angular.module('login.app', ['interceptor', 'security.service', 'util.service', 'login.controller', 'login.service'])
.config(function($httpProvider) {
  $httpProvider.interceptors.push('RequestInterceptorFactory');
});