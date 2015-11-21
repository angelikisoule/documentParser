<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty pager && pager.totalPageNumber > 1}">	
	<!-- Initialize Parameters -->
	<c:choose>
		<c:when test="${empty param.page || param.page lt 1}">
	    	<c:set var="current" value="0" />
	  	</c:when>
	  	<c:otherwise>
	    	<c:set var="current" value="${param.page}" />
	  	</c:otherwise>
	</c:choose>
	<!-- First Page -->
	<c:if test="${current!=0}">
		<c:url value="${pager.firstPage}" var="lu">
			<c:if test="${not empty param.size}"><c:param name="size" value="${param.size}" /></c:if>
			<c:if test="${not empty param.type}"><c:param name="type" value="${param.type}" /></c:if>
		</c:url>
		<li><a href="${lu}" title="First Page">&laquo;</a></li>
	</c:if>
	<!-- Previous Page -->
	<c:if test="${current!=0}">
		<c:url value="${pager.urls[current-1]}" var="lu">
			<c:if test="${not empty param.size}"><c:param name="size" value="${param.size}" /></c:if>
			<c:if test="${not empty param.type}"><c:param name="type" value="${param.type}" /></c:if>
		</c:url>
		<li><a href="${lu}" title="Previous Page">&lsaquo;</a></li>
	</c:if>
	<!-- Where The Page Loop Starts -->
	<c:choose>
		<c:when test="${current==0 || current==1}">
			<c:set var="start" value="0" />
		</c:when>
		<c:otherwise>
			<c:set var="start" value="${current-2}" />
		</c:otherwise>
	</c:choose>
	<!-- Where The Page Loop Ends -->
	<c:choose>
		<c:when test="${current==pager.totalPageNumber || current==pager.totalPageNumber-1}">
			<c:set var="end" value="${pager.totalPageNumber}" />
		</c:when>
		<c:otherwise>
			<c:set var="end" value="${current+2}" />
		</c:otherwise>
	</c:choose>
	<!-- Page Loop -->
	<c:forEach var="u" items="${pager.urls}" begin="${start}" end="${end}" varStatus="i">
		<c:choose>
			<c:when test="${i.index==current}"><c:set var="cl" value="active" /></c:when>
			<c:otherwise><c:set var="cl" value="" /></c:otherwise>
		</c:choose>
		<c:url value="${u}" var="lu">
			<c:if test="${not empty param.size}"><c:param name="size" value="${param.size}" /></c:if>
			<c:if test="${not empty param.type}"><c:param name="type" value="${param.type}" /></c:if>
		</c:url>
		<li class="${cl}"><a href="${lu}" class="${cl}">${i.index+1}</a></li>
	</c:forEach>
	<!-- Next Page -->
	<c:if test="${pager.totalPageNumber > 1 && current < pager.totalPageNumber-1}">
		<c:url value="${pager.urls[current+1]}" var="lu">
			<c:if test="${not empty param.size}"><c:param name="size" value="${param.size}" /></c:if>
			<c:if test="${not empty param.type}"><c:param name="type" value="${param.type}" /></c:if>
		</c:url>
		<li><a href="${lu}" title="Next Page">&rsaquo;</a></li>
	</c:if>
	<!-- Last Page -->
	<c:if test="${current!=pager.totalPageNumber-1}">
		<c:url value="${pager.urls[pager.totalPageNumber-1]}" var="lu">
			<c:if test="${not empty param.size}"><c:param name="size" value="${param.size}" /></c:if>
			<c:if test="${not empty param.type}"><c:param name="type" value="${param.type}" /></c:if>
		</c:url>
		<li><a href="${lu}" title="Last Page">&raquo;</a></li>
	</c:if>
</c:if>