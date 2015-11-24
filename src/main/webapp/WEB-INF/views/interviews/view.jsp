<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <style>
    	strong {
    		display:inline-block;
    		width:120px;
    	}
    	.subQuestion {
    		display:block;
    		color:#337ab7;
    	}
    	.subAnswer {
    		display:block;
    		padding-left:40px;
    		color:#d9534f;
    	}
    </style>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="container-fluid">
	<div class="page-header"><h1>Interview ${interview.id}</h1></div>
		<div class="row">
			<div class="col-xs-12">
				<ul class="list-group">
  					<li class="list-group-item"><strong>Interview Id:</strong>${interview.interviewId}</li>
  					<li class="list-group-item"><strong>Address Id:</strong>${interview.addressId}</li>
  					<li class="list-group-item">
						<strong>Phone1:</strong>
						<c:choose>
							<c:when test="${not empty interview.phone1}">${interview.phone1}</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
  					</li>
  					<li class="list-group-item">
						<strong>Phone2:</strong>
						<c:choose>
							<c:when test="${not empty interview.phone2}">${interview.phone2}</c:when>
							<c:otherwise>-</c:otherwise>
						</c:choose>
  					</li>
  					<li class="list-group-item"><strong>Filename:</strong>${interview.filename}</li>
				</ul>
				<c:forEach var="answer" items="${interview.answers}">
					<div class="panel panel-primary">
  						<div class="panel-heading">
    						<h3 class="panel-title">${answer.question.questionText}</h3>
  						</div>
  						<div class="panel-body">
    						<c:forEach var="answerToken" items="${answer.answerTokens}">
								<c:choose>
									<c:when test="${not empty answerToken.subQuestion}">
										<span class="subQuestion">
											${answerToken.subQuestion}
										</span>
										<span class="subAnswer">
											${answerToken.subAnswer}
										</span>
									</c:when>
									<c:otherwise>
										<div>
											${answerToken.answerTokenText}
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
  						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js" />"></script>
</body>
</html>