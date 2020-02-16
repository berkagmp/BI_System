<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>KEYORA - Dashboard</title>

<%@ include file="./include/css.jsp"%>

<style>
div.dataTables_wrapper div.dataTables_filter input {width:50%;}
</style>

</head>

<body id="page-top">

	<%@ include file="./include/navigation.jsp"%>

	<div id="wrapper">

		<%@ include file="./include/sidebar.jsp"%>

		<div id="content-wrapper">

			<div class="container-fluid">

				<div class="card-group">
				
					<div class="card mb-3">
						<div class="card-header">
							<i class="fas fa-table"></i>
							SEARCH<span style="margin-right:20px;"></span>
							<input type="text" id="searchtext" />&emsp;
							Amount:  
							<input type="radio" id="num" name="num" class="radio-custom" value="100" checked />100
							<input type="radio" id="num" name="num" class="radio-custom" value="70" />70
							<input type="radio" id="num" name="num" class="radio-custom" value="50" />50
							<input type="radio" id="num" name="num" class="radio-custom" value="30" />30
							<input type="radio" id="num" name="num" class="radio-custom" value="20" />20
							<input type="radio" id="num" name="num" class="radio-custom" value="10" />10

							<button class="btn btn-primary" id="searchbtn" style="float:right;">SEARCH</button>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-sm" id="search_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
											<th>MALL</th>
											<th>PRICE</th>
											<th>DELIVERY</th>
											<th>SUM</th>
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
	
<!-- 	<div class="modal fade" id="alert">
		<div class="modal-dialog">
			<div class="modal-content">

				Modal body
				<div class="modal-body">
					<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
					<span >Loading...</span>
				</div>

			</div>
		</div>
	</div> -->
	
	<div class="modal fade bd-modal-lg" data-backdrop="static" data-keyboard="false" tabindex="-1" id="alert">
	    <div class="modal-dialog modal-sm">
	        <div class="modal-content" style="width: 100px">
	            <span class="fa fa-spinner fa-pulse fa-5x fa-fw"></span>
	        </div>
	    </div>
	</div>

	<%@ include file="./include/bottom.jsp"%>

	<%@ include file="./include/js.jsp"%>
	<script src="${pageContext.request.contextPath}/resources/js/data-access-search.js"></script>
</body>

</html>
