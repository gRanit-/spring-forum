<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

	<nav class="nav nav-justified" role="navigation"
		style="position:fixed; background-color:rgba(255,255,255,0.96);
-webkit-box-shadow: 0 1px 3px rgba(0,0,0,.25); // iOS <4.3 & Android <4.1
          box-shadow:0 1px 3px rgba(0,0,0,.25);


">
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<security:authorize access="isAnonymous()">
				<spring:url value="../login" var="loginUrl" />
				<li><a href="${loginUrl}">Log In</a></li>
				<spring:url value="../addUser" var="addUserUrl" />
				<li><a href="${addUserUrl}">Sign Up</a></li>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
			<spring:url value="../profile" var="profileUrl" />
				<li><a href="${profileUrl}">Profile</a></li>
				<spring:url value="../showAllTopics" var="showAllTopicsUrl" />
				<li><a href="${showAllTopicsUrl}">Show All Topics</a></li>
				<spring:url value="../j_spring_security_logout" var="logoutUrl" />
				<li><a href="javascript:formSubmit()">Log Out</a></li>
			</security:authorize>

		</ul>
	</div>

	</nav>
	<br>
	<br>
	<br>
</body>
<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>

		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>


</html>