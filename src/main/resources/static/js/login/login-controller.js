angular.module('login.controller', []);
angular.module('login.controller').controller('loginCtrl', function($scope, $timeout, CookiesFactory, SessionFactory, LoadingFactory, AuthenticationFactory) {
  var init = function() {
    LoadingFactory.increase('page');
    $scope.login = {
      username: null,
      password: null,
    };
    $scope.errors = {
      authentication: null
    }
    $scope.valid = true;
    router();
  }
  var router = function() {
    if (isEmpty(SessionFactory.token())) {
      LoadingFactory.decrease('page');
    } else {
      $timeout(function() {
        window.location = ctx + '/ui/application';
      }, 500);
    }
  }
  var authenticationSuccessHandler = function(token) {
    SessionFactory.init(token);
    CookiesFactory.put('username', $scope.login.username);
    router();
  }
  // validation
  var validateLogin = function() {
    $scope.errors = {
      authentication: null
    };
    var valid = $scope.valid = true;
    if (isEmpty($scope.login.username) || isEmpty($scope.login.password)) {
      valid = $scope.valid = false;
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
  var authenticationFactory = function(login) {
    LoadingFactory.increase('page');
    AuthenticationFactory.post(login, function(response) {
      if (response.success) {
        authenticationSuccessHandler(response.value);
      } else {
        $scope.errors.authentication = response.errorMessage;
        LoadingFactory.decrease('page');
      }
    }, function(response) {
      LoadingFactory.increase('page');
      $scope.errors.authentication = 'Error occurred in the system';
    });
  }
  // clickable
  $scope.clickLogin = function() {
    if (validateLogin()) {
      authenticationFactory(angular.copy($scope.login));
    }
  }
  $scope.clickRegister = function() {
    window.location = ctx + '/ui/signup';
  }
  init();
});