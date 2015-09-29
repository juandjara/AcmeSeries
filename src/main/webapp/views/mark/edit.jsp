<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jstl:if test="${mark != null}">
<form:form action="${requestURI}" modelAttribute="mark">
	
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="customer"/>
	<form:hidden path="content"/>
	
	<acme:checkbox code="mark.viewed" path="isViewed"/>
	
	<form:label path="score">
		<spring:message code="mark.score" />
	</form:label>:&nbsp;
	<form:radiobutton path="score" value="1"/>1
	<form:radiobutton path="score" value="2"/>2
	<form:radiobutton path="score" value="3"/>3
	<form:radiobutton path="score" value="4"/>4
	<form:radiobutton path="score" value="5"/>5
	<form:errors path="score" cssClass="error" />
	
	
	<br />
	<button type="submit" name="save" class="btn btn-success">
		<spring:message code="acme.save" />
	</button>
	
	&nbsp;
	<acme:cancel url="mark/list.do?contentId=${mark.content.id}" 
				 code="acme.cancel"/>

</form:form>
</jstl:if>