<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<display:table name="items" uid="row" requestURI="media/query.do" 
				pagesize="5" class="table table-hover table-bordered">
	<acme:column code="multimedia.title" path="title" sortable="true"/>
	<acme:column code="multimedia.releasedate" path="releaseDate" sortable="true"/>
	<spring:message code="content.avgscore" var="avgscore" />
	<display:column title="${avgscore}" sortable="true">
		<jstl:out value="${controller.avgScore(row)}" />
	</display:column>
	<jstl:choose>
		<jstl:when test="${row.getClass().name == 'domain.Film'}">
			<jstl:set var="detailUrl" value="film/details.do?filmId=${row.id}"/>
		</jstl:when>
		<jstl:when test="${row.getClass().name == 'domain.TVSerie'}">
			<jstl:set var="detailUrl" value="series/details.do?serieId=${row.id}" />
		</jstl:when>
	</jstl:choose>
	<spring:message code="acme.actions" var="theader" />
	<display:column title="${theader}">
		<a href="role/list.do?mediaId=${row.id}"
		   class="btn btn-primary">
			<spring:message code="multimedia.roles"/>
		</a>
		<a href="${detailUrl}"
		   class="btn btn-info">
			<spring:message code="acme.details"/>
		</a>
	</display:column>
	
</display:table>