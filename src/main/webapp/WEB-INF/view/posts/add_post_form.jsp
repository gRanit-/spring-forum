<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>	
	 <div class="modal-body">
	<form:form action="../createPostInTopic/" method="POST" modelAttribute="post" id="postForm">
	 <table class="addPostTable">

        <tr>
            <td><label for="text">Post here</label></td>
            <td><form:input path="text" id="text" cssClass="form-control"/></td>
        </tr>

        <tr>
       
        <td></td>
        </tr>
    </table>
	</form:form>
	
	</div>
	<button class="btn btn-default" id="submitPost" >submit</button>
	
	<script>
	$(document).ready(function() {
		
		$('#submitPost').click(function(event) {
			event.preventDefault();
			onSubmitPost();
	});
	
	});
	
		
	function onSubmitPost(){
		  var token = $("meta[name='_csrf']").attr("content");
		  var header = $("meta[name='_csrf_header']").attr("content");
		var url="../addPostToTopic/"+topicNumber;
		var text=$('#text').val();
		var topic_id=topicNumber;
		var post= {"text":text,"topic_id":topic_id};
		var ar={"text":text,"topicID":topicNumber};
		//alert(post);
		//post=post.serialize();
		//alert("beforepost");
		//$.post(url,{"text":text,"topicID":topicNumber},function(){alert("Done");});
		//('#inner-post-modal')
		//alert("afterpost");
	   $.ajax({
             type: "POST",
             url: url,
             data: text,
             beforeSend: function(xhr) {
            	 
            	             xhr.setRequestHeader("Accept", "application/json");
            	 
            	             xhr.setRequestHeader("Content-Type", "application/json");
            	             xhr.setRequestHeader(header, token);
            	 
            	         },
           	success: function(){
           		$('#post-index').load("../showPostsForTopic/"+topicNumber);
           		$("#post-modal").modal("hide");
           	}
	   
            });	
		
		
		
	}
	
	</script>