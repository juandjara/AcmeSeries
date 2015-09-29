<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="input-group">
	<input id="keyword" type="text" class="form-control" title="Search" 
		   placeholder='<spring:message code="media.search"/>'/>
	<span class="input-group-btn">
		<button name="search" id="search-btn" type="submit" title="Search" 
				class="btn btn-primary">
			<span class="glyphicon glyphicon-search"></span>
		</button>
	</span>		
</div>
<p>
	<spring:message code="media.searchinfo"/>
</p>
<div id="episodeTable"></div>
<script>
	$(document).ready(function(){
		$("#keyword").bind('keypress', function(e){
			if(e.keyCode = 13){ // ENTER
				ajaxtable();
			}
		});
	});
	function ajaxtable() {
       $.ajax({
           type: "GET",
           url: "media/query.do?key="+$("#keyword").val(),
           success: function(response) {
               $("#episodeTable").html( response );
               bindTableSortAndPaging();
           }
       });
	}
	function ajaxtableParam(url) {
       $.ajax({
           type: "GET",
           url: url,
           success: function(response) {
               $("#episodeTable").html( response );
               bindTableSortAndPaging();
           }
       });
	}
	function bindTableSortAndPaging(){
		$("table th.sortable").each(function() {
			$(this).click(function() {
				var link = $(this).find("a").attr("href");
				ajaxtableParam(link);		        
				return false;
			});
		});
		$(".pagelinks a").each(function() {
			$(this).click(function() {
				var link = $(this).attr("href");
				ajaxtableParam(link);		        
				return false;
			});
		});
	}
</script>