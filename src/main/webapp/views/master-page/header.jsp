<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme Series Co., Inc." />
</div>
<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="admin/registernewadmin.do">
						<spring:message	code="master.page.admin.newadmin" />
					</a></li>
					<li><a href="customer/admin/list.do">
						<spring:message	code="master.page.admin.customers" />
					</a></li>
					<li><a href="dashboard/admin.do">
						<spring:message	code="master.page.admin.dashboard" />
					</a></li>	
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="comment/customer/outbox.do">
						<spring:message	code="master.page.customer.mycomments" />
					</a></li>
					<li><a href="comment/customer/inbox.do">
						<spring:message	code="master.page.customer.recommendationsinbox" />
					</a></li>
					<li><a href="mark/customer/list.do">
						<spring:message	code="master.page.customer.mymarks" />
					</a></li>
					<li><a href="dashboard/customer.do">
						<spring:message	code="master.page.customer.dashboard" />
					</a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PRODUCER')">
			<li><a class="fNiv"><spring:message	code="master.page.producer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="film/producer/list.do">
						<spring:message	code="master.page.producer.myfilms" />
					</a></li>
					<li><a href="series/producer/list.do">
						<spring:message	code="master.page.producer.mytvseries" />
					</a></li>
					<li><a href="staff/producer/list.do">
						<spring:message	code="master.page.producer.mystaff" />
					</a></li>
					<li><a href="dashboard/producer.do">
						<spring:message	code="master.page.producer.dashboard" />
					</a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/register.do">
						<spring:message	code="master.page.register.customer" />
					</a></li>
					<li><a href="producer/register.do">
						<spring:message	code="master.page.register.producer" />
					</a></li>
				</ul>
			</li>
		</security:authorize>
		<!-- start section for every user, authenticated or not -->
			<li><a class="fNiv" href="film/list.do"><spring:message code="master.page.films" /></a></li>
			<li><a class="fNiv" href="series/list.do"><spring:message code="master.page.series" /></a></li>
		<!-- end section -->
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="dashboard/generic.do">
				<spring:message code="master.page.genericdashboard" />
			</a></li>
			<li><a class="fNiv" href="media/search.do">
				<spring:message code="master.page.search" />
			</a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" />:&nbsp;
		        	<span style="text-decoration: underline; color:#33CCCC;">
		        		<security:authentication property="principal.username" />
		        	</span>
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /></a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
