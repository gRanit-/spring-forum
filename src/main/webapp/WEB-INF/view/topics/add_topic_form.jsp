
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<head>
<script src="<c:url value="/resources/static/js/topics/addTopic.js"/>"></script>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
</head>
<div class="modal-body">
<form:form action="addTopic" method="POST" modelAttribute="topic" id="topicForm">
	<table class="addTopicTable">
		<tr>
			<td><label for="title">Title</label></td>
			<td><form:input path="title" id="title" cssClass="form-control" />
			</td>
		</tr>
		<tr>
			<td><label for="text">Text</label></td>
			<td><form:input path="text" id="text" cssClass="form-control" />
			</td>
		</tr>
		<tr>
			<td></td>
			
		</tr>
	</table>

</form:form>
</div>
<div class="modal-footer">
	<button class="btn btn-default" id="submitTopic">Submit</button>
	<button class="btn btn-danger" id="cancel">Cancel</button>
</div>