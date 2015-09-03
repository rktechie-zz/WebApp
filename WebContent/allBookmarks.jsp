<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
<script type="text/javascript" src="jquery-1.11.3.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
</head>
<body>
<body>
	<c:set var="counter" value="0" scope="page" />
	<c:if test="${message eq null}">
		<jsp:include page="/BookmarkServlet" />
	</c:if>
	<table>
		<c:forEach items="${listBookmarkBeans}" var="element">
			<tr id="<c:out value="${counter+1}"/>">
				<td id="<c:out value="${counter+1}"/>_email">${element.getEmail()}</td>
				<c:choose>
					<c:when test="${fn:startsWith(element.getUrl(), 'http://')}">
						<td class="tdclass" id="<c:out value="${counter+1}"/>_url"
							data-url="${element.getUrl()}"><a href="${element.getUrl()}"
							target="_blank">${element.getUrl()}</a></td>
					</c:when>
					<c:when test="${fn:startsWith(element.getUrl(), 'https://')}">
						<td class="tdclass" id="<c:out value="${counter+1}"/>_url"
							data-url="${element.getUrl()}"><a href="${element.getUrl()}"
							target="_blank">${element.getUrl()}</a></td>
					</c:when>
					<c:otherwise>
						<td class="tdclass" id="<c:out value="${counter+1}"/>_url"
							data-url="${element.getUrl()}"><a
							href="http://${element.getUrl()}" target="_blank">${element.getUrl()}</a></td>
					</c:otherwise>
				</c:choose>
				<td id="<c:out value="${counter+1}"/>_comment">${element.getComment()}</td>
				<td id="<c:out value="${counter+1}"/>_count">${element.getCount()}</td>
			</tr>
			<c:set var="counter" value="${counter+1}" />
		</c:forEach>
	</table>
</body>
</body>

<script type="text/javascript">
	$('.tdclass').on('click', function() {
		var id = $(this).closest('tr').attr("id");
		var email = $('#' + id + '_email').html();
		var url = $(this).data('url');
		var comment = $('#' + id + '_comment').html();
		console.log(url);
		console.log(comment);
		$.ajax({
			url : "BookmarkServlet?action=incrementCount",
			type : "post",
			data : {
				email : email,
				url : url,
				comment : comment
			},
			success : function(data) {
				alert("success");
				if (data == 'true') {
					//window.open(url, "_blank");
				} else {
					alert("failure");
				}
			},
			error : function() {
				alert("failure");
				//$("#result").html('there is error while submit');
			}
		});
	});
</script>
</html>