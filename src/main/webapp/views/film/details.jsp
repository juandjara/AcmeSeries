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
	<dd> <jstl:out value="${film.title}" /> </dd>
	
	<dt><spring:message code="multimedia.tags"/></dt> 
	<dd>
		<jstl:forEach var="tag" items="${film.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>&nbsp;
		</jstl:forEach>
	</dd>
	
	<dt><spring:message code="multimedia.genres"/></dt> 
	<dd><jstl:out value="${film.genres}" /></dd>
	
	<dt><spring:message code="multimedia.distributioncompany"/></dt> 
	<dd><jstl:out value="${film.distributionCompany}" /></dd>
	
	<dt><spring:message code="multimedia.origincountry"/></dt> 
	<dd><jstl:out value="${film.originCountry}" /></dd>
	
	<dt><spring:message code="multimedia.releasedate"/></dt> 
	<dd> <fmt:formatDate value="${film.releaseDate}" pattern="dd/MM/yyyy"/> </dd>
	
	<dt><spring:message code="multimedia.sinopsis"/></dt> 
	<dd><jstl:out value="${film.sinopsis}" /></dd>
	
	<dt><spring:message code="multimedia.producer"/></dt> 
	<dd><jstl:out value="${film.producerCompany.name}" /></dd>

	<dt><spring:message code="film.runningtime"/></dt> 
	<dd><jstl:out value="${film.runningTime}" /></dd>
	
	<dt><spring:message code="film.budget"/></dt>
	<dd> <fmt:formatNumber value="${film.budget}" pattern="###########.##"/> </dd>
	
	<dt><spring:message code="film.boxoffice"/></dt> 
	<dd><fmt:formatNumber value="${film.boxOffice}" pattern="###########.##"/></dd>
	
</dl>
<a href="comment/list.do?contentId=${film.id}"
   class="btn btn-primary">
	<spring:message code="content.comments"/>
</a>
<a href="mark/list.do?contentId=${film.id}"
   class="btn btn-primary">
	<spring:message code="content.marks"/>
</a>