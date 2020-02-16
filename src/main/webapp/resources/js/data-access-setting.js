let s_b_dataTable;
let s_p_dataTable;
let s_b_dataTable_arr;
let s_p_dataTable_arr;

function s_getBrandList() {
	$.get("/api/brand/list", function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		s_b_dataTable_arr = obj;
		if ($.fn.DataTable.isDataTable("#s_b_dataTable")) {
			$('#s_b_dataTable').DataTable().clear().destroy();
		}
		if ($.fn.DataTable.isDataTable("#s_p_dataTable")) {
			$('#s_p_dataTable').DataTable().clear().destroy();
		}
		s_b_dataTable = $('#s_b_dataTable').DataTable({
			data: obj,
			columns: [{
				data: "name",
				"fnCreatedCell": function(nTd, sData, oData, iRow, iCol) {
					$(nTd).html("<a href=javascript:s_getProductList('" + oData.id + "');>" + oData.name + "</a>");
				}
			}, {
				data: "useyn",
				render: function(data, type, row) {
					if (data == true) return 'Y';
					else return 'N';
				}
			}],
			"scrollY": '100vh',
			"scrollCollapse": true,
			dom: 'Blrt', // Needs button container
			buttons: [{
				text: 'Create',
				name: 'create',
				className: 's_b_btn_create',
			}, {
				extend: 'selected', // Bind to Selected row
				text: 'Edit',
				name: 'edit',
				className: 's_b_btn_edit',
			}],
			select: 'single',
			responsive: true,
			"paging": false
		});
	}).done(function() {
		location.href = "#page-top";
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	});
}

function s_getProductList(brandId) {
	$('#selected_b_id').val(brandId);
	$.get("/api/product/list/" + brandId, function(data, status) {
		const obj = jQuery.parseJSON(JSON.stringify(data));
		s_p_dataTable_arr = obj;
		if ($.fn.DataTable.isDataTable("#s_p_dataTable")) {
			$('#s_p_dataTable').DataTable().clear().destroy();
		}
		s_p_dataTable = $('#s_p_dataTable').DataTable({
			data: obj,
			columns: [{
				data: "name"
			}, {
				data: "raw",
				"width": "10%",
				"render": function(data, type, row) {
					return '$' + data;
				}
			}, {
				data: "keyword"
			}, {
				data: "useyn",
				"width": "10%",
				render: function(data, type, row) {
					if (data == true) return 'Y';
					else return 'N';
				}
			}],
			"scrollY": '100vh',
			"scrollCollapse": true,
			dom: 'Blrt', // Needs button container
			select: 'single',
			responsive: true,
			buttons: [{
				text: 'Create',
				name: 'create',
				className: 's_p_btn_create',
			}, {
				extend: 'selected', // Bind to Selected row
				text: 'Edit',
				name: 'edit',
				className: 's_p_btn_edit',
			}],
			"paging": false
		});
	}).done(function() {
		location.href = "#page-top";
	}).fail(function() {
		alert("ERROR");
	}).always(function() {
		console.log("FINISHED");
	})
}

function showModal(id, type, arr) {
	if (type == 'PRODUCT') {
		$("#m_raw").show();
		$("#m_raw_label").show();
		$("#m_keyword").show();
		$("#m_keyword_label").show();
	} else {
		$("#m_raw").hide();
		$("#m_raw_label").hide();
		$("#m_keyword").hide();
		$("#m_keyword_label").hide();
	}
	if (id > 0) {
		$('#p-btn-update').text('UPDATE');
		$("#m_title").text('UPDATE ' + type);
	} else {
		$('#p-btn-update').text('CREATE');
		$("#m_title").text('CREATE ' + type);
	}
	$('#alert').html("");
	$("#m_type").val(type);
	$("#m_id").val("");
	$("#m_name").val("");
	$("#m_raw").val("");
	$("#m_keyword").val("");
	$("input[name=m_useyn][value=true]").prop('checked', false);
	$("input[name=m_useyn][value=false]").prop('checked', false);
	$("#editModal").modal({
		backdrop: 'static',
		keyboard: false
	});
	if (id > 0) {
		arr.forEach(element => {
			if (id == element.id) {
				console.log(element);
				$("#m_id").val(element.id);
				$("#m_name").val(element.name);
				if (type == 'PRODUCT') {
					$("#m_raw").val(element.raw);
					$("#m_keyword").val(element.keyword);
				}
				if (element.useyn == true) {
					$("input[name=m_useyn][value=true]").prop('checked', 'checked');
				} else {
					$("input[name=m_useyn][value=false]").prop('checked', 'checked');
				}
				return;
			}
		});
	}
}
$(document).ready(function() {
	s_getBrandList();
	$('body').on('click', '.s_b_btn_create', function(data) {
		showModal(0, 'BRAND', null);
		$("#p-btn-delete").hide();
	});
	$('body').on('click', '.s_b_btn_edit', function() {
		var rowData = s_b_dataTable.row({
			selected: true
		}).data();
		showModal(rowData.id, 'BRAND', s_b_dataTable_arr);
		$("#p-btn-delete").show();
	});
	$('body').on('click', '.s_p_btn_create', function() {
		showModal(0, 'PRODUCT', null);
		$("#p-btn-delete").hide();
	});
	$('body').on('click', '.s_p_btn_edit', function() {
		var rowData = s_p_dataTable.row({
			selected: true
		}).data();
		showModal(rowData.id, 'PRODUCT', s_p_dataTable_arr);
		$("#p-btn-delete").show();
	});
	$('#m_raw').keypress(function(event) {
		var keycode = event.which;
		if (!(event.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || keycode == 39 || (keycode >= 48 && keycode <= 57)))) {
			event.preventDefault();
		}
	});
	$('body').on('click', '#p-btn-delete', function() {
		if (!confirm('삭제하시겠습니까?\n\n* Brand의 경우 Product 들도 함께 삭제됩니다.')) {
			return false;
		} else {
			let rest_url;
			if ($("#m_type").val() == 'BRAND') {
				rest_url = '/api/brand/';
			} else if ($("#m_type").val() == 'PRODUCT') {
				rest_url = '/api/product/';
			}
			
			rest_url += $("#m_id").val();
			$.ajax({
				type: "delete",
				headers: {
					"Content-Type": "application/json",
					"X-HTTP-Method-Override": "delete"
				},
				url: rest_url,
				success: function(data) {
					$('#editModal').modal('hide');
					alert("삭제하었습니다.");
					s_getBrandList();
					if ($("#selected_b_id").val() != '') s_getProductList($("#selected_b_id").val());
				},
				error: function(data) {
					alert('죄송합니다. 잠시 후 다시 시도해주세요.');
					return false;
				}
			});
		}
	});
	$('body').on('click', '#p-btn-update', function() {
		if (!confirm('수정하시겠습니까?\n\n* Brand의 경우 Product 들의 Useyn도 함께 수정됩니다.')) {
			return false;
		} else {
			console.log($("#m_name").val());
			console.log($('input[name=m_useyn]:checked').val());
			if ($("#m_name").val() == '') {
				$('#alert').html("Name must be filled out");
			} else if ($("#m_useyn:checked").length == 0) {
				$('#alert').html("Useyn must be selected");
			} else if ($("#m_type").val() == 'PRODUCT' && (isNaN($("#m_raw").val()) || $("#m_raw").val() == '')){
				$('#alert').html("Raw must be filled out with a number");
			} else  if ($("#m_type").val() == 'PRODUCT' && $("#m_keyword").val() == '') {
				$('#alert').html("Keyword must be filled out");
			} else {
				let data = {
					"name": $("#m_name").val(),
					"useyn": $('input[name=m_useyn]:checked').val()
				};
				let rest_url;
				if ($("#m_type").val() == 'BRAND') {
					rest_url = '/api/brand/';
				} else if ($("#m_type").val() == 'PRODUCT') {
					if ($("#m_id").val() < 1) data.brandId = $("#selected_b_id").val();
					data.keyword = $("#m_keyword").val();
					data.raw = $("#m_raw").val();
					rest_url = '/api/product/';
				}
				
				let method;
				if ($("#m_id").val() > 0) {
					method = 'PUT';
					rest_url += $("#m_id").val();
				} else {
					method = 'POST';
				}
				console.log(method);
				console.log(rest_url);
				console.log(data);
				$.ajax({
					type: method,
					headers: {
						"Content-Type": "application/json",
						"X-HTTP-Method-Override": method
					},
					url: rest_url,
					data: JSON.stringify(data),
					success: function(data) {
						$('#editModal').modal('hide');
						alert("저장되었습니다.");
						s_getBrandList();
						if ($("#selected_b_id").val() != '') s_getProductList($("#selected_b_id").val());
					},
					error: function(data) {
						alert('죄송합니다. 잠시 후 다시 시도해주세요.');
						return false;
					}
				});
			}
		}
	});
});