<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<dl class="dl-horizontal">
	<dt><spring:message code="comment.date"/></dt> 
	<dd> <jstl:out value="${comment.creationDate}" /> </dd>
	
	<dt><spring:message code="comment.body"/></dt> 
	<dd> <jstl:out value="${comment.text}" /> </dd>
	
	<dt><spring:message code="comment.content"/></dt> 
	<dd> <jstl:out value="${comment.content.title}" /> </dd>
	
	<jstl:if test="${comment.parent != null}">
		<dt><spring:message code="comment.replyto"/></dt> 
		<dd>
			<a href="comment/details.do?commentId=${comment.parent.id}">
				#<jstl:out value="${comment.parent.id}" />
			</a>
		</dd>
	</jstl:if>
	
	<jstl:if test="${comment.receiver != null}">
		<dt><spring:message code="comment.recommendationfor"/></dt> 
		<dd> <jstl:out value="${comment.receiver.userAccount.username }"/> </dd>
	</jstl:if>
</dl>

<jstl:choose>
	<jstl:when test="${comment.content.getClass().name == 'domain.Film'}">
		<jstl:set var="detailUrl" value="film/details.do?filmId=${comment.content.id}"/>
	</jstl:when>
	<jstl:when test="${comment.content.getClass().name == 'domain.TVSerie'}">
		<jstl:set var="detailUrl" value="series/details.do?serieId=${comment.content.id}" />
	</jstl:when>
	<jstl:when test="${comment.content.getClass().name == 'domain.Episode'}">
		<jstl:set var="detailUrl" value="episode/details.do?episodeId=${comment.content.id}" />
	</jstl:when>
</jstl:choose>

<jstl:if test="${detailUrl != null}">
<a href="${detailUrl}"
   class="btn btn-primary">
	<spring:message code="comment.content"/>
</a>
<a href="comment/replies.do?commentId=${comment.id}"
   class="btn btn-primary">
	<spring:message code="comment.replies"/>
</a>
</jstl:if>