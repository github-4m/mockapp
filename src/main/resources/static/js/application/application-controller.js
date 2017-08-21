angular.module('application.controller', []);
angular.module('application.controller').controller('modalCtrl', function($scope, $uibModalInstance, code, args, LoadingFactory, CreateModuleFactory, CreateEndpointFactory) {
  var init = function() {
    $scope.code = code;
    $scope.args = args;
    $scope.valid = true;
    $scope.errors = {
      creation: null
    };
    if ('createModule' === code) {
      $scope.createModule = {
        name: null,
        contextPath: null
      };
    } else if ('createEndpoint' === code) {
      $scope.createEndpoint = {
        moduleCode: args.module.code,
        url: null,
        requestMethod: null,
        requestParams: null,
        pathVariables: null,
        responseBody: null
      };
      $scope.module = args.module;
      $scope.requestParam = null;
    }
  }
  // checker
  var isEmpty = function(value) {
    return typeof value === 'undefined' || value === null || value === '' || (typeof value === 'object' && typeof value.length !== 'undefined' && value.length === 0);
  }
  $scope.isEmpty = function(value) {
    return isEmpty(value);
  }
  $scope.isLoading = function(key) {
    return LoadingFactory.status(key);
  }
  // validation
  var validateCreateModule = function() {
    var valid = $scope.valid = true;
    if (isEmpty($scope.createModule.name) || isEmpty($scope.createModule.contextPath)) {
      valid = $scope.valid = false;
    }
    return valid;
  }
  var validateCreateEndpoint = function() {
    var valid = $scope.valid = true;
    if (isEmpty($scope.createEndpoint.url) || isEmpty($scope.createEndpoint.requestMethod) || isEmpty($scope.createEndpoint.responseBody)) {
      valid = $scope.valid = false;
    }
    return valid;
  }
  // data source
  var createModuleFactory = function(createModule) {
    LoadingFactory.increase('page');
    CreateModuleFactory.post(createModule, function(response) {
      if (response.success) {
        $uibModalInstance.close({
          code: code
        });
      } else {
        $scope.errors.creation = response.errorMessage;
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
      $scope.errors.creation = 'Error occurred in the system';
    });
  }
  var createEndpointFactory = function(createEndpoint) {
    LoadingFactory.increase('page');
    CreateEndpointFactory.post(createEndpoint, function(response) {
      if (response.success) {
        $uibModalInstance.close({
          code: code
        });
      } else {
        $scope.errors.creation = response.errorMessage;
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
      $scope.errors.creation = 'Error occurred in the system';
    });
  }
  // generator
  $scope.generatePathVariables = function() {
    if (!isEmpty($scope.createEndpoint.url)) {
      var url = $scope.createEndpoint.url;
      var urlParts = url.split("/");
      var pathVariableRegExp = new RegExp(/{(.*)}/);
      var pathVariables = [];
      for (var i in urlParts) {
        if (pathVariableRegExp.test(urlParts[i])) {
          pathVariables.push(urlParts[i].match(/{(.*)}/).pop());
        }
      }
      if (isEmpty(pathVariables)) {
        pathVariables = null;
      }
      $scope.createEndpoint.pathVariables = pathVariables;
    }
  }
  // clickable
  $scope.clickDismiss = function() {
    $uibModalInstance.dismiss();
  }
  $scope.clickCreateModule = function() {
    if (validateCreateModule()) {
      createModuleFactory(angular.copy($scope.createModule));
    }
  }
  $scope.clickRequestMethod = function(requestMethod) {
    $scope.createEndpoint.requestMethod = requestMethod;
  }
  $scope.clickRequestParam = function() {
    if (!isEmpty($scope.requestParam) && (isEmpty($scope.createEndpoint.requestParams) || -1 === $scope.createEndpoint.requestParams.indexOf($scope.requestParam))) {
      if (isEmpty($scope.createEndpoint.requestParams)) {
        $scope.createEndpoint.requestParams = [];
      }
      $scope.createEndpoint.requestParams.push($scope.requestParam);
    }
    $scope.requestParam = null;
  }
  $scope.clickCreateEndpoint = function() {
    if (validateCreateEndpoint()) {
      createEndpointFactory(angular.copy($scope.createEndpoint));
    }
  }
  // changeable
  $scope.changeRequestParam = function() {
    $scope.requestParam = $scope.requestParam.replace(/[^A-Za-z0-9-]/g, '');
  }
  init();
});
angular.module('application.controller').controller('applicationCtrl', function($scope, $timeout, $uibModal, CookiesFactory, SessionFactory,
LoadingFactory, LogoutFactory, FilterModuleFactory, ActivateModuleFactory, FilterEndpointFactory, ActivateEndpointFactory, DeleteEndpointFactory, DeleteModuleFactory) {
  var init = function() {
    LoadingFactory.increase('page');
    $scope.errors = {
      logout: null,
      module: {
        filter: null
      }
    };
    $scope.modules = [];
    $scope.endpoint = null;
    router();
    filterModuleFactory();
  }
  var router = function() {
    if (isEmpty(SessionFactory.token())) {
      $timeout(function() {
        window.location = ctx + '/';
      }, 500);
    } else {
      LoadingFactory.decrease('page');
    }
  }
  var logoutSuccessHandler = function() {
    SessionFactory.remove();
    CookiesFactory.remove('username');
    router();
  }
  var openModal = function(code, size, args) {
    var modalInstance = $uibModal.open({
      backdrop: 'static',
      controller: 'modalCtrl',
      keyboard: false,
      size: size,
      templateUrl: '/ui/application/modal',
      resolve: {
        code: function() {
          return code;
        },
        args: function() {
          return args;
        }
      }
    });
    modalInstance.result.then(function(result) {
      if ('createModule' === result.code) {
        filterModuleFactory();
      } else if ('createEndpoint' === result.code) {
        filterModuleFactory();
      }
    });
  }
  // post process
  var postFilterModuleFactory = function(content) {
    for (var i in content) {
      content[i].toggle = {
        details: false,
        endpoints: false
      };
      content[i].endpoints = null;
    }
    $scope.modules = content;
    $scope.endpoint = null;
  }
  // data source
  var logoutFactory = function() {
    LoadingFactory.increase('page');
    LogoutFactory.get(null, function(response) {
      if (response.success) {
        logoutSuccessHandler();
      } else {
        $scope.errors.logout = response.errorMessage;
        LoadingFactory.decrease('page');
      }
    }, function(response) {
      LoadingFactory.decrease('page');
      $scope.errors.logout = 'Error occurred in the system';
    });
  }
  var filterModuleFactory = function() {
    LoadingFactory.increase('page');
    FilterModuleFactory.get(null, function(response) {
      if (response.success) {
        postFilterModuleFactory(response.content);
      } else {
        $scope.errors.module.filter = response.errorMessage;
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
      $scope.errors.module.filter = 'Error occurred in the system';
    });
  }
  var activateModuleFactory = function(active, code) {
    LoadingFactory.increase('page');
    ActivateModuleFactory.get({
      active: active ? 'enable' : 'disable',
      code: code
    }, function(response) {
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
    });
  }
  var filterEndpointFactory = function(module) {
    LoadingFactory.increase('filter-endpoint-' + module.code);
    FilterEndpointFactory.get({
      moduleCode: module.code
    }, function(response) {
      if (response.success) {
        module.endpoints = response.content;
      }
      LoadingFactory.decrease('filter-endpoint-' + module.code);
    }, function(response) {
      LoadingFactory.decrease('filter-endpoint-' + module.code);
    });
  }
  var activateEndpointFactory = function(active, code) {
    LoadingFactory.increase('page');
    ActivateEndpointFactory.get({
      active: active ? 'enable' : 'disable',
      code: code
    }, function(response) {
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
    });
  }
  var deleteEndpointFactory = function(endpoint) {
    LoadingFactory.increase('page');
    DeleteEndpointFactory.get({
      code: endpoint.code
    }, function(response) {
      if (response.success) {
        filterEndpointFactory(endpoint.module);
        $scope.endpoint = null;
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
    });
  }
  var deleteModuleFactory = function(module) {
    LoadingFactory.increase('page');
    DeleteModuleFactory.get({
      code: module.code
    }, function(response) {
      if (response.success) {
        filterModuleFactory();
      }
      LoadingFactory.decrease('page');
    }, function(response) {
      LoadingFactory.decrease('page');
    });
  }
  // checker
  var isEmpty = function(value) {
    return typeof value === 'undefined' || value === null || value === '' || (typeof value === 'object' && typeof value.length !== 'undefined' && value.length === 0);
  }
  $scope.isEmpty = function(value) {
    return isEmpty(value);
  }
  $scope.isLoading = function(key) {
    return LoadingFactory.status(key);
  }
  // clickable
  $scope.clickLogout = function() {
    logoutFactory();
  }
  $scope.clickToggle = function(key, field) {
    field.toggle[key] = !field.toggle[key];
    if ('endpoints' === key) {
      if (field.toggle[key]) {
        filterEndpointFactory(field);
      }
    }
  }
  $scope.clickActivate = function(key, field) {
    if ('module' === key) {
      activateModuleFactory(field.enable, field.code);
    } else if ('endpoint' === key) {
      activateEndpointFactory(field.enable, field.code);
    }
  }
  $scope.clickEndpoint = function(module, endpoint) {
    $scope.endpoint = endpoint;
    $scope.endpoint.module = {
      code: module.code,
      name: module.name,
      contextPath: module.contextPath
    };
  }
  $scope.clickDeleteModule = function(module) {
    swal({
      title: 'Deleting module',
      text: 'Are you sure want to delete ' + module.name + ' module?',
      type: 'warning',
      confirmButtonColor: '#ec971f',
      confirmButtonText: 'YES, DELETE IT',
      cancelButtonColor: '#ddd',
      cancelButtonText: 'CANCEL',
      showCancelButton: true,
      allowEscapeKey: false,
      allowOutsideClick: false
    }).then(function() {
      deleteModuleFactory(module);
    });
  }
  $scope.clickDeleteEndpoint = function(endpoint) {
    swal({
      title: 'Deleting endpoint',
      text: 'Are you sure want to delete this endpoint?',
      type: 'warning',
      confirmButtonColor: '#ec971f',
      confirmButtonText: 'YES, DELETE IT',
      cancelButtonColor: '#ddd',
      cancelButtonText: 'CANCEL',
      showCancelButton: true,
      allowEscapeKey: false,
      allowOutsideClick: false
    }).then(function() {
      deleteEndpointFactory(endpoint);
    });
  }
  $scope.clickCreateModule = function() {
    openModal('createModule', 'sm', null);
  }
  $scope.clickCreateEndpoint = function(module) {
    openModal('createEndpoint', 'md', {
      module: module
    });
  }
  init();
});