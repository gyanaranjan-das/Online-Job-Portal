<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jobportal.model.User" %>
<%@ page import="com.jobportal.model.Job" %>
<%@ page import="com.jobportal.dao.JobDAO" %>
<%@ page import="java.util.List" %>

<%
    // Security Check: Ensure a job seeker is logged in.
    User currentUser = (User) session.getAttribute("currentUser");
    if (currentUser == null || !"job_seeker".equals(currentUser.getRole())) {
        response.sendRedirect("login.jsp");
        return; // Stop processing the rest of the page
    }

    // Fetch all available jobs
    JobDAO jobDAO = new JobDAO();
    List<Job> allJobs = jobDAO.getAllJobs();

    // Get and then remove message from session
    String message = (String) session.getAttribute("message");
    if (message != null) {
        session.removeAttribute("message");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Job Seeker Dashboard</title>
</head>
<body>

    <div class="header">
        <h1>Job Portal</h1>
        <span>Welcome, <%= currentUser.getFullName() %>!</span>
        <a href="logout">Logout</a>
    </div>

    <div class="container">
        <h2>Available Jobs</h2>

        <% if (message != null) { %>
            <p class="message"><%= message %></p>
        <% } %>

        <% if (allJobs.isEmpty()) { %>
            <p>No jobs are available at the moment. Please check back later!</p>
        <% } else { %>
            <% for (Job job : allJobs) { %>
                <div class="job-card">
                    <h3><%= job.getTitle() %></h3>
                    <p class="location"><strong>Location:</strong> <%= job.getLocation() %></p>
                    <p class="description"><%= job.getDescription() %></p>
                    <a href="apply?jobId=<%= job.getJobId() %>" class="apply-btn">Apply Now</a>
                </div>
            <% } %>
        <% } %>
    </div>

</body>
</html>