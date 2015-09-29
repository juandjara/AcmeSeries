<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<spring:message code="dashboard.nofcu"/>:&nbsp;
<pre><jstl:out value="${nOfCustomers}"></jstl:out></pre>

<spring:message code="dashboard.mcc"/>:&nbsp;
<display:table name="mostCommentedContents" uid="row1" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="multimedia.title" path="title"/>
</display:table><br />

<spring:message code="dashboard.msc"/>:&nbsp;
<display:table name="betterAvgContents" uid="row2" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
	<acme:column code="multimedia.title" path="title"/>
</display:table><br />