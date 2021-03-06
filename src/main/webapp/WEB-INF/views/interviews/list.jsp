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
	<div class="page-header"><h1>List Of Interviews</h1></div>
		<div class="row">
			<div class="col-xs-12">
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>InterviewId</th>
								<th>AddressId</th>
								<th>Phone1</th>
								<th>Phone2</th>
								<th>Filename</th>
								<th>Q60</th>
								<th>Date Updated</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${interviews}" var="interview">
								<tr>
									<td>${interview.interviewId}</td>
									<td>${interview.addressId}</td>
									<td>
										<c:choose>
											<c:when test="${not empty interview.phone1}">${interview.phone1}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${not empty interview.phone2}">${interview.phone2}</c:when>
											<c:otherwise>-</c:otherwise>
										</c:choose>									
									</td>
									<td>${interview.filename}</td>
									<td>
										<c:forEach var="answer" items="${interview.answers}">
											<c:if test="${answer.question.questionCode eq 'Q60'}">
												<c:forEach var="answerToken" items="${answer.answerTokens}">
													${answerToken.subQuestion} - ${answerToken.id - answer.answerTokens[0].id +1}
													<br/>
												</c:forEach>
											</c:if>
										</c:forEach>	
									</td>
									<td><spring:eval expression="interview.dateUpdated" /></td>
									<td>
										<a href="/interviews/interview/${interview.id}" title="View"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
									</td>
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