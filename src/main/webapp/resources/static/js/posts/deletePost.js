$(document).ready(function() {
	
	$('#cancel').click(function() {
		$('#post-modal').hide();
	});
	
	$('#deletePost').click(function() {
		deletePost();
	});
	
});

function deletePost(){
	var postID=${post.id};
	var url="../deletePost/"+postID;
	var post=${post};
	$.ajax({	type : "DELETE",
		url:url,
		data:JSON.stringify(post),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
			
		}
	});
	$('#post-index').load("../showPostsForTopic/" + topicNumber);
	$('#post-modal').hide();

}