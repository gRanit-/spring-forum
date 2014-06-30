<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<tiles:insertDefinition name="baseTemplate">
    <tiles:putAttribute name="body">
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<sec:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->
		
		<a href="../addTopic">Add Topic </a>
		<br>
		<a href="../showAllTopics">"Show All Topics" </a>
		
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				User : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
			</h2>
			
		</c:if>
	
		

	</sec:authorize>
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		<h2><a href="../login">Login </a></h2><br>
		<h2><a href="../addUser">"Register" </a></h2>
	</c:if>
</body>
  </tiles:putAttribute>
</tiles:insertDefinition>
</html>