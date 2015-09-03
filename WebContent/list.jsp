<%@page import="webapp.model.BookmarkBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of All Bookmarks</title>
<script type="text/javascript" src="jquery-1.11.3.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
</head>
<body>
<c:set var="count" value="0" scope="page" />
    <table>
    	<tr>
    		<td>Email</td>
    		<td>Url</td>
    		<td>Comment</td>
    		<td>Click Count</td>
    	</tr>
		<c:forEach items="${listBookmarkBeans}" var="element">
		   <tr id="<c:out value="${counter+1}"/>">
		   		<td id="<c:out value="${counter+1}"/>_email">${element.getEmail()}</td>
		   		<c:choose>
				    <c:when test="${fn:startsWith(element.getUrl(), 'http://')}">
		   				<td class="tdclass" id="<c:out value="${counter+1}"/>_url" data-url="${element.getUrl()}"><a href="${element.getUrl()}" target="_blank">${element.getUrl()}</a></td>
				    </c:when>
				    <c:when test="${fn:startsWith(element.getUrl(), 'https://')}">
		   				<td class="tdclass" id="<c:out value="${counter+1}"/>_url" data-url="${element.getUrl()}"><a href="${element.getUrl()}" target="_blank">${element.getUrl()}</a></td>
				    </c:when>   
				    <c:otherwise>
				        <td class="tdclass" id="<c:out value="${counter+1}"/>_url" data-url="${element.getUrl()}"><a href="http://${element.getUrl()}" target="_blank">${element.getUrl()}</a></td>
				    </c:otherwise>
				</c:choose>
		   		<td id="<c:out value="${counter+1}"/>_comment">${element.getComment()}</td>
		   		<td id="<c:out value="${counter+1}"/>_count">${element.getCount()}</td>
		   </tr>
		   <c:set var="counter" value="${counter+1}"/>
		</c:forEach>
	</table>
	<c:import url="navigationBar.jsp"/>
</body>

<script type="text/javascript">
$('.tdclass').on('click',function(){
    var id = $(this).closest('tr').attr("id");
    var email = $('#'+id+'_email').html();
    var url = $(this).data('url');
    var comment = $('#'+id+'_comment').html();
    $.ajax({
		  url: "BookmarkServlet?action=incrementCount",
		  type: "post",
		  data: {email: email, url: url, comment: comment},
		  success: function(data){
		      if(data == 'true'){
		    	  window.location.href = "BookmarkServlet?action=getUserBookmarks&email="+email;
		      } else{
		      }
		  },
		  error:function(){
		  }   
		});
});
</script>
</html>