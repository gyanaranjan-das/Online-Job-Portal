<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Online Job Portal</title>
</head>
<body>
    <div class="container">
        <h2>Create Your Account</h2>
        <% if (request.getAttribute("errorMessage")!=null){
            <p><%= request.getttribute("errorMessage")>
        %}%>

        <form action="register" method="POST">
            
        </form>
    </div>
    
</body>
</html>