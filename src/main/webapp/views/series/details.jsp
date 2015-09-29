<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dl class="dl-horizontal">
	<dt><spring:message code="multimedia.title"/></dt> 
	<dd> <jstl:out value="${serie.title}" /> </dd>
	
	<dt><spring:message code="multimedia.tags"/></dt> 
	<dd>
		<jstl:forEach var="tag" items="${serie.tags}">
			<span class="label label-primary">
				<jstl:out value="${tag}" />
			</span>&nbsp;
		</jstl:forEach>
	</dd>
	
	<dt><spring:message code="multimedia.genres"/></dt> 
	<dd><jstl:out value="${serie.genres}" /></dd>
	
	<dt><spring:message code="multimedia.distributioncompany"/></dt> 
	<dd><jstl:out value="${serie.distributionCompany}" /></dd>
	
	<dt><spring:message code="multimedia.origincountry"/></dt> 
	<dd><jstl:out value="${serie.originCountry}" /></dd>
	
	<dt><spring:message code="multimedia.releasedate"/></dt> 
	<dd> <fmt:formatDate value="${serie.releaseDate}" pattern="dd/MM/yyyy"/> </dd>
	
	<dt><spring:message code="multimedia.sinopsis"/></dt> 
	<dd><jstl:out value="${serie.sinopsis}" /></dd>
	
	<dt><spring:message code="multimedia.producer"/></dt> 
	<dd><jstl:out value="${serie.producerCompany.name}" /></dd>
	
	<dt><spring:message code="tvserie.originalchannel"/></dt> 
	<dd><jstl:out value="${serie.originalChannel}" /></dd>
	
	<dt><spring:message code="tvserie.numberofepisodes"/></dt> 
	<dd><jstl:out value="${serie.episodes.size()}" /></dd>
	
</dl>
<a href="comment/list.do?contentId=${serie.id}"
   class="btn btn-primary">
	<spring:message code="content.comments"/>
</a>
<a href="mark/list.do?contentId=${serie.id}"
   class="btn btn-primary">
	<spring:message code="content.marks"/>
</a>
<a href="episode/list.do?serieId=${serie.id}"
   class="btn btn-primary">
	<spring:message code="tvserie.episodes"/>
</a>