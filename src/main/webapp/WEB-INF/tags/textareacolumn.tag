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

<%@ attribute name="text"     required="true" %>
<%@ attribute name="titleCode"    required="true" %>
<%@ attribute name="readonly" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<%-- Definition --%>

<div>
	<spring:message code="${titleCode}" var="title"></spring:message>
	<display:column title="${title}">
		<textarea rows="4" cols="20" readonly="${readonly}">
			<jstl:out value="${text}"></jstl:out>
		</textarea>
	</display:column>
</div>