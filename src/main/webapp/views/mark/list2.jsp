<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="marks" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	<spring:message code="comment.content" var="title" />
	<display:column title="title" sortable="true" >
		<jstl:set var="content" value="${contentMap.get(row.id)}"/>
		<jstl:choose>
			<jstl:when test="${content.getClass().name == 'domain.Film'}">
				<jstl:set var="detailUrl" value="film/details.do?filmId=${content.id}"/>
			</jstl:when>
			<jstl:when test="${content.getClass().name == 'domain.TVSerie'}">
				<jstl:set var="detailUrl" value="series/details.do?serieId=${content.id}" />
			</jstl:when>
			<jstl:when test="${content.getClass().name == 'domain.Episode'}">
				<jstl:set var="detailUrl" value="episode/details.do?episodeId=${content.id}" />
			</jstl:when>
		</jstl:choose>
		<a href="${detailUrl}">
			<jstl:out value="${content.getTitle()}" />
		</a>
		<jstl:if test="${content == null}">I HATE YOU</jstl:if>
	</display:column>
	<acme:column code="mark.score" path="score" sortable="true"/>

</display:table>