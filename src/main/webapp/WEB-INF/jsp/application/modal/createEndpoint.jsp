<div class="modal-header">
  <div class="modal-title">
    <span>Create Endpoint</span>
  </div>
</div>
<div class="modal-body" ng-form="createEndpointForm">
  <div class="form-group">
    <label>MODULE NAME</label>
    <span ng-bind="module.name"></span>
  </div>
  <div class="form-group">
    <label>URL</label>
    <input id="url" name="url" type="text" ng-model="createEndpoint.url" ng-blur="generatePathVariables()" ng-class="{'error' : createEndpointForm.url.$invalid && !valid}" required />
  </div>
  <div class="form-group">
    <label>REQUEST METHOD</label>
    <div class="select-group input-quarter">
      <div class="input-addon right" uib-dropdown>
        <div class="multi-select" ng-class="{'error' : isEmpty(createEndpoint.requestMethod) && !valid}">
          <span ng-if="!isEmpty(createEndpoint.requestMethod)" class="tag" ng-class="{'tag-blue' : 'GET' === createEndpoint.requestMethod, 'tag-green' : 'POST' === createEndpoint.requestMethod}" ng-bind="createEndpoint.requestMethod"></span>
        </div>
        <div id="request-method-toggle" class="addon right" ng-class="{'error' : isEmpty(createEndpoint.requestMethod) && !valid}" uib-dropdown-toggle>
          <span>&nbsp;<i class="fa fa-chevron-down"></i></span>
        </div>
        <ul class="dropdown-menu" uib-dropdown-menu aria-labelledby="request-method-toggle">
          <li><a ng-click="clickRequestMethod('GET')">GET</a></li>
          <li><a ng-click="clickRequestMethod('POST')">POST</a></li>
        </ul>
      </div>
    </div>
  </div>
  <div class="form-group">
    <label>PATH VARIABLES</label>
    <div ng-if="!isEmpty(createEndpoint.pathVariables)">
      <span ng-repeat="pathVariable in createEndpoint.pathVariables"><span class="tag tag-grey" ng-bind="pathVariable"></span><span>&nbsp;</span></span>
    </div>
    <div ng-if="isEmpty(createEndpoint.pathVariables)">
      <span>-</span>
    </div>
  </div>
  <div class="form-group">
    <label>REQUEST PARAMS</label>
    <div class="input-addon right input-half">
      <input id="requestParams" name="requestParams" ng-model="$parent.requestParam" ng-change="changeRequestParam()"/>
      <div class="addon right" ng-click="clickRequestParam()">
        <span>&nbsp;<i class="fa fa-plus"></i></span>
      </div>
    </div>
    <div class="group-tag">
      <span ng-repeat="requestParameter in createEndpoint.requestParams"><span class="tag tag-grey" ng-bind="requestParameter"></span><span>&nbsp;</span></span>
    </div>
  </div>
  <div class="form-group">
    <label>RESPONSE BODY</label>
    <textarea id="responseBody" name="responseBody" ng-model="createEndpoint.responseBody" ng-class="{'error' : createEndpointForm.responseBody.$invalid && !valid}" required></textarea>
  </div>
</div>
<div class="modal-footer">
  <button class="btn btn-green btn-square" type="button" ng-click="clickCreateEndpoint()">CREATE</button>
  <button class="btn btn-grey btn-square" type="button" ng-click="clickDismiss()">CANCEL</button>
</div>