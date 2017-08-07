angular.module('interceptor', []);
angular.module('interceptor').factory('RequestInterceptorFactory', function(SessionFactory) {
  var requestInterceptor = {
    request: function(config) {
      config.headers['Authorization'] = SessionFactory.token();
      return config;
    }
  };
  return requestInterceptor;
});
angular.module('interceptor').factory('ResponseInterceptorFactory', function(SessionFactory) {
  var responseInterceptor = {
    response: function(response) {
      return response;
    },
    responseError: function(response) {
      return response;
    }
  };
  return responseInterceptor;
});