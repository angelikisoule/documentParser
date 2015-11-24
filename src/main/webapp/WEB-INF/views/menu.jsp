<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	<div class="row" style="padding: 10px 0;">
		<div class="col-xs-12">
			<a href="/interviews/list"	class="btn btn-default btn-lg" role="button">List Of Interviews <b>${countInterviews}</b></a>
			<a href="/persons/list" class="btn btn-default btn-lg" role="button">List Of Persons <b>${countPersons}</b></a> 
			<a href="/stats/list" class="btn btn-default btn-lg" role="button">List Of Files <b>${countFiles}</b></a> 
			<div style="display:none">
				<a href="/parseTxt" class="btn btn-warning btn-lg" role="button">Parse .txt Files</a> 
				<a href="/parseXls" class="btn btn-primary btn-lg" role="button">Parse .xls Files</a> 
				<a href="/mergeFiles" class="btn btn-success btn-lg" role="button">Merge Both Files</a> 
				<a href="/deleteAllXls" class="btn btn-danger btn-lg" role="button">Delete .xls Files</a>
			</div>
		</div>
	</div>
</div>
