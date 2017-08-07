angular.module('module.controller', []);
angular.module('module.controller').controller('moduleCtrl', function($scope, LoadingFactory, CookiesFactory) {
  var init = function() {
    $scope.session = {
      username: CookiesFactory.get('username')
    };
  }
  // checker
  var isEmpty = function(value) {
    return typeof value === 'undefined' || value === null || value === '';
  }
  $scope.isEmpty = function(value) {
    return isEmpty(value);
  }
  init();
});