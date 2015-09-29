<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="episodes" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	
	<acme:column code="episode.season" path="seasonNumber" sortable="true"/>
	<acme:column code="episode.number" path="episodeNumber" sortable="true"/>
	<acme:column code="multimedia.title" path="title" sortable="true"/>
	<acme:column code="film.runningtime" path="runningTime" sortable="true"/>
	
	<spring:message code="acme.actions" var="theader"></spring:message>
	<display:column title="${theader}">
		<a href="episode/details.do?episodeId=${row.id}"
		   class="btn btn-info">
			<spring:message code="acme.details"/>
		</a>
		<security:authorize access="hasRole('PRODUCER')">
		<jstl:if test="${isPrincipal == true}">
			<a href="episode/producer/edit.do?episodeId=${row.id}" 
			   class="btn btn-primary">
				<spring:message code="acme.edit"/>
			</a>
		</jstl:if>
		</security:authorize>	
	</display:column>
</display:table>
<security:authorize access="hasRole('PRODUCER')">
<jstl:if test="${isPrincipal == true}">
	<acme:cancel url="episode/producer/create.do?serieId=${serieId}" 
				 code="acme.create"/>
</jstl:if>
</security:authorize>