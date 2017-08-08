angular.module('register.app', ['interceptor', 'security.service', 'util.service', 'register.controller', 'register.service'])
.config(function($httpProvider) {
  $httpProvider.interceptors.push('RequestInterceptorFactory');
  $httpProvider.interceptors.push('ResponseInterceptorFactory');
});