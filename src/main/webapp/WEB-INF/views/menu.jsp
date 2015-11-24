<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<a href="<c:url value="/interviews/list"/>"	class="btn btn-default btn-lg" role="button">List Of Interviews <b>${countInterviews}</b></a>
			<a href="<c:url value="/persons/list"/>" class="btn btn-default btn-lg" role="button">List Of Persons <b>${countPersons}</b></a> 
			<a href="<c:url value="/stats/list"/>" class="btn btn-default btn-lg" role="button">List Of Files <b>${countFiles}</b></a> 
			<a href="<c:url value="/parseTxt"/>" class="btn btn-warning btn-lg" role="button">Parse .txt Files</a> 
			<a href="<c:url value="/parseXls"/>" class="btn btn-primary btn-lg" role="button">Parse .xls Files</a> 
			<a href="<c:url value="/mergeFiles"/>" class="btn btn-success btn-lg" role="button">Merge Both Files</a> 
			<a href="<c:url value="/deleteAllXls"/>" class="btn btn-danger btn-lg" role="button">Delete .xls Files</a>
		</div>
	</div>
</div>
