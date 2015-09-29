<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="roles" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	
	<acme:column code="actor.name" path="staff.name" sortable="true"/>
	<acme:column code="actor.surname" path="staff.surname" sortable="true"/>
	<acme:column code="role.role" path="role" sortable="true"/>
	
	<jstl:if test="${isPrincipal == true}">
	<acme:linkcolumn linkCode="acme.edit" actionsCode="acme.actions" 
					 link="role/producer/edit.do?roleId=${row.id}"
					 classes="btn btn-primary"/>
	</jstl:if>
</display:table>
<jstl:if test="${isPrincipal == true}">
	<acme:cancel url="role/producer/create.do?mediaId=${media.id}" 
				 code="acme.create"/>
</jstl:if>