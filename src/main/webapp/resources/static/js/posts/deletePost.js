$(document).ready(function() {
	
	$('#cancel').click(function() {

		$('#post-modal').hide();
	});

	$('#deletePost').click(function(event) {
		
		deletePost(event.target.value);
	});

});

function deletePost(id) {

	var url = "../deletePost/" + id;
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	//var post = ${post};
	$.ajax({
		type : "DELETE",
		url : url,
		//data : JSON.stringify(postID),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
			xhr.setRequestHeader(header, token);
		}
	});
	$('#post-index').load("../showPostsForTopic/" + topicNumber);
	$('#post-modal').hide();

}