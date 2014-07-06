$(document).ready(function() {
		$('#cancel').click(function(event){
			$("#post-modal").modal("hide");
		});
		$('#submitPost').click(function(event) {
			event.preventDefault();
			onSubmitPost();
		});

	});

	function onSubmitPost() {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var url = "../addPostToTopic/" + topicNumber;
		var text = $('#text').val();

		$.ajax({
			type : "POST",
			url : url,
			data : text,
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Accept", "application/json");
				xhr.setRequestHeader("Content-Type", "application/json");
				xhr.setRequestHeader(header, token);

			},
			success : function(post) {
				
				$('#post-index').load("../showPostsForTopic/" + topicNumber);
				$("#post-modal").modal("hide");
			}

		});
		

	}