angular.module('login.controller', []);
angular.module('login.controller').controller('loginCtrl', function($scope, LoadingFactory, CookiesFactory, SessionFactory, LoginFactory) {
  var init = function() {
    $scope.username = null;
    $scope.password = null;
    $scope.session = null;
    $scope.errors = {
      username: null,
      password: null,
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
  // validation
  var validateLogin = function() {
    var valid = true;
    if (isEmpty($scope.username)) {
      valid = false;
      $scope.errors.username = 'Username must not be blank';
    }
    if (isEmpty($scope.password)) {
      valid = false;
      $scope.errors.password = 'Password must not be blank';
    }
    return valid;
  }
  // checker
  var isEmpty = function(value) {
    return typeof value === 'undefined' || value === null || value === '';
  }
  $scope.isEmpty = function(value) {
    return isEmpty(value);
  }
  $scope.isLoading = function(key) {
    return LoadingFactory.status(key);
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
  // clickable
  $scope.clickLogin = function() {
    if (validateLogin() && !LoadingFactory.status('login')) {
      loginFactory($scope.username, $scope.password);
    }
  }
  init();
});