<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="body">
	<sec:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->

		<a href="../addTopic">Add Topic </a>
		<br>


		



	</sec:authorize>

</div>
