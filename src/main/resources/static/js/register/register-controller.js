angular.module('register.controller', []);
angular.module('register.controller').controller('registerCtrl', function($scope, $timeout, SessionFactory, LoadingFactory, RegisterFactory) {
  var init = function() {
    LoadingFactory.increase('page');
    $scope.register = {
      username: null,
      password: null,
      email: null,
      name: null
    };
    $scope.errors = {
      registration: null
    };
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
  // validation
  var validateRegister = function() {
    $scope.errors = {
      registration: null
    };
    var valid = $scope.valid = true;
    if (isEmpty($scope.register.username) || isEmpty($scope.register.password) || isEmpty($scope.register.email) || isEmpty($scope.register.name)) {
      valid = $scope.valid = false;
    }
    return valid;
  }
  // data source
  var registerFactory = function(register) {
    LoadingFactory.increase('page');
    RegisterFactory.post(register, function(response) {
      if (response.success) {
        swal({
          title: 'Registration complete',
          text: 'You will redirected to login page',
          type: 'success',
          confirmButtonColor: '#31ad74',
          confirmButtonText: 'OK',
          allowEscapeKey: false,
          allowOutsideClick: false
        }).then(function() {
          window.location = ctx + '/ui/login';
        });
      } else {
        $scope.errors.registration = response.errorMessage;
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
      $scope.errors.registration = 'Error occurred in the system';
    });
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
  // clickable
  $scope.clickLogin = function() {
    window.location = ctx + '/ui/login';
  }
  $scope.clickRegister = function() {
    if (validateRegister()) {
      registerFactory(angular.copy($scope.register));
    }
  }
  init();
});