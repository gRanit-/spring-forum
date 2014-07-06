$(document).ready(function() {
	
	$('#addPost').click(function() {
		addPostForm();
	});
	
	$('.deletePost').click(function() {
		addPostForm();
	});
	
});

function addPostForm() {
	
	var url="../addPostToTopic/"+topicNumber;
	$('#inner-post-modal').load(url);

	$("#post-modal").modal("show");
	
}

function deletePost() {
	
	var url="../addPostToTopic/"+topicNumber;
	$('#inner-post-modal').load(url);

	$("#post-modal").modal("show");
	
}
