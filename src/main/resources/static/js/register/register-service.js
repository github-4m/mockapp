angular.module('register.service', ['ngResource']);
angular.module('register.service').factory('RegisterFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/signup', null, {
    post: {
      method: 'POST',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});