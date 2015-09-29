<%--
 * column.tag
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
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="path"  required="true" %>
<%@ attribute name="code"  required="true" %>
<%@ attribute name="sortable"  required="false" %>

<jstl:if test="${sortable== null}">
	<jstl:set var="sortable" value="false" />
</jstl:if>

<%-- Definition --%>

<div>
	<spring:message code="${code}" var="title" />
	<display:column property="${path}" title="${title}" sortable="${sortable}" />
</div>