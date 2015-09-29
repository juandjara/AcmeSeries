<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="commentForm">
	<form:hidden path="contentId"/>
	<form:hidden path="parentId"/>

	<jstl:if test="${isRecommendation == true}">
		<acme:select items="${customers}" itemLabel="fullname"
				 code="comment.receiver" path="receiverId" firstvalue="-1"/>
	</jstl:if>
	<acme:textarea code="comment.body" path="text"/>

	<acme:submit name="save" code="acme.save"/>
	<acme:cancel url="comment/list.do?contentId=${commentForm.contentId}" 
				 code="acme.cancel"/>
</form:form>