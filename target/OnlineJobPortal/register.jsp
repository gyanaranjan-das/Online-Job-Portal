<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Online Job Portal</title>
</head>
<body>
    <div class="container">
        <h2>Create Your Account</h2>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error"><%= request.getAttribute("errorMessage") %></p>
        <% } %>

        <form action="register" method="POST">
            <div class="form-group">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label>Register as:</label>
                <div class="radio-group">
                    <input type="radio" id="job_seeker" name="role" value="job_seeker" checked>
                    <label for="job_seeker">Job Seeker</label>
                    <input type="radio" id="recruiter" name="role" value="recruiter">
                    <label for="recruiter">Recruiter</label>
                </div>
            </div>
            <button type="submit">Register</button>
        </form>
        <div class="login-link">
            <p>Already have an account? <a href="login.jsp">Login here</a></p>
        </div>
    </div>
</body>
</html>