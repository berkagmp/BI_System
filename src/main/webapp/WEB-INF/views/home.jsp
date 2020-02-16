<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>KEYORA - Dashboard</title>

<%@ include file="./include/css.jsp"%>

</head>

<body id="page-top">
	<form method="GET" id="myForm" action="${pageContext.request.contextPath}/rebatch/">

		<%@ include file="./include/navigation.jsp"%>

		<div id="wrapper">

			<%@ include file="./include/sidebar.jsp"%>

			<div id="content-wrapper">

				<div class="container-fluid">

					<!-- Breadcrumbs-->
					<ol class="breadcrumb">
						<li class="breadcrumb-item">
							<a href="#">Dashboard</a>
						</li>
						<li class="breadcrumb-item active">Overview</li>
					</ol>

					<!-- Icon Cards-->
					<div class="row">

						<div class="col-xl-3 col-sm-6 mb-3">
							<div class="card text-white bg-primary o-hidden h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-fw fa-comments"></i>
									</div>
									<div class="mr-5">
										ERROR :
										<c:out value="${fn:length(err)}" />
									</div>
								</div>
								<a class="card-footer text-white clearfix small z-1" href="javascript:handleErr();">
									<span class="float-left">Recollect Error Data</span>
									<span class="float-right">
										<i class="fas fa-angle-right"></i>
									</span>
								</a>
							</div>
						</div>
						<div class="col-xl-3 col-sm-6 mb-3">
							<div class="card text-white bg-warning o-hidden h-100">
								<div class="card-body">
									<div class="card-body-icon">
										<i class="fas fa-fw fa-list"></i>
									</div>
									<div class="mr-5">
										<fmt:parseDate value="${from[0]}" var="fromDate" pattern="yyyy-MM-dd"/>
										<fmt:parseDate value="${to[0]}" var="toDate" pattern="yyyy-MM-dd"/>
										Collected Data <br />
										&emsp;from <strong><fmt:formatDate value="${fromDate}" pattern="yyyy.MM.dd"/></strong>
										<br />
										&emsp;to <strong><fmt:formatDate value="${toDate}" pattern="yyyy.MM.dd"/></strong>
									</div>
								</div>
								<!-- <a class="card-footer text-white clearfix small z-1" href="#">
								<span class="float-left">View Details</span>
								<span class="float-right">
									<i class="fas fa-angle-right"></i>
								</span>
							</a> -->
							</div>
						</div>
					</div>

					<!-- DataTables Example -->
					<div class="card mb-3" style="margin-top: 30px;">
						<div class="card-header">
							<i class="fas fa-table"></i>
							Data Collecting History
						</div>
						<div class="card-body">
							<div class="table-responsive" style="max-width: 60rem;">
								<table class="table table-bordered" id="dash_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>DATE</th>
											<th>COUNT</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>

				</div>
				<!-- /.container-fluid -->

				<%@ include file="./include/footer.jsp"%>

			</div>
			<!-- /.content-wrapper -->

		</div>
		<!-- /#wrapper -->

		<div class="modal fade" id="alert">
			<div class="modal-dialog">
				<div class="modal-content">

					<!-- Modal body -->
					<div class="modal-body">
						<div class="form-group">Please wait.</div>
					</div>

				</div>
			</div>
		</div>

		<%@ include file="./include/bottom.jsp"%>

		<%@ include file="./include/js.jsp"%>

		<script>
			function handleErr() {
				$("#alert").modal({
		            backdrop: 'static',
		            keyboard: false
		        });
				
				document.getElementById('myForm').submit();
			}

			$(document).ready(function() {
				$.get("/api/item/getStatistics", function(data, status) {
					const obj = jQuery.parseJSON(JSON.stringify(data));

					$('#dash_dataTable').dataTable({
						data : obj,
						columns : [ {
							data : 0
						}, {
							data : 1
						} ],
						"searching" : false,
						"order": [[ 0, 'desc' ]],
					});

				}).done(function() {
					console.log("SECOND SUCCESS");
				}).fail(function() {
					alert("ERROR");
				}).always(function() {
					console.log("FINISHED");
				})
			});
		</script>

	</form>
</body>

</html>
