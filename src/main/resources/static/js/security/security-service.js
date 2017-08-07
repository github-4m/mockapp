angular.module('security.service', ['ngCookies']);
angular.module('security.service').factory('CookiesFactory', function($cookies) {
  var cookiesManager = {
    get: function(key) {
      var value = $cookies.get(key);
      return typeof value === 'undefined' || value === 'null' ? null : value;
    },
    put: function(key, value) {
      $cookies.put(key, value, {
        path: '/'
      });
    },
    remove: function(key) {
      $cookies.remove(key, {
        path: '/'
      });
    }
  };
  return cookiesManager;
});
angular.module('security.service').factory('SessionFactory', function(CookiesFactory) {
  var sessionManager = {
    init: function(token) {
      CookiesFactory.put('token', token);
    },
    token: function() {
      return CookiesFactory.get('token');
    },
    remove: function() {
      CookiesFactory.remove('token');
    }
  }
  return sessionManager;
});