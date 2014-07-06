<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
	
	 <div class="modal-body">
	<form:form action="../addPostToTopic/${id}" method="POST" modelAttribute="post" id="postForm">
	 <table class="addPostTable">

        <tr>
            <td><label for="text">Post here</label></td>
            <td><form:input path="text" id="text" cssClass="form-control input"/></td>
        </tr>

        <tr>
        <td></td>
        <td><button class="btn btn-default" id="submitPost" >submit</button></td>
        </tr>
    </table>
	</form:form>
	
	</div>
	
	
	<script>
	$(document).ready(function() {
		
		$('#postForm').submit(function(event) {
			event.preventDefault();
			onSubmitPost();
	});
	
	});
	
		
	function onSubmitPost(){
		
		var url="../createPostInTopic/";
		var text=$('#text').val();
		var topic_id=topicNumber;
		var post= {"text":text,"topic_id":topic_id};
		//alert(post);
		//post=post.serialize();
		$.post(url,{"text":text,"topicID":topicNumber);
		//('#inner-post-modal')
		
	 /*  $.ajax({
             type: "POST",
             url: url,
           
             data: JSON.stringify(post),
             beforeSend: function(xhr) {
            	 
            	             xhr.setRequestHeader("Accept", "application/json");
            	 
            	             xhr.setRequestHeader("Content-Type", "application/json");
            	 
            	         }
            });	*/
		$("#post-modal").modal("hide");
		$('#post-index').load("../showPostsForTopic/"+topicNumber);
		
	}
	
	</script>