<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
</head>
<body>
	<b>
	
	${topic.title}<br>
	
	</b>
	<c:forEach var="post" items="${posts}">
	<table id="post${post.id}">
	<tr>
	
		<td>${post.creationDate}</td>
		<td>${post.author}</td>
		<td>${post.text}</td>
		</tr>
	</table>
	</c:forEach>
	
	<form:form action="../addPostToTopic/${topic.id }" method="POST" modelAttribute="post">
	 <table class="addPostTable">

        <tr>
            <td><label for="text">email</label></td>
            <td><form:input path="text" id="text" cssClass="form-control"/></td>
        </tr>

        <tr>
        <td></td>
        <td><button type="submit">submit</button></td>
        </tr>
    </table>
	</form:form>
</body>
</html>