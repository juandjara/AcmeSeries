<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<spring:message code="dashboard.pbs"/>: &nbsp;
<display:table name="producersBetterScore" uid="pbs" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />

<spring:message code="dashboard.pws"/>: &nbsp;
<display:table name="producersWorstScore" uid="pws" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />

<spring:message code="dashboard.pmm"/>: &nbsp;
<display:table name="producersMoreMedia" uid="pmm" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />

<spring:message code="dashboard.cmc"/>: &nbsp;
<display:table name="customersMoreComments" uid="cmc" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />

<spring:message code="dashboard.cmf"/>: &nbsp;
<display:table name="customersMoreFilms" uid="cmf" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />

<spring:message code="dashboard.cme"/>: &nbsp;
<display:table name="customersMoreEpisodes" uid="cme" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="actor.name" path="name"/>
	<acme:column code="actor.surname" path="surname"/>
	<acme:column code="actor.email" path="email"/>
</display:table><br />