angular.module('login.service', ['ngResource']);
angular.module('login.service').factory('AuthenticationFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/login', null, {
    post: {
      method: 'POST',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});