function remove(pId) {
	$.ajax({
		type: "delete",
		headers: {
			"Content-Type": "application/json",
			"X-HTTP-Method-Override": "delete"
		},
		url: "/api/blacklist/" + pId,
		success: function(data) {
			alert("삭제되었습니다.");
			getDate();
		},
		error: function(data) {
			alert('죄송합니다. 잠시 후 다시 시도해주세요.');
			return false;
		}
	});
}

function getDate() {
	$.get("/api/blacklist/listWithItem", function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#r_dataTable")) {
			$('#r_dataTable').DataTable().clear().destroy();
		}
		p_dataTable = $('#r_dataTable').dataTable({
			data: obj,
			columns: [{
				data: "b_name"
			}, {
				data: "p_name"
			}, {
				data: "product_name",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href='" + oData.link + "' target='_blank'>" + oData.product_name + "</a>");
				}
			}, {
				data: "seller"
				// }, {
				// data: "product_id"
			}, {
				data: "listed_date",
				render: function(data, type, row) {
					return moment(data).format("MM/DD/YYYY");
				},
				"width": "10%"
			}, {
				data: "product_id",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<button class='btn btn-danger btn-sm' onclick='remove(" + oData.id + ")'>REMOVE</button>");
				}
			}]
		});
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
}
$(document).ready(function() {
	$(".allownumericwithoutdecimal").on("keypress keyup blur", function(event) {
		$(this).val($(this).val().replace(/[^\d].+/, ""));
		if ((event.which < 48 || event.which > 57)) {
			event.preventDefault();
		}
	});
	$("#datetxt").datepicker({
		dateFormat: "yymmdd",
		onSelect: function() {
			getDate();
		}
	});
	$('body').on('click', '#datepicker', function() {
		getDate();
	});
	getDate();
});