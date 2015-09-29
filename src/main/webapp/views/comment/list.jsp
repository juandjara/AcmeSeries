<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<div class="row-fluid">
<security:authorize access="hasRole('CUSTOMER')">
	<jstl:choose>
		<jstl:when test="${requestURI == 'comment/list.do' }">
			<a href="comment/customer/create.do?contentId=${content.id}"
			   class="btn btn-primary">
				<spring:message code="comment.create"/>
			</a>
			<a href="comment/customer/recommend.do?contentId=${content.id}"
			   class="btn btn-primary">
				<spring:message code="comment.recommend"/>
			</a>
		</jstl:when>
		<jstl:when test="${requestURI == 'comment/replies.do' }">
			<a href="comment/customer/reply.do?parentId=${commentId}"
			   class="btn btn-primary">
				<spring:message code="comment.reply"/>
			</a>
		</jstl:when>
	</jstl:choose>
</security:authorize>
<hr/>
</div>
<display:table name="comments" id="comment" requestURI="${requestURI}" 
				pagesize="5" class="table">
	<spring:message code="content.comments" var="theader"></spring:message>
	<display:column title="${theader}">
	<div class="panel panel-default panel-body col-md-8">
		<div> <!-- Comment header -->
			<spring:message code="comment.on"/>&nbsp;
			(<fmt:formatDate value="${comment.creationDate}" 
				pattern="dd/MM/yy HH:mm"/>)&nbsp;
			<jstl:out value="${comment.sender.userAccount.username}"/>
			<spring:message code="comment.said"/>:
		</div>
		<div class="well"
			 style="margin: 5px 0px 15px 0px !important;">
		<!-- Comment body -->
			<jstl:out value="${comment.text}"/>
		</div>
		<div><!-- Comment footer -->
			<a href="comment/details.do?commentId=${comment.id}"
			   class="btn btn-primary">
				<spring:message code="acme.details"/>
			</a>&nbsp;
			<security:authorize access="hasRole('CUSTOMER')">
			<jstl:if test="${comment.sender == principal}">
				<a href="comment/customer/delete.do?commentId=${comment.id}"
				   class="btn btn-danger">
					<spring:message code="acme.delete"/>
				</a>
			</jstl:if>
			</security:authorize>
			&nbsp;(<spring:message code="comment.content"/>:
			<jstl:out value="${comment.content.title}" />)
		</div>
	</div>
	</display:column>
</display:table>