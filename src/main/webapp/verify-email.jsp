
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.codingnuts.app.ws.service.UserService"%>
<%@page import="com.codingnuts.app.ws.service.impl.UserServiceImpl"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Email Verification Page</h1>
        
        <%
          
         String token = request.getParameter("token");
         UserService userService = new UserServiceImpl();
         boolean isEmailVerified = userService.verifyEmail(StringEscapeUtils.escapeHtml4(token));
         
        if(isEmailVerified)
        {
        %>
           
        <p>Thank you! Your email has been verified!</p>   
           
         <%   
        } else {
        
        %>
        
        <p>Sorry, your email address has not been verified. Try again.</p>

       <%
        }

        %>
        
    </body>
</html>
