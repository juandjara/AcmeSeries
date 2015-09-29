<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${staff != null }">
<form:form action="${requestURI}" modelAttribute="staff">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="producer"/>
	<form:hidden path="roles"/>
	
	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.email" path="email"/>
	
	<button type="submit" name="save" class="btn btn-success">
		<spring:message code="acme.save" />
	</button>
	&nbsp;<jstl:if test="${staff.id != 0}">
		<spring:message code="acme.confirmdelete" var="deletecode"/>
		<button type="submit" name="delete" class="btn btn-danger"
				onclick="return confirm('${deletecode}')">
			<spring:message code="acme.delete" />
		</button>
	</jstl:if>
	&nbsp;<acme:cancel url="staff/producer/list.do" code="acme.cancel"/>

</form:form>
</jstl:if>