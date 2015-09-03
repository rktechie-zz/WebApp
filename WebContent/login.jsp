<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<script type="text/javascript" src="jquery-1.11.3.min.js"></script>
</head>
<body>
<form action="UserServlet?action=login" method="post" id="form1">
Email:<input type="email" name="email" id="email">
Password:<input type="password" name="password" id="password">
<input type="button" value="Login" id="login">
<input type="button" value="Register" id="register">
</form>
<div id="message">
</div>
</body>

<script type="text/javascript">
$(document).ready(function()
{
    $("#login").click(function()
    {
    	var email = $('#email').val();
    	var password = $('#password').val();
    	if($('#email').val().trim() == "" || $('#password').val().trim() == ""){
    		$('#message').html("Please enter both email and password");
    	} else{
    		var pattern = email.match(/^[a-zA-Z0-9]+@[a-zA-Z0-9.]+/);
    		if(pattern != null){
        		var new_password = $('#password').val().replace(/</g, "&lt;").replace(/>/g, "&gt;");
        		$('#password').val(new_password);
        		
    			$.ajax({
          		  url: "UserServlet?action=login",
          		  type: "post",
          		  data: {email: email, password: new_password},
          		  success: function(data){
          		      if(data == 'true'){
          		    	  window.location.href = "bookmark.jsp";
          		      } else{
              		      $('#message').html("Email and Password do not match.");
          		      }
          		  },
          		  error:function(){
          			  window.location.href = "error.jsp";
          		  }   
          		});
    		} else{
    			$('#message').html("Enter a valid Email Address.");
    		}
    	}
    });
    
    $("#register").click(function()
	{
    	var email = $('#email').val();
    	var password = $('#password').val();
    	if($('#email').val().trim() == "" || $('#password').val().trim() == ""){
    		$('#message').html("Please enter both email and password");
    	} else{
    		var pattern = email.match(/^[a-zA-Z0-9]+@[a-zA-Z0-9.]+/);
    		if(pattern != null){
        		$.ajax({
            		  url: "UserServlet?action=checkEmail",
            		  type: "post",
            		  data: {email: email, password: password},
            		  success: function(data){
            		      if(data == 'true'){
                		      $('#message').html("Email already exists. Please provide a new email.");
            		      } else{
            		    	  window.location.href = "register.html";
            		      }
            		  },
            		  error:function(){
            			  window.location.href = "error.jsp";
            		  }   
            		});
    		} else{
    			$('#message').html("Enter a valid Email Address.");
    		}
    	}
    	
	});
});
</script>
</html>