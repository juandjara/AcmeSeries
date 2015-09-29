<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${episode != null}">
<form:form action="${requestURI}" modelAttribute="episode">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="marks"/>
	<form:hidden path="comments"/>
	<form:hidden path="producerCompany"/>
	<form:hidden path="serie"/>
	
	<acme:textbox code="multimedia.title" path="title"/>
	<acme:numberbox code="episode.season" path="seasonNumber" />
	<acme:numberbox code="episode.number" path="episodeNumber"/>
	<acme:numberbox code="film.runningtime" path="runningTime"/>
	
	<button type="submit" name="save" class="btn btn-success">
		<spring:message code="acme.save" />
	</button>
	&nbsp;<jstl:if test="${episode.id != 0}">
		<spring:message code="acme.confirmdelete" var="deletecode"/>
		<button type="submit" name="delete" class="btn btn-danger"
				onclick="return confirm('${deletecode}')">
			<spring:message code="acme.delete" />
		</button>
	</jstl:if>
	&nbsp;<acme:cancel url="episode/list.do?serieId=${serieId}" code="acme.cancel"/>

</form:form>
</jstl:if>