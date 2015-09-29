<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>

<spring:message code="dashboard.nofc"/>:&nbsp;
<pre><jstl:out value="${nOfComments}"></jstl:out></pre>

<spring:message code="dashboard.noff"/>:&nbsp;
<pre><jstl:out value="${nOfFilms}"></jstl:out></pre>

<spring:message code="dashboard.nofs"/>:&nbsp;
<pre><jstl:out value="${nOfSeries}"></jstl:out></pre>

<spring:message code="dashboard.nofe"/>:&nbsp;
<pre><jstl:out value="${nOfEpisodes}"></jstl:out></pre>

<spring:message code="dashboard.morereplies"/>
<display:table name="moreReplies" uid="row" requestURI="${requestURI}" 
				pagesize="5" class="table table-hover table-striped table-bordered">
		
	<acme:column code="comment.id" path="id"/>
	<acme:column code="comment.sender" path="sender.userAccount.username"/>
	<acme:column code="comment.content" path="content.title" />
	<acme:column code="comment.date" path="creationDate"/>	
</display:table>

