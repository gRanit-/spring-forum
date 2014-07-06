<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<head>
<script src="<c:url value="/resources/static/js/posts/addPost.js"/>"></script>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
</head>

<div class="modal-body">

	<b>Post here</b>
	<textarea class="form-control" rows="3" id="text"></textarea>

</div>

<div class="modal-footer">
	<button class="btn btn-default" id="submitPost">Submit</button>
	<button class="btn btn-danger" id="cancel">Cancel</button>
</div>
