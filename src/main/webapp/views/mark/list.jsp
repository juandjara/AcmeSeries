<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="mark/customer/edit.do?contentId=${content.id}" class="btn btn-primary">
		<spring:message code="mark.edit"/>
	</a>
</security:authorize>
<br/><br/>
<p class="well">
	<spring:message code="mark.avgscore"/>
	<i><jstl:out value="${content.title}" /></i>:
	<span class="badge"><jstl:out value="${avgScore}" /></span>
</p>
<display:table name="marks" id="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-bordered">
	<acme:column code="actor.name" path="customer.userAccount.username" 
									sortable="true"/>
	<acme:column code="mark.viewed" path="isViewed" sortable="true"/>
	<acme:column code="mark.score" path="score" sortable="true"/>
	
</display:table>