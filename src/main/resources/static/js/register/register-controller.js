angular.module('register.controller', []);
angular.module('register.controller').controller('registerCtrl', function($scope, LoadingFactory) {
  var init = function() {
    $scope.session = null;
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
  init();
});