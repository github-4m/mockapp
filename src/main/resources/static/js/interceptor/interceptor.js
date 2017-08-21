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