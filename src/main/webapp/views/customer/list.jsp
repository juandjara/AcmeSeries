<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<display:table name="customers" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	
	<acme:column code="actor.userAccount.username" path="userAccount.username"
					sortable="true" />
	<spring:message code="actor.name" var="name" />
	<display:column title="${name}" sortable="true">
		<jstl:out value="${row.name.concat(' ').concat(row.surname)}" />
	</display:column>
	<acme:column code="actor.email" path="email" sortable="true"/>
	<acme:column code="customer.isBanned" path="isBanned" sortable="true"/>
	
	<jstl:choose>
		<jstl:when test="${row.isBanned}">
			<jstl:set var="banCode" value="customer.unban"/>
		</jstl:when>
		<jstl:otherwise>
			<jstl:set var="banCode" value="customer.ban"/>
		</jstl:otherwise>
	</jstl:choose>
	
	<spring:message code="acme.actions" var="theader"></spring:message>
	<display:column title="${theader}">
		<a href="customer/admin/toggleban.do?customerId=${row.id}"
			class="btn btn-danger">
			<spring:message code="${banCode}"/>
		</a>
		<br/>
	</display:column>
</display:table>