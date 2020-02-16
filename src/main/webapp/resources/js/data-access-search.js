$(document).ready(function() {
	$("#searchtext").focus();
});
$("#searchtext").keypress(function(event) {
	if (event.which == 13) {
		$('#searchbtn').click();
	}
});
$('body').on('click', '#searchbtn', function() {
	if ($("#searchtext").val().trim() == '') {
		alert('Please fill in the keyword');
		$("#searchtext").focus();
		return false;
	}
	console.log($('input[id=num]:checked').val());
	$("#alert").modal({
		backdrop: 'static',
		keyboard: false
	});
	$.get("/api/item/search/?keyword=" + $("#searchtext").val() + "&num=" + $('input[id=num]:checked').val(), function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#search_dataTable")) {
			$('#search_dataTable').DataTable().clear().destroy();
		}
		$('#search_dataTable').dataTable({
			data: obj.items,
			columns: [{
				data: "title",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href='" + oData.link + "' target='_blank'>" + oData.title + "</a>");
				},
				"width": "40%"
			}, {
				data: "mallName"
			}, {
				data: "lprice"
			}, {
				data: "deliveryFee"
			}, {
				data: "sum"
			}],
			"paging": false,
			"order": [
				[4, 'asc']
			],
		});
		$("#alert").modal('hide');
	}).done(function() {
		console.log("SECOND SUCCESS");
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
});