<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
<script type="text/javascript" src="jquery-1.11.3.min.js"></script>
</head>
<body>
	<form action="UserServlet?action=changePassword" method="post"
		id="change_password_form">
		Password:<input type="password" name="password" id="password">
		<input type="button" value="Change Password" id="change_password">
	</form>
	<div id="message">
	</div>
</body>

<script type="text/javascript">
	$("#change_password").click(
			function() {
				if ($('#password').val().trim() == "") {
					$('#message').html("Please enter the Password");
				} else {
					var new_password = $('#password').val().replace(/</g, "&lt;").replace(/>/g, "&gt;");
	        		$('#password').val(new_password);
					$("#change_password_form").submit();
				}
			});
</script>
</html>