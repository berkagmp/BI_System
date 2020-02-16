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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/jquery/jquery-ui.css">
     
</head>

<body id="page-top">

	<%@ include file="./include/navigation.jsp"%>

	<div id="wrapper">

		<%@ include file="./include/sidebar.jsp"%>

		<div id="content-wrapper">

			<div class="container-fluid">
			
				<!-- Breadcrumbs-->
				<ol class="breadcrumb">
					<li class="breadcrumb-item">
						<i class="fas fa-table"></i> Keyword
						<input type="date" id="datetxt" />
						<button id="datepicker" class="btn btn-dark">Search</button>
					</li>
				</ol>

				<div class="card-group">
				
					<div class="card mb-3" style="max-width: 30rem;">
						<div class="card-body">
							<div class="table-responsive">
								<i class="fa fa-calendar"></i><span id="r1_dataTable_txt"></span>

								<table class="table table-striped table-sm" id="r1_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>RANK</th>
											<th>KEYWORD</th>
											<th>FLUCTUATION</th>
										</tr>
									</thead>
								</table>
								
							</div>
						</div>
					</div>
					
					<div class="card mb-3" style="max-width: 30rem;">
						<div class="card-body">
							<div class="table-responsive">
								<i class="fa fa-calendar"></i><span id="r2_dataTable_txt"></span>

								<table class="table table-striped table-sm" id="r2_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>RANK</th>
											<th>KEYWORD</th>
											<th>FLUCTUATION</th>
										</tr>
									</thead>
								</table>
								
							</div>
						</div>
					</div>
					
					<div class="card mb-3" style="max-width: 30rem;">
						<div class="card-body">
							<div class="table-responsive">
								<i class="fa fa-calendar"></i><span id="r3_dataTable_txt"></span>
								
								<table class="table table-striped table-sm" id="r3_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>RANK</th>
											<th>KEYWORD</th>
											<th>FLUCTUATION</th>
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
	
	<div class="modal fade" id="data_modal">
		<div class="modal-dialog" style=" width: 60%;max-width:60%;height: 98%;margin-auto: 0;padding: 0;">
			<div class="modal-content" style="width: 98%;height: auto;min-height: 98%;border-radius: 0;">
				<!-- Modal body -->
				<div class="modal-body">
					<div id="content-wrapper">
				        <div class="container-fluid">
					
				          <!-- Area Chart Example-->
				          <div class="card mb-3">
				            <div class="card-header">
				              <i class="fas fa-chart-area"> </i> 
				              <sapn id="keyword_title"></sapn>
								<button type="button" class="btn btn-danger" data-dismiss="modal" style="float:right;">CLOSE</button>
				            </div>
				            <div class="card-body">
								<div class="table-responsive">
									<table class="table table-striped table-sm" id="modal_dataTable" width="100%" cellspacing="0">
										<thead>
											<tr>
												<th>DATE</th>
												<th>RANK</th>
												<th>FLUCTUSATION</th>
												<th>KEYWORD</th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
				          </div>
				         </div>
				         
					</div>
				</div>
				
				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-danger" data-dismiss="modal">CLOSE</button>
				</div>

			</div>
		</div>
	</div>
	
	<%@ include file="./include/bottom.jsp"%>

	<%@ include file="./include/js.jsp"%>
    <script src="${pageContext.request.contextPath}/resources/js/data-access-rank.js"></script>
</body>

</html>
