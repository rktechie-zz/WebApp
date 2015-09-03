<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<title>Navigation Bar</title>
</head>
<body>
	<jsp:include page="/UserServlet?action=getAllUsers" />
	<ul id="ulid">
		<c:forEach items="${allUsers}" var="element">
			<li id="${element.getEmail()}"><a
				href="BookmarkServlet?action=getUserBookmarks&email=${element.getEmail()}">${element.getEmail()}</a></li>
		</c:forEach>
		<c:if test="${sessionScope.login == null}">
			<li><a href="login.jsp">Login/Register</a></li>
		</c:if>
		<c:if test="${sessionScope.login != null}">
			<form method="post" action="BookmarkServlet?action=manageBookmarks"
				id="manage_form">
				<input type="hidden" name="email" value="${sessionScope.email}">
				<li id="manageli"><a href="#">Manage Bookmarks</a></li>
			</form>
			<li><a href="bookmark.jsp">Add a Bookmark</a></li>
			<li><a href="changePassword.jsp">Change Password</a></li>
			<form method="post" action="UserServlet?action=logout"
				id="logout_form">
				<li id="logoutli"><a href="#">Logout</a></li>
			</form>
		</c:if>
	</ul>
</body>
<script type="text/javascript">
	$('#ulid li').click(function() {
		var id = this.id
		if (id == "manageli") {
			$('#manage_form').submit();
		} else if (id == "logoutli") {
			$('#logout_form').submit();
		}
	});
</script>
</html>