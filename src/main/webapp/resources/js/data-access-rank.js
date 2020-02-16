function getStatis(keyword) {
	let url = "/api/rank/getStats/" + keyword;
	console.log(url);
	$("#keyword_title").html(keyword + " HISTORY");
	$.get(url, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#modal_dataTable")) {
			$('#modal_dataTable').DataTable().clear().destroy();
		}
		$('#modal_dataTable').dataTable({
			data: obj,
			columns: [{
				data: "date",
				className: 'dt-center'
			}, {
				data: "rank",
				className: 'dt-center'
			}, {
				data: "fluctuation",
				"width": "10%",
				"render": function(data, type, row) {
					return data.replace("상승", "<i class='fas fa-angle-up' style='font-size:20px;color:red'></i> ")
							.replace("하락", "<i class='fas fa-angle-down' style='font-size:20px;color:blue'></i> ")
							.replace("진입", "<span style='color:red;'>NEW</span>")
							.replace("유지", "<span class=\"glyphicon\">&#x2212;</span>");
				},
				className: 'dt-center'
			}, {
				data: "keyword"
			}, ],
			"order": [
				[0, 'desc']
			],
			"searching": false,
		});
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
	$("#data_modal").modal({
		backdrop: 'static',
		keyboard: true
	});
}

function getDataList() {
	if ($.fn.DataTable.isDataTable("#r1_dataTable")) $("#r1_dataTable").DataTable().clear().destroy();
	if ($.fn.DataTable.isDataTable("#r2_dataTable")) $("#r2_dataTable").DataTable().clear().destroy();
	if ($.fn.DataTable.isDataTable("#r3_dataTable")) $("#r3_dataTable").DataTable().clear().destroy();
	let s1 = $("#datetxt").val();
	let s2 = new Date((new Date(s1)).valueOf() - 1000 * 60 * 60 * 24 * 1).toISOString().split('T')[0];
	let s3 = new Date((new Date(s1)).valueOf() - 1000 * 60 * 60 * 24 * 2).toISOString().split('T')[0];
	console.log(s1);
	console.log(s2);
	console.log(s3);
	getData(s1, "#r1_dataTable");
	getData(s2, "#r2_dataTable");
	getData(s3, "#r3_dataTable");
	$("#r1_dataTable_txt").html("&nbsp;&nbsp;&nbsp;" + s1);
	$("#r2_dataTable_txt").html("&nbsp;&nbsp;&nbsp;" + s2);
	$("#r3_dataTable_txt").html("&nbsp;&nbsp;&nbsp;" + s3);
}

function getData(date, table) {
	$.get("/api/rank/listByDate?date=" + date, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		$(table).dataTable({
			data: obj,
			columns: [{
				data: "rank",
				className: 'dt-center'
			}, {
				data: "keyword",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href=javascript:getStatis('" + encodeURIComponent(oData.keyword) + "');>" + oData.keyword + "</a>");
				}
			}, {
				data: "fluctuation",
				"render": function(data, type, row) {
					return data.replace("상승", "<i class='fas fa-angle-up' style='font-size:20px;color:red'></i> ")
							.replace("하락", "<i class='fas fa-angle-down' style='font-size:20px;color:blue'></i> ")
							.replace("진입", "<span style='color:red;'>NEW</span>")
							.replace("유지", "<span class=\"glyphicon\">&#x2212;</span>");
				},
				className: 'dt-center'
			}],
			"dom": '<"top"f<"clear">>rt<"bottom"p><"clear">',
			"paging": false,
			"searching": false,
			"language": {
				// "info": "_PAGE_/_PAGES_",
				"info": "_START_~_END_ / _TOTAL_",
				"paginate": {
					"previous": "<",
					"next": ">"
				},
				"lengthMenu": '<select>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '<option value="-1">All</option>' + '</select>'
			}
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
		dateFormat: "yy-mm-dd",
		onSelect: function() {
			getDataList();
		}
	}).datepicker("setDate", new Date());
	$('body').on('click', '#datepicker', function() {
		getDataList();
	});
	getDataList();
});