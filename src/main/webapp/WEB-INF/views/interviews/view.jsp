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
    		width:150px;
    	}
    	.question {
    		display:block;
    		font-size:20px;
    		font-weight:bold;
    	}
    	.subQuestion {
    		display:block;
    		color:blue;
    	}
    	.answer {
    		display:block;
    	}
    	.subAnswer {
    		display:block;
    		padding-left:50px;
    		color:red;
    	}
    </style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<h4><strong>Interview Id:</strong>${interview.interviewId}</h4>
				<h4><strong>Address Id:</strong>${interview.addressId}</h4>
				<h4>
					<strong>Phone1:</strong>
					<c:choose>
						<c:when test="${not empty interview.phone1}">${interview.phone1}</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>
				</h4>
				<h4>
					<strong>Phone2:</strong>
					<c:choose>
						<c:when test="${not empty interview.phone2}">${interview.phone2}</c:when>
						<c:otherwise>-</c:otherwise>
					</c:choose>					
				</h4>
				<h4><strong>Filename:</strong>${interview.filename}</h4>
				<c:forEach var="answer" items="${interview.answers}">
					<span class="question">${answer.question.questionText}</span>
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
								<span class="answer">
									${answerToken.answerTokenText}
								</span>
							</c:otherwise>
						</c:choose>
					</c:forEach>
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