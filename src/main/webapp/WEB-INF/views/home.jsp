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
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<a href="<c:url value="/parseTxt"/>" class="btn btn-default btn-lg active" role="button">Parse .txt Files</a>
				<a href="<c:url value="/parseXls"/>" class="btn btn-primary btn-lg active" role="button">Parse .xls Files</a>
			</div>
		</div>
	</div>	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="<c:url value="/resources/bootstrap-3.3.5/js/bootstrap.min.js" />"></script>
</body>
</html>