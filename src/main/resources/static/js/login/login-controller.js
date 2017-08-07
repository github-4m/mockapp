angular.module('login.controller', []);
angular.module('login.controller').controller('loginCtrl', function($scope, LoadingFactory, CookiesFactory, SessionFactory, LoginFactory) {
  var init = function() {
    $scope.username = null;
    $scope.password = null;
    $scope.session = null;
    $scope.errors = {
      authentication: null
    };
    router();
  }
  var router = function() {
    LoadingFactory.increase('page');
    if (SessionFactory.token() !== null) {
      window.location = '/ui/module';
    }
    LoadingFactory.decrease('page');
  }
  var loginSuccessHandler = function(username, token) {
    CookiesFactory.put('username', username);
    SessionFactory.init(token);
    window.location = '/';
  }
  // checker
  var isEmpty = function(value) {
    return typeof value === 'undefined' || value === null || value === '';
  }
  $scope.isEmpty = function(value) {
    return isEmpty(value);
  }
  // data source
  var loginFactory = function(username, password) {
    LoadingFactory.increase('login');
    LoginFactory.post({
      'username': username,
      'password': password
    }, function(response) {
      if (response.success) {
        loginSuccessHandler(username, response.value);
      } else {
        $scope.errors.authentication = response.errorMessage;
        LoadingFactory.decrease('login');
      }
    }, function(response) {
      $scope.errors.authentication = 'Error occurred in the system'
      LoadingFactory.decrease('login');
    });
  }
  // checker
  $scope.isLoading = function(key) {
    return LoadingFactory.status(key);
  }
  // clickable
  $scope.clickLogin = function() {
    if (!LoadingFactory.status('login')) {
      loginFactory($scope.username, $scope.password);
    }
  }
  init();
});