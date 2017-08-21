angular.module('application.service', ['ngResource']);
angular.module('application.service').factory('LogoutFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/logout', null, {
    get: {
      method: 'GET',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('FilterModuleFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/module/filter', null, {
    get: {
      method: 'GET',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('ActivateModuleFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/module/:active', {
    active: '@active'
  }, {
    get: {
      method: 'GET',
      params: {
        'code': '@code',
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('FilterEndpointFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/endpoint/filter/module-code', null, {
    get: {
      method: 'GET',
      params: {
        'moduleCode': '@moduleCode',
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('ActivateEndpointFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/endpoint/:active', {
    active: '@active'
  }, {
    get: {
      method: 'GET',
      params: {
        'code': '@code',
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('DeleteEndpointFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/endpoint/delete', null, {
    get: {
      method: 'GET',
      params: {
        'code': '@code',
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('DeleteModuleFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/module/delete', null, {
    get: {
      method: 'GET',
      params: {
        'code': '@code',
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('CreateModuleFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/module/create', null, {
    post: {
      method: 'POST',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});
angular.module('application.service').factory('CreateEndpointFactory', function($resource, RequestIdFactory) {
  return $resource(ctx + '/api/endpoint/create', null, {
    post: {
      method: 'POST',
      params: {
        'requestId': RequestIdFactory.get()
      }
    }
  });
});