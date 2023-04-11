<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.codingnuts.app.ws.service.UserService"%>
<%@page import="com.codingnuts.app.ws.service.impl.UserServiceImpl"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Email Verification Response</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f5f5f5;
		}
		h1 {
			text-align: center;
		}
		p {
			margin-bottom: 20px;
		}
		.error {
			color: red;
		}
		.container {
          display: flex;
          justify-content: center;
          align-items: center;
          height: 100vh;
          background: #f5f5f5;
          font-family: Arial, sans-serif;
        }

        .verified {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          padding: 50px;
          background: #fff;
          border-radius: 5px;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
          text-align: center;
        }

        .fas {
          font-size: 60px;
          color: #008000;
          margin-bottom: 20px;
        }

        .success {
          font-size: 36px;
          font-weight: bold;
          color: #333;
          margin-bottom: 20px;
        }

        .message {
          font-size: 18px;
          color: #666;
          margin-bottom: 30px;
        }

        .button {
          display: inline-block;
          padding: 10px 20px;
          background: #008000;
          color: #fff;
          border-radius: 5px;
          transition: all 0.2s ease-in-out;
          text-decoration: none;
        }

        .button:hover {
          background: #005700;
        }

	</style>
</head>
<body>
	<div class="container">
		<%
		String token = request.getParameter("token");
        UserService userService = new UserServiceImpl();
        boolean isEmailVerified = userService.verifyEmail(StringEscapeUtils.escapeHtml4(token));
		if (isEmailVerified)
		{ %>
		    <div class="container">
            		<div class="verified">
            			<i class="fas fa-check-circle"></i>
            			<h2 class="success">Your email has been successfully verified.</h2>
            			<p class="message">Thank you for verifying your email address. You can now enjoy all the benefits of your account.</p>
            			<a href="https://github.com/Sarfaraz-Hussain" class="button">My Github</a>
            		</div>
            	</div>
		<%} else {%>
			<p class="error">Oops! We could not verify your email. Please try again later.</p>
		<%}%>
	</div>
</body>
</html>

