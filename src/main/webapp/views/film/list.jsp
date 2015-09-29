<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="films" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	
	<acme:column code="multimedia.title" path="title"/>
	<spring:message code="multimedia.tags" var="title" />
	<display:column title="${title}" sortable="false">
		<jstl:forEach var="tag" items="${row.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
				<security:authorize access="hasRole('PRODUCER')">
				<jstl:if test="${requestURI=='film/producer/list.do'}">
					<a title="remove tag" 
						href="film/producer/removetag.do?multimediaId=${row.id}&tag=${tag}" 
						class="text-danger">
					<strong>X</strong>
					</a>
				</jstl:if>
				</security:authorize>
			</span>
			&nbsp;
		</jstl:forEach>
	</display:column>
	
	<spring:message code="acme.actions" var="theader"></spring:message>
	<display:column title="${theader}">
		<a href="role/list.do?mediaId=${row.id}"
		   class="btn btn-primary">
			<spring:message code="multimedia.roles"/>
		</a>
		<a href="film/details.do?filmId=${row.id}"
		   class="btn btn-info">
			<spring:message code="acme.details"/>
		</a>
		<security:authorize access="hasRole('PRODUCER')">
		<jstl:if test="${requestURI=='film/producer/list.do'}">
			<a href="film/producer/edit.do?filmId=${row.id}" 
			   class="btn btn-primary">
				<spring:message code="acme.edit"/>
			</a>
		</jstl:if>
		</security:authorize>	
	</display:column>
</display:table>
<security:authorize access="hasRole('PRODUCER')">
	<acme:cancel url="film/producer/create.do" code="acme.create"/>
</security:authorize>