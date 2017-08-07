angular.module('module.app', ['interceptor', 'security.service', 'util.service', 'module.controller'])
.config(function($httpProvider) {
  $httpProvider.interceptors.push('RequestInterceptorFactory');
  $httpProvider.interceptors.push('ResponseInterceptorFactory');
});