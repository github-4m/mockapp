angular.module('login.service', ['ngResource']);
angular.module('login.service').factory('LoginFactory', function($resource, RequestIdFactory) {
  return $resource('/api/login', null, {
    post: {
      method: 'POST',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});