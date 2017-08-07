<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app="module.app">
<head>
<jsp:include page="../dependencies.jsp"/>
<script src="/js/module/module-controller.js"></script>
<script src="/js/module/module-app.js"></script>
</head>
<body ng-controller="moduleCtrl">
  <jsp:include page="../header.jsp"/>
</body>
</html>