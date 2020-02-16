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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/datatables/buttons.dataTables.min.css"/> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/datatables/responsive.dataTables.min.css"/> 
<!-- <link rel="stylesheet" href="https://cdn.datatables.net/select/1.1.2/css/select.dataTables.min.css"/> -->
     
</head>

<body id="page-top">


	<%@ include file="./include/navigation.jsp"%>


	<div id="wrapper">

		<%@ include file="./include/sidebar.jsp"%>

		<div id="content-wrapper">
		
			<input type="hidden" id="selected_b_id" />

			<div class="container-fluid">

				<div class="card-group">
				
					<div class="card mb-3" style="max-width: 30rem;">
						<div class="card-header">
							<i class="fas fa-table"></i>
							BRAND
						</div>
						<div class="card-body">
							<div class="table-responsive">

								<table class="table table-striped table-sm" id="s_b_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
											<th>USEYN</th>
										</tr>
									</thead>
								</table>
								
							</div>
						</div>
					</div>
					
					<div class="card mb-3" style="max-width: 50rem;">
						<div class="card-header">
							<i class="fas fa-table"></i>
							PRODUCT
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-striped table-sm" id="s_p_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
											<th>RAW</th>
											<th>KEYWORD</th>
											<th>USEYN</th>
										</tr>
									</thead>
								</table>
							</div>
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
	
	<div class="modal fade" id="editModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 id="m_title" class="modal-title"></h4>
					<!-- <button type="button" class="btn btn-danger" data-dismiss="modal">&times;</button> -->
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" id="m_type" />
						<input type="hidden" id="m_id" />
						<br />
	  					<label for="m_name">Name</label><input type="text" id="m_name" class="form-control" placeholder="NAME" required />
	  					<br />
						<div class="radio">
						  <label><input type="radio" id="m_useyn" name="m_useyn" value="true" required /> USE</label>
						  <label><input type="radio" id="m_useyn" name="m_useyn" value="false" required /> NOT USE</label>
						</div>
						<br />
						<label id="m_raw_label" for="m_raw">Raw</label>
						<input type="text" id="m_raw" class="form-control" style="width:50%" required />
						<br />
						<label id="m_keyword_label" for="m_keyword">Keyword</label><input type="text" id="m_keyword" class="form-control" placeholder="KEYWORD" required />
						<p id="alert" style="color:#ff0033"></p>
					</div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" id="p-btn-update" class="btn btn-update">UPDATE</button>
					<button type="button" id="p-btn-delete" class="btn btn-warning">DELETE</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">CLOSE</button>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="./include/bottom.jsp"%>

	<%@ include file="./include/js.jsp"%>
	<script src="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.buttons.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.select.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/vendor/datatables/dataTables.responsive.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/data-access-setting.js"></script>
    <!-- <script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.min.js"></script> -->
</body>

</html>
