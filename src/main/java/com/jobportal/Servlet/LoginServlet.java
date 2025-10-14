package com.jobportal.Servlet;

import com.jobportal.DAO.UserDAO;
import com.jobportal.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 1. Find the user by email
        User user = userDAO.getUserByEmail(email);

        // 2. Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            // --- Authentication Successful ---

            // 3. Create a new session
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", user); // Store user object in session

            // 4. Redirect based on user role
            if ("job_seeker".equals(user.getRole())) {
                response.sendRedirect("jobseeker_dashboard.jsp");
            } else if ("recruiter".equals(user.getRole())) {
                response.sendRedirect("recruiter_dashboard.jsp");
            } else {
                // Handle other roles or default redirect
                response.sendRedirect("index.jsp");
            }
        } else {
            // --- Authentication Failed ---
            request.setAttribute("errorMessage", "Invalid email or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}