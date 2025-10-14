package com.jobportal.Servlet;

import com.jobportal.DAO.JobDAO;
import com.jobportal.model.Job;
import com.jobportal.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addJob")
public class AddJobServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JobDAO jobDAO;

    public void init() {
        jobDAO = new JobDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("currentUser") : null;

        // Security check: ensure a recruiter is logged in
        if (currentUser == null || !"recruiter".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get form parameters
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        int recruiterId = currentUser.getUserId();

        Job newJob = new Job(recruiterId, title, description, location);

        // Add job to database
        boolean success = jobDAO.addJob(newJob);

        if (success) {
            session.setAttribute("message", "Job posted successfully!");
        } else {
            session.setAttribute("message", "Error: Could not post job.");
        }

        // Redirect back to the dashboard
        response.sendRedirect("recruiter_dashboard.jsp");
    }
}