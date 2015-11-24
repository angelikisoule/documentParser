<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Parser</title>
	<link href="<c:url value="/resources/bootstrap-3.3.5/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container-fluid">
	<div class="page-header"><h1>List Of Files</h1></div>
	<h4>Files txt : ${countTxt}</h4>
	<h4>Files xls : ${countXls}</h4>
		<div class="row">
			<div class="col-xs-12">
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Id</th>
								<th>Count Elements</th>
								<th>Stored Elements</th>
								<th>Filename</th>
								<th>Total Elements</th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allStats}" var="stats">
								<tr>
									<td>${stats.id}</td>
									<td>${stats.countElements}</td>
									<td>
										<c:choose>
											<c:when test="${not empty stats.elements}">${stats.elements}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</td>
									<td>${stats.filename}</td>
									<td>
										<c:choose>
											<c:when test="${not empty stats.totalElements}">${stats.totalElements}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>									
									</td>
									<td>${stats.type}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<nav>
					<ul class="pagination pagination-lg">
						<jsp:include page="../pagination.jsp" />
					</ul>
				</nav>
			</div>
		</div>
	</div>	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js" />"></script>
</body>
</html>