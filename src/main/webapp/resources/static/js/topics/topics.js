$(document).ready(function() {
	$('#addTopic').click(function() {
		addTopicForm();
	});

});


function addTopicForm(){
	var url="../addTopic/";
	$('#inner-topic-modal').load(url);
	$("#topic-modal").modal("show");
}