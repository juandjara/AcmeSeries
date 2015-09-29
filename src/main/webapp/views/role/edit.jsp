<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form action="${requestURI}" modelAttribute="roleForm">
	
	<form:hidden path="id"/>
	<form:hidden path="mediaId"/>
	
	<form:label path="role">
		<spring:message code="role.role" />
	</form:label>	
	<form:select path="role">
		<form:option label="---" value="NONE"/>
		<form:option label="DIRECTOR" value="DIRECTOR"/>
		<form:option label="WRITER" value="WRITER"/>
		<form:option label="ACTOR" value="ACTOR"/>
		<form:option label="COMPOSER" value="COMPOSER"/>
	</form:select>
	<form:errors path="role" cssClass="error" />
	<jstl:choose>
		<jstl:when test="${roleForm.id == 0}">
			<acme:select items="${staffmembers}" itemLabel="fullname"
						 code="actor.name" path="staffId"/>
		</jstl:when>
		<jstl:otherwise>
			<form:hidden path="staffId"/>
		</jstl:otherwise>
	</jstl:choose>
	<br />
	<button type="submit" name="save" class="btn btn-success">
		<spring:message code="acme.save" />
	</button>
	&nbsp;<jstl:if test="${roleForm.id != 0}">
		<spring:message code="acme.confirmdelete" var="deletecode"/>
		<button type="submit" name="delete" class="btn btn-danger"
				onclick="return confirm('${deletecode}')">
			<spring:message code="acme.delete" />
		</button>
	</jstl:if>
	&nbsp;
	<acme:cancel url="role/list.do?mediaId=${roleForm.mediaId}" 
				 code="acme.cancel"/>

</form:form>