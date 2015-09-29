<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${serie != null}">
<form:form action="${requestURI}" modelAttribute="serie">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="marks"/>
	<form:hidden path="comments"/>
	<form:hidden path="producerCompany"/>
	<form:hidden path="roles"/>
	<form:hidden path="episodes"/>	
	
	<acme:textbox code="multimedia.title" path="title" />
	<acme:textbox code="multimedia.tags" path="tags"/>
	<acme:textbox code="multimedia.genres" path="genres" />
	<acme:textbox code="multimedia.distributioncompany" path="distributionCompany" />
	<acme:textbox code="multimedia.origincountry" path="originCountry" />
	<acme:datebox code="multimedia.releasedate" path="releaseDate" />
	<acme:textbox code="multimedia.sinopsis" path="sinopsis"/>
	<acme:textbox code="tvserie.originalchannel" path="originalChannel" />
	
	
	<button type="submit" name="save" class="btn btn-success">
		<spring:message code="acme.save" />
	</button>
	&nbsp;<jstl:if test="${serie.id != 0}">
		<spring:message code="acme.confirmdelete" var="deletecode"/>
		<button type="submit" name="delete" class="btn btn-danger"
				onclick="return confirm('${deletecode}')">
			<spring:message code="acme.delete" />
		</button>
	</jstl:if>
	&nbsp;<acme:cancel url="series/producer/list.do" code="acme.cancel"/>

</form:form>
</jstl:if>