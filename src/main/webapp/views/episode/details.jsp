<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dl class="dl-horizontal">
	<dt><spring:message code="multimedia.title"/></dt> 
	<dd> <jstl:out value="${episode.title}" /> </dd>
	
	<dt><spring:message code="multimedia.producer"/></dt> 
	<dd><jstl:out value="${episode.producerCompany.name}" /></dd>
		
	<dt><spring:message code="episode.number"/></dt> 
	<dd><jstl:out value="${episode.episodeNumber}" /></dd>
	
	<dt><spring:message code="episode.season"/></dt> 
	<dd><jstl:out value="${episode.seasonNumber}" /></dd>
	
	<dt><spring:message code="film.runningtime"/></dt> 
	<dd><jstl:out value="${episode.runningTime}" /></dd>
	
</dl>
<a href="comment/list.do?contentId=${episode.id}"
   class="btn btn-primary">
	<spring:message code="content.comments"/>
</a>
<a href="mark/list.do?contentId=${episode.id}"
   class="btn btn-primary">
	<spring:message code="content.marks"/>
</a>
<a href="series/details.do?serieId=${episode.serie.id}"
   class="btn btn-primary">
	<spring:message code="master.page.series"/>
</a>
