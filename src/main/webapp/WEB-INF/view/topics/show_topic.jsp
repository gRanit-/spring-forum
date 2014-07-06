<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<script src="<c:url value="/resources/static/js/posts/posts.js"/>"></script>
</head>
<body>
	
	<br>


	<div class="page-header">
		<h1>${topic.title}</h1>
	</div>
	<button class="btn btn-default" id="addPost" value="${topic.id}">Add Post</button>
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<th>Author</th>
				<th>Created at</th>
				<th>&nbsp;</th>

			</tr>
		</thead>
		<tbody class="post-index" id="post-index" value="${topic.id}">
			

		</tbody>
	</table>


	<div id="post-modal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="inner-post-modal" class="modal-body" value="${topic.id}"></div>
			</div>
		</div>
	</div>


</body>
</html>

<script>
var topicNumber=${topic.id};
$(document).ready(function() {

$('#post-index').load("../showPostsForTopic/${topic.id}");
});
</script>