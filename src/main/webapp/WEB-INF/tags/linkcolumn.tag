<%--
 * linkcolumn.tag
 * author: Juan Dominguez Jara
 --%>
<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="link"  required="true" %>
<%@ attribute name="actionsCode"  required="true" %>
<%@ attribute name="linkCode"  required="true" %>
<%@ attribute name="classes"  required="false" %>

<jstl:if test="${classes == null}">
	<jstl:set var="classes" value="btn btn-info" />
</jstl:if>

<%-- Definition --%>

<div>
	<spring:message code="${actionsCode}" var="theader"></spring:message>
	<display:column title="${theader}">
		<a href="${link}" class="${classes}">
			<spring:message code="${linkCode}"/>
		</a>
	</display:column>
</div>