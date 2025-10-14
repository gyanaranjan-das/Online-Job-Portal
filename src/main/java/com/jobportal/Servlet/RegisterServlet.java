package com.jobportal.Servlet;

import com.jobportal.DAO.UserDAO;
import com.jobportal.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Retrieve form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 2. Create a new User object
        User newUser = new User(email, password, fullName, role);

        // 3. Check if user already exists
        if (userDAO.getUserByEmail(email) != null) {
            // User already exists, set an error message and forward back to register page
            request.setAttribute("errorMessage", "An account with this email already exists.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            // 4. Attempt to register the user
            boolean isRegistered = userDAO.registerUser(newUser);

            if (isRegistered) {
                // Redirect to login page with a success message
                response.sendRedirect("login.jsp?success=true");
            } else {
                // Set an error message and forward back to the register page
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }
}