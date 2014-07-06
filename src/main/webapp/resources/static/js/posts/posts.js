$(document).ready(function() {
	
	$('#addPost').click(function() {
		addPostForm();
	});
	
	$('.deletePost').click(function(event) {
		deletePost();
	});
	
});

function addPostForm() {
	
	var url="../addPostToTopic/";
	$('#inner-post-modal').load(url);
	$("#post-modal").modal("show");
	
}

function deletePost(number) {
	var url="../deletePost/"+number;
	$('#inner-post-modal').load(url);
	
	
	/*.ajax({	type : "GET",
		url:url,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
			
		}
	});*/

	$("#post-modal").modal("show");
	
}

function editPost(){
	var url="../editPost/"+number;
	$('#inner-post-modal').load(url);
}
