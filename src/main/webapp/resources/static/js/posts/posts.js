$(document).ready(function() {
	
	$('#addPost').click(function() {
		addPostForm();
	});
	
	$("button[name = 'deletePost']").click(function(event) {
		alert();
		deletePost(event.target.value);
	});
	$("button[name = 'editPost']").click(function(event) {
		editPost(event.target.value);
	});
	
});

function addPostForm() {
	
	var url="../addPostToTopic/";
	$('#inner-post-modal').load(url);
	$("#post-modal").modal("show");
	
}

function deletePost(id) {
	var url="../deletePost/"+id;
	$('#inner-post-modal').load(url);
	$("#post-modal").modal("show");
	
}

function editPost(id){
	var url="../editPost/"+id;
	$('#inner-post-modal').load(url);
	$("#post-modal").modal("show");
}
