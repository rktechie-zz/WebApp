<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bookmarks</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="jquery-1.11.3.min.js"></script>
</head>
<body>
<div>
Bookmarks for ${sessionScope.first_name} ${sessionScope.last_name}
</div>
<div>
<form action="BookmarkServlet?action=addBookmark" method="post" id="bookmark_form">
URL:<input type="text" name="url" id="url">
Comment:<input type="text" name="comment" id="comment">
<input type="button" value="Add Bookmark" id="add_bookmark">
</form>
</div>
<div id="message"></div> 
</body>

<script type="text/javascript">
$(document).ready(function()
{
    $("#add_bookmark").click(function()
    {
    	if($('#url').val().trim() == "" || $('#comment').val().trim() == ""){
    		$('#message').html("Please enter both url and commment");
    	} else{
			var url = $('#url').val().replace(/</g, "&lt;").replace(/>/g, "&gt;");
			var comment = $('#comment').val().replace(/</g, "&lt;").replace(/>/g, "&gt;");
			$('#url').val(url);
			$('#comment').val(comment);
    		$("#bookmark_form").submit();
    	}
    });
});
</script>
</html>