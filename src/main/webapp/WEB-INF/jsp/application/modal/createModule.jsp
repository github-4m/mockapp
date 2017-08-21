<div class="modal-header">
  <div class="modal-title">
    <span>Create Module</span>
  </div>
</div>
<div class="modal-body" ng-form="createModuleForm">
  <div class="form-group">
    <label>NAME</label>
    <input id="name" name="name" placeholder="Enter your application name" ng-model="createModule.name" ng-class="{'error' : createModuleForm.name.$invalid && !valid}" required/>
  </div>
  <div class="form-group">
    <label>CONTEXT PATH</label>
    <input id="context-path" name="contextPath" placeholder="Enter your application context path" ng-model="createModule.contextPath" ng-class="{'error' : createModuleForm.contextPath.$invalid && !valid}" required/>
  </div>
</div>
<div class="modal-footer">
  <button class="btn btn-blue btn-square" type="button" ng-click="clickCreateModule()">CREATE</button>
  <button class="btn btn-grey btn-square" type="button" ng-click="clickDismiss()">CANCEL</button>
</div>