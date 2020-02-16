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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<style>
div.dataTables_wrapper div.dataTables_filter input {width:50%;}
</style>

</head>

<body id="page-top">

	<input type="hidden" id="pId" />
	<input type="hidden" id="pNm" />

	<%@ include file="./include/navigation.jsp"%>

	<div id="wrapper">

		<%@ include file="./include/sidebar.jsp"%>

		<div id="content-wrapper">

			<div class="container-fluid">

				<div class="card-group">
				
					<div class="card mb-3" style="max-width: 15rem;">
						<div class="card-header">
							<i class="fas fa-table"></i>
							BRAND
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-striped table-sm" id="b_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
					
					<div class="card mb-3" style="max-width: 25rem;">
						<div class="card-header">
							<i class="fas fa-table"></i>
							PRODUCT
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-striped table-sm" id="p_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
											<th>RAW</th>
											<th>STATS</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
					<input type="hidden" id="productId" />
					<input type="hidden" id="keyword" />
					
					<div class="card mb-3">
						<div class="card-header">
							<div class="row">
							    <div class="col-md-4">
							    	<input type="date" id="datetxt">
									<button id="datepicker" class="btn btn-dark">Search</button>
									<button id="goNaver" class="btn btn-primary">NAVER</button>
							    </div>
							    <div class="col-md-4">
							    	<!-- * RAW = sum - (9,900 + raw * 1.28 * currency)   -->
							    </div>
							    <div class="col-md-4">
							    	<div style="float: right;">
								    	Currency (&#8361;):   
										<input type="radio" id="currency" name="currency" class="radio-custom" value="780" checked />780	
										<input type="radio" id="currency" name="currency" class="radio-custom" value="820" />820
									</div>
							    </div>
							</div>
							
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-striped table-sm" id="d_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>NAME</th>
											<th>MALL</th>
											<th>PRICE</th>
											<th>DELIVERY</th>
											<th>SUM</th>
											<th>RAW</th>
											<th>PROFIT</th>
											<th>DATE</th>
											<th>BLACKLIST</th>
											<th>HIDE</th>
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
	
	<div class="modal fade" id="editModal">
		<div class="modal-dialog">
			<div class="modal-content">

				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">UPDATE RAW</h4>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" id="m_id" />
	  					<h5 id="m_name" class="modal-title"></h5>
	  					<br />
						<label id="m_raw_label" for="m_raw">RAW</label>
						<input type="text" id="m_raw" style="width:50%" required />
						<p id="alert" style="color:#ff0033"></p>
					</div>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" id="p-btn-update" class="btn btn-update">UPDATE</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">CLOSE</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="chart_modal">
		<div class="modal-dialog" style=" width: 98%;max-width:98%;height: 98%;margin-auto: 0;padding: 0;">
			<div class="modal-content" style="width: 100%;height: auto;min-height: 98%;border-radius: 0;">
				<!-- Modal body -->
				<div class="modal-body">
					<div id="content-wrapper">
				        <div class="container-fluid">
					
				          <!-- Area Chart Example-->
				          <div class="card mb-3">
				            <div class="card-header">
				              <i class="fas fa-chart-area"></i>
				              <sapn id="chart_title"></sapn>
				              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				              	<input type="date" id="startDate" />
				              	<input type="date" id="endDate" />
								<button id="modal_datepicker" class="btn btn-submit">Search</button>
								
								<!-- <input type="radio" id="period" value="d" checked="checked" />Daily
								<input type="radio" id="period" value="m" />Monthly -->
								
								<button type="button" class="btn btn-danger" data-dismiss="modal" style="float:right;">CLOSE</button>
				            </div>
				            <div class="card-body">
				              <i class="fas fa-chart-area"></i>&nbsp;&nbsp;DAILY
				              <canvas id="myAreaChart" width="100%" height="15px"></canvas>
				              <br />
				              <i class="fas fa-chart-area"></i>&nbsp;&nbsp;WEEKLY
				              <canvas id="myAreaChartByW" width="100%" height="15px"></canvas>
				              <br />
				              <i class="fas fa-chart-area"></i>&nbsp;&nbsp;MONTHLY
				              <canvas id="myAreaChartByM" width="100%" height="15px"></canvas>
				            </div>
				            <div class="card-body">
								<div class="table-responsive">
									<table class="table table-striped table-sm" id="modal_dataTable" width="100%" cellspacing="0">
										<thead>
											<tr>
												<th>DATE</th>
												<th>SUM</th>
												<th>PRICE</th>
												<th>DELIVERY</th>
												<th>SELLER</th>
												<th>PRODUCT</th>
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
	
	<input type="hidden" id="selected_b_id" />

	<%@ include file="./include/bottom.jsp"%>

	<%@ include file="./include/js.jsp"%>
	<script src="${pageContext.request.contextPath}/resources/js/data-access.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  	<script src="${pageContext.request.contextPath}/resources/vendor/chart.js/Chart.min.js"></script>
  	<script src="//cdn.datatables.net/plug-ins/1.10.19/dataRender/datetime.js"></script>
</body>

</html>
