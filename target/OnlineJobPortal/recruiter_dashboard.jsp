<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jobportal.model.User" %>
<%@ page import="com.jobportal.model.Job" %>
<%@ page import="com.jobportal.dao.JobDAO" %>
<%@ page import="java.util.List" %>

<%
    // Security Check: Ensure a recruiter is logged in.
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null || !"recruiter".equals(currentUser.getRole())) {
        response.sendRedirect("login.jsp");
        return; // Stop processing the rest of the page
    }

    // Fetch the jobs for this recruiter
    JobDAO jobDAO = new JobDAO();
    List<Job> jobList = jobDAO.getJobsByRecruiter(currentUser.getUserId());

    // Get and then remove the message from session to show it only once
    String message = (String) session.getAttribute("message");
    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Recruiter Dashboard</title>
</head>
<body>

    <div class="header">
        <h1>Recruiter Dashboard</h1>
        <span>Welcome, <%= currentUser.getFullName() %>!</span>
        <a href="logout">Logout</a>
    </div>

    <% if (message != null) { %>
        <p class="message"><%= message %></p>
    <% } %>

    <div class="container">
        <div class="form-container">
            <h2>Post a New Job</h2>
            <form action="addJob" method="POST">
                <div class="form-group">
                    <label for="title">Job Title</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="location">Location</label>
                    <input type="text" id="location" name="location" placeholder="e.g., Remote, New York, NY" required>
                </div>
                <div class="form-group">
                    <label for="description">Job Description</label>
                    <textarea id="description" name="description" required></textarea>
                </div>
                <button type="submit">Post Job</button>
            </form>
        </div>

        <div class="jobs-container">
            <h2>Your Job Listings</h2>
            <% if (jobList.isEmpty()) { %>
                <p>You have not posted any jobs yet.</p>
            <% } else { %>
                <% for (Job job : jobList) { %>
                    <div class="job-card">
                        <h3><%= job.getTitle() %></h3>
                        <p><strong>Location:</strong> <%= job.getLocation() %></p>
                        <p><%= job.getDescription() %></p>
                        <small>Posted on: <%= job.getPostedAt() %></small>
                    </div>
                <% } %>
            <% } %>
        </div>
    </div>

</body>
</html>