<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="actorForm">
<fieldset>
	<legend align="left">
		<spring:message code="actor.userAccount"/>
	</legend>
	<acme:textbox code="actor.userAccount.username" path="username"/>
	<acme:password code="actor.userAccount.password" path="password"/>
	<acme:password code="actor.userAccount.confirmPassword" path="confirmPassword"/>
</fieldset>
<br />
<fieldset>
	<legend align="left">
		<spring:message code="actor.personalInfo" />
	</legend>
	<acme:textbox code="actor.name" path="name"/>
	<acme:textbox code="actor.surname" path="surname"/>
	<acme:textbox code="actor.email" path="email"/>
	<acme:numberbox code="actor.phone" path="phone"/>
</fieldset>
<br />
<!-- Start section check and buttons -->
	<acme:checkbox code="actor.acceptConditions" path="acceptConditions"/>
	<acme:submit name="save" code="acme.save" />
	&nbsp;
	<acme:cancel url="" code="acme.cancel"/>
<!-- End section -->
</form:form>