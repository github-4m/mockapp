angular.module('util.service', []);
angular.module('util.service').factory('LoadingFactory', function() {
  var loadings = [];
  var loadingManager = {
    increase: function(key) {
      if (typeof loadings[key] == 'undefined' || loadings[key] == null) {
        loadings[key] = 1;
      } else {
        loadings[key]++;
      }
    },
    decrease: function(key) {
      if (typeof loadings[key] != 'undefined' && loadings[key] != null) {
        loadings[key]--;
      }
    },
    status: function(key) {
      if (typeof loadings[key] == 'undefined' || loadings[key] == null || loadings[key] == 0) {
        return false;
      } else {
        return true;
      }
    },
    getAll: function() {
      return loadings;
    }
  };
  return loadingManager;
});
angular.module('util.service').factory('RequestIdFactory', function(CookiesFactory) {
  var requestId = '';
  if (CookiesFactory.get('username') == null) {
    requestId = 'anonymous-' + (new Date).getTime();
  } else {
    requestId = CookiesFactory.get('username') + '-' + (new Date).getTime();
  }
  var requestIdManager = {
    get: function() {
      return requestId;
    }
  };
  return requestIdManager;
});