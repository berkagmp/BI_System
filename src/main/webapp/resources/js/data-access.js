let ctx;
let myLineChart;
let ctxByW;
let myLineChartByW;
let ctxByM;
let myLineChartByM;

function reGetData(){
	if($("#productId").val() == ''){
		alert("Please choose product first.");
		return false;
	}
	
	getData($("#productId").val(), $("#datetxt").val());
}

function showStats(pId, pNm) {
	$("#startDate").datepicker("setDate", new Date((new Date()).valueOf() - 1000 * 60 * 60 * 24 * 20).toISOString().split('T')[0]);
	$("#endDate").datepicker("setDate", new Date());
	$("#pId").val(pId);
	$("#pNm").val(pNm);
	// Set new default font family and font color to mimic Bootstrap's default styling
	Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
	Chart.defaults.global.defaultFontColor = '#292b2c';
	getStats(pId, pNm);
}

function getConfig(type) {
	let config = {
		type: type,
		data: {
			labels: "",
			datasets: [{
				label: "",
				lineTension: 0.3,
				backgroundColor: "rgba(2,117,216,0.2)",
				borderColor: "rgba(2,117,216,1)",
				pointRadius: 5,
				pointBackgroundColor: "rgba(2,117,216,1)",
				pointBorderColor: "rgba(255,255,255,0.8)",
				pointHoverRadius: 5,
				pointHoverBackgroundColor: "rgba(2,117,216,1)",
				pointHitRadius: 50,
				pointBorderWidth: 2,
				data: "",
			}],
		},
		options: {
			scales: {
				xAxes: [{
					time: {
						unit: 'date'
					},
					gridLines: {
						display: true
					},
					ticks: {
						//maxTicksLimit: 5
					}
				}],
				yAxes: [{
					ticks: {
						maxTicksLimit: 5
					},
					gridLines: {
						color: "rgba(0, 0, 0, .125)",
					}
				}],
			},
			legend: {
				display: false
			}
		}
	};
	return config;
}

function getStats(pId, pNm) {
	/*let type = $('input[name=period]:checked').val();
	console.log(type);*/
	if (myLineChart != null) myLineChart.destroy();

	ctx = document.getElementById("myAreaChart");
	myLineChart = new Chart(ctx, getConfig('line'));
	if (myLineChartByW != null) myLineChartByW.destroy();

	ctxByW = document.getElementById("myAreaChartByW");
	myLineChartByW = new Chart(ctxByW, getConfig('bar'));
	if (myLineChartByM != null) myLineChartByM.destroy();

	ctxByM = document.getElementById("myAreaChartByM");
	myLineChartByM = new Chart(ctxByM, getConfig('bar'));
	
	let url = "/api/item/getStatisticsByProductId?pId=" + pId + "&startDate=" + $("#startDate").val() + "&endDate=" + $("#endDate").val();
	console.log(url);
	$.get(url, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		var labels = new Array();
		var values = new Array();
		var i;
		for (i = 0; i < obj.length; i++) {
			labels[i] = obj[i].date;
			values[i] = obj[i].value;
		}
		let min = Math.floor(Math.min.apply(null, values));
		let max = Math.floor(Math.max.apply(null, values));
		$("#chart_title").html(decodeURIComponent(pNm));
		myLineChart.data.labels = labels;
		myLineChart.data.datasets[0].data = values;
		myLineChart.options.scales.yAxes[0].ticks.min = Math.floor(min - (min * 0.2));
		myLineChart.options.scales.yAxes[0].ticks.max = Math.floor(max + (max * 0.2));
		myLineChart.update();
		if ($.fn.DataTable.isDataTable("#modal_dataTable")) {
			$('#modal_dataTable').DataTable().clear().destroy();
		}
		$('#modal_dataTable').dataTable({
			data: obj,
			columns: [{
				data: "date"
			}, {
				data: "value"
			}, {
				data: "price"
			}, {
				data: "delivery_fee"
			}, {
				data: "seller"
			}, {
				data: "product_name",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href='" + oData.link + "' target='_blank'>" + oData.product_name + "</a>");
				}
			}],
			"order": [
				[0, 'asc']
			],
			"searching": false,
		});
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
	url = "/api/item/getStatisticsByProductIdByMonth?pId=" + pId;
	console.log(url);
	$.get(url, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		var labels = new Array();
		var values = new Array();
		var i;
		for (i = 0; i < obj.length; i++) {
			labels[i] = obj[i].date;
			values[i] = obj[i].value;
		}
		let min = Math.floor(Math.min.apply(null, values));
		let max = Math.floor(Math.max.apply(null, values));
		myLineChartByW.type = 'bar';
		myLineChartByM.data.labels = labels;
		myLineChartByM.data.datasets[0].data = values;
		myLineChartByM.options.scales.yAxes[0].ticks.min = Math.floor(min - (min * 0.2));
		myLineChartByM.options.scales.yAxes[0].ticks.max = Math.floor(max + (max * 0.2));
		myLineChartByM.update();
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
	url = "/api/item/getStatisticsByProductIdByWeek?pId=" + pId;
	console.log(url);
	$.get(url, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		let label;
		let labels = new Array();
		let values = new Array();
		for (i in obj) {
			label = new Array();
			label.push(obj[i].date);
			label.push(obj[i].start + "-" + obj[i].end);
			labels[i] = label;
			values[i] = obj[i].value;
		}
		console.log(labels);
		let min = Math.floor(Math.min.apply(null, values));
		let max = Math.floor(Math.max.apply(null, values));
		myLineChartByW.type = "bar";
		myLineChartByW.data.labels = labels;
		myLineChartByW.data.datasets[0].data = values;
		myLineChartByW.options.scales.yAxes[0].ticks.min = Math.floor(min - (min * 0.2));
		myLineChartByW.options.scales.yAxes[0].ticks.max = Math.floor(max + (max * 0.2));
		myLineChartByW.update();
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
	$("#chart_modal").modal({
		backdrop: 'static',
		keyboard: true
	});
}
let b_dataTable;
let p_dataTable;

function getBrandList() {
	$.get("/api/brand/list/?useyn=true", function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#b_dataTable")) {
			$('#b_dataTable').DataTable().clear().destroy();
		}
		b_dataTable = $('#b_dataTable').DataTable({
			data: obj,
			columns: [{
				data: "name",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href=javascript:getProductList(" + oData.id + ",'');>" + oData.name + "</a>");
				}
			}],
			"scrollY": '100vh',
			"scrollCollapse": true,
			"dom": '<"top"f<"clear">>rt<"bottom"p><"clear">',
			"paging": false,
			"language": {
				"info": "_START_~_END_ / _TOTAL_",
				"paginate": {
					"previous": "<",
					"next": ">"
				},
				"lengthMenu": '<select>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '<option value="-1">All</option>' + '</select>'
			}
		});
		$('#b_dataTable tbody').on('click', 'a', function() {
			b_dataTable.$('tr.selected').removeClass('selected');
			$(this).closest('tr').addClass('selected');
		});
	}).done(function() {
		location.href = "#page-top";
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
}

function getProductList(brandId) {
	$('#selected_b_id').val(brandId);
	$.get("/api/product/list/" + brandId + "?useyn=true", function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#p_dataTable")) {
			$('#p_dataTable').DataTable().clear().destroy();
		}
		if ($.fn.DataTable.isDataTable("#d_dataTable")) {
			$('#d_dataTable').DataTable().clear().destroy();
		}
		p_dataTable = $('#p_dataTable').DataTable({
			data: obj,
			columns: [{
				data: "name",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href=javascript:getData(" + oData.id + ",''); title=\'"+oData.keyword+"\'>" + oData.name + "</a>");
				}
			}, {
				data: "raw",
				"width": "20%",
				/*"render": function(data, type, row) {
					return '$' + data;
				}*/
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href=javascript:showEditModal(" + oData.id + "," + oData.raw + ",'" + encodeURIComponent(oData.name) + "');>$" + oData.raw + "</a>");
				}
			}, {
				data: "id",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<button class='btn btn-success btn-sm' onclick=showStats('" + oData.id + "','" + encodeURIComponent(oData.name) + "');>STATS</button>");
				}
			}],
			"scrollY": '100vh',
			"scrollCollapse": true,
			"dom": '<"top"f<"clear">>rt<"bottom"p><"clear">',
			"paging": false,
			"language": {
				//"info": "_PAGE_/_PAGES_",
				"info": "_START_~_END_ / _TOTAL_",
				"paginate": {
					"previous": "<",
					"next": ">"
				},
				"lengthMenu": '<select>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '<option value="-1">All</option>' + '</select>'
			}
		});
		$('#p_dataTable tbody').on('click', 'a', function() {
			p_dataTable.$('tr.selected').removeClass('selected');
			$(this).closest('tr').addClass('selected');
		});
	}).done(function() {
		location.href = "#page-top";
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
}
function hide(pId){
	if (!confirm('해당 데이터(오직 1건)을 안보이도록 하시겠습니까?\n* 블랙 리스트와는 별도로 단지 해당 날짜의 이 데이터만 안보이도록 처리됩니다')) {
		return false;
	} else {
		let data = {
			"useyn": false,
		};
		$.ajax({
			type: "PUT",
			headers: {
				"Content-Type": "application/json",
				"X-HTTP-Method-Override": "PUT"
			},
			url: "/api/item/"+pId,
			data: JSON.stringify(data),
			success: function(data) {
				alert("저장되었습니다.");
				reGetData();
			},
			error: function(data) {
				alert('죄송합니다. 잠시 후 다시 시도해주세요.');
				return false;
			}
		});
	}
}
function blacklist(pId, kId) {
	if (!confirm('블랙리스트에 등록하시겠습니까?\n\n* 블랙리스트에 등록된 제품들은 앞으로 수집되지 않으며, 기존에 수집된 데이터들은 노출되지 않습니다.\n\n* Setting > Blacklist 메뉴에서 복구할 수 있습니다.')) {
		return false;
	} else {
		let data = {
			"productId": pId,
			"keywordId": kId,
			"userId": userID
		};
		console.log(data);

		$.ajax({
			type: "POST",
			headers: {
				"Content-Type": "application/json",
				"X-HTTP-Method-Override": "POST"
			},
			url: "/api/blacklist/",
			data: JSON.stringify(data),
			success: function(data) {
				alert("저장되었습니다.");
				reGetData();
			},
			error: function(data) {
				alert('죄송합니다. 잠시 후 다시 시도해주세요.');
				return false;
			}
		});
	}
}

function getData(productId, date) {
	$("#productId").val(productId);
	let url = "/api/item/listByProductIdAndDate/" + productId;
	if (date != '') {
		url += "/?date=" + date;
	}
	$.get(url, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		if ($.fn.DataTable.isDataTable("#d_dataTable")) {
			$('#d_dataTable').DataTable().clear().destroy();
		}
		$("#datetxt").val(obj.lastBuildDate);
		$("#keyword").val(obj.keyword);
		var table = $('#d_dataTable').dataTable({
			data: obj.itemViews,
			columns: [{
				data: "title",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href='" + oData.link + "' target='_blank'>" + oData.title + "</a>");
				},
				"width": "20%"
			}, {
				data: "mallName",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					if (sData.includes('샵앤')) {
						$(nTd).closest('tr').css('background-color', '#DFF2BF').css('color', '#4F8A10');
					}
				}
			}, {
				data: "lprice",
				className: 'dt-center'
			}, {
				data: "deliveryFee",
				className: 'dt-center'
			}, {
				data: "sum",
				className: 'dt-center'
			}, {
				data: "raw",
				render: function(data, type, row) {
					return Math.round((Number(data) * 1.28) * Number($('input[name=currency]:checked').val()));
				},
				className: 'dt-center'
			}, {
				render: function(data, type, row) {
					// price + delivery -9900 - raw
					return row.sum - (9900 + Math.round((Number(row.raw) * 1.28) * Number($('input[name=currency]:checked').val())));
				}
			}, {
				data: "insertDate",
				render: function(data, type, row) {
					return moment(data).format("MM/DD/YYYY");
				},
				"width": "10%"
			}, {
				data: "productId",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<button class='btn btn-danger btn-sm' onclick='blacklist(" + oData.productId + "," + oData.keywordId + ")'>ADD</button>");
				},
				className: 'dt-center'
			}, {
				data: "id",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<button class='btn btn-warning btn-sm' onclick='hide(" + oData.id + ")'>HIDE</button>");
				},
				className: 'dt-center'
			}],
			"order": [
				[4, 'asc']
			],
			"scrollY": '100vh',
			"scrollCollapse": true,
			"dom": '<"top"f<"clear">>rt<"bottom"p><"clear">',
			"paging": false,
			"language": {
				//"info": "_PAGE_/_PAGES_",
				"info": "_START_~_END_ / _TOTAL_",
				"paginate": {
					"previous": "<",
					"next": ">"
				},
				"lengthMenu": '<select>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '<option value="-1">All</option>' + '</select>'
			}
		});
	}).done(function() {
		location.href = "#page-top";
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
}

function showEditModal(id, raw, name) {
	$("#m_id").val(id);
	$("#m_raw").val(raw);
	$("#m_name").html(name);
	$("#editModal").modal({
		backdrop: 'static',
		keyboard: false
	});
}
$(document).ready(function() {
	$('#m_raw').keypress(function(event) {
		var keycode = event.which;
		if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || keycode == 39 || (keycode >= 48 && keycode <= 57)))) {
			event.preventDefault();
		}
	});
	getBrandList();
	$('body').on('click', '#p-btn-update', function() {
		console.log($("#m_name").val());
		console.log($('input[name=m_useyn]:checked').val());
		if (isNaN($("#m_raw").val()) || $("#m_raw").val() == '') {
			$('#alert').html("Raw must be filled out with a number");
		} else {
			let data = {
				"raw": $("#m_raw").val()
			};
			console.log(data);
			$.ajax({
				type: 'PUT',
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": 'PUT'
				},
				url: "/api/product/raw/" + $("#m_id").val(),
				data: JSON.stringify(data),
				success: function(data) {
					$('#editModal').modal('hide');
					alert("저장되었습니다.");
					if ($("#selected_b_id").val() != '') getProductList($("#selected_b_id").val());
				},
				error: function(data) {
					alert('죄송합니다. 잠시 후 다시 시도해주세요.');
					return false;
				}
			});
		}
	});
	$("#datetxt").datepicker({
		dateFormat: "yy-mm-dd",
		onSelect: function() {
			reGetData();
		}
	});
	$("#startDate").datepicker({
		dateFormat: "yy-mm-dd"
	});
	$("#endDate").datepicker({
		dateFormat: "yy-mm-dd"
	});
	//showStats(5, 'TEST');
	$('body').on('click', '#currency', function() {
		reGetData();
	});
	$('body').on('click', '#datepicker', function() {
		reGetData();
	});
	$('body').on('click', '#goNaver', function() {
		if ($("#keyword").val() != '') {
			window.open("https://search.shopping.naver.com/search/all.nhn?pagingIndex=1&pagingSize=20&sort=price_asc&query=" + $("#keyword").val());
		}
	});
	$('body').on('click', '#modal_datepicker', function() {
		getStats($("#pId").val(), $("#pNm").val());
	});
	$( "#datetxt" ).datepicker({
		dateFormat: "yy-mm-dd",
		onSelect: function(){
			reGetData();
		}
	});
});