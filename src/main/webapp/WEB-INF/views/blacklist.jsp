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
						<i class="fas fa-table"></i> Black List
					</li>
				</ol>

				<div class="card-group">
				
					<div class="card mb-3">
						<div class="card-body">
							<div class="table-responsive">

								<table class="table table-striped table-sm" id="r_dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>BRAND</th>
											<th>PRODUCT</th>
											<th>NAVER PRODUCT NAME</th>
											<th>SELLER</th>
											<th>REGISTER_DATE</th>
											<th>FUNC</th>
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
	
	<%@ include file="./include/bottom.jsp"%>

	<%@ include file="./include/js.jsp"%>
	<script src="${pageContext.request.contextPath}/resources/js/data-access-blacklist.js"></script>
</body>

</html>
