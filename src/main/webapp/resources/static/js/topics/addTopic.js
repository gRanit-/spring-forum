$(document).ready(function() {
	$('#cancel').click(function(event) {
		$("#topic-modal").modal("hide");
	});
	$('#submitTopic').click(function(event) {
		event.preventDefault();
		onSubmitTopic();
	});

});

function onSubmitTopic() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var url = "../addTopic/";
	var text = $('#text').val();
	var title = $('#title').val();
	var topic = { "title" : title, "text" : text};
	$.ajax({
		type : "POST",
		url : url,
		data : JSON.stringify(topic),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
			xhr.setRequestHeader(header, token);

		},
		success : function(post) {

			
		}

	});
	//$.load("../showAllTopics/");
	$("#topic-modal").modal("hide");
}