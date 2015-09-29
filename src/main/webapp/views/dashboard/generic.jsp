<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<spring:message code="dashboard.mve"/>:
<display:table name="mostViewedEpisodes" uid="mve" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="multimedia.title" path="title"/>
	<acme:column code="episode.number" path="episodeNumber"/>
	<acme:column code="episode.season" path="seasonNumber"/>
	<acme:column code="dashboard.serie" path="serie.title"/>
</display:table><br/>
<spring:message code="dashboard.mce"/>:
<display:table name="mostCommentedEpisodes" uid="mce" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="multimedia.title" path="title"/>
	<acme:column code="episode.number" path="episodeNumber"/>
	<acme:column code="episode.season" path="seasonNumber"/>	
	<acme:column code="dashboard.serie" path="serie.title"/>
</display:table><br/>
<spring:message code="dashboard.mse"/>:
<display:table name="betterAvgEpisodes" uid="mse" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="multimedia.title" path="title"/>
	<acme:column code="episode.number" path="episodeNumber"/>
	<acme:column code="episode.season" path="seasonNumber"/>	
	<acme:column code="dashboard.serie" path="serie.title"/>
</display:table><br/>

<spring:message code="dashboard.mvs"/>:
<display:table name="mostViewedSeries" uid="mvs" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${mvs.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>
<spring:message code="dashboard.mcs"/>:
<display:table name="mostCommentedSeries" uid="mcs" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${mcs.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>
<spring:message code="dashboard.mss"/>:
<display:table name="betterAvgSeries" uid="mss" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${mss.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>

<spring:message code="dashboard.mvf"/>:
<display:table name="mostViewedFilms" uid="mvf" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${mvf.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>
<spring:message code="dashboard.mcf"/>:
<display:table name="mostCommentedFilms" uid="mcf" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${mcf.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>
<spring:message code="dashboard.msf"/>:
<display:table name="betterAvgFilms" uid="msf" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">		<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
	<jstl:forEach var="tag" items="${msf.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
</display:table><br/>