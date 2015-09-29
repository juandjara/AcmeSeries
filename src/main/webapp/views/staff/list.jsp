<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="staff" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	
	<acme:column code="actor.name" path="name" sortable="true"/>
	<acme:column code="actor.surname" path="surname" sortable="true"/>
	<acme:column code="actor.email" path="email" sortable="true"/>
	
	<acme:linkcolumn linkCode="acme.edit" actionsCode="acme.actions" 
					 link="staff/producer/edit.do?staffId=${row.id}"
					 classes="btn btn-primary"/>
	
</display:table>
<acme:cancel url="staff/producer/create.do" code="acme.create"/>