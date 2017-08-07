<div ng-if="isLoading('page')" class="loading">
  <div class="loading-icon">
    <span>
      <i class="fa fa-spinner fa-pulse fa-3x"></i>
    </span>
  </div>
</div>
<div ng-if="!isEmpty(session)" class="header">
  <div class="container">
    <div class="col-sm-12">
      <div class="header-container">
        <div class="header-block">
          <div class="header-option">
            <span>
              <i class="fa fa-user"></i>&nbsp;<span ng-bind="session.username"></span>&nbsp;<i class="fa fa-caret-down"></i>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>