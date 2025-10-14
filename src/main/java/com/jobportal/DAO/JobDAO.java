package com.jobportal.DAO;

import com.jobportal.model.Job;
import com.jobportal.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    /**
     * Adds a new job to the database.
     */
    public boolean addJob(Job job) {
        String sql = "INSERT INTO jobs (recruiter_id, title, description, location) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, job.getRecruiterId());
            pstmt.setString(2, job.getTitle());
            pstmt.setString(3, job.getDescription());
            pstmt.setString(4, job.getLocation());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all jobs posted by a specific recruiter.
     */
    public List<Job> getJobsByRecruiter(int recruiterId) {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE recruiter_id = ? ORDER BY posted_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recruiterId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("job_id"));
                job.setRecruiterId(rs.getInt("recruiter_id"));
                job.setTitle(rs.getString("title"));
                job.setDescription(rs.getString("description"));
                job.setLocation(rs.getString("location"));
                job.setPostedAt(rs.getTimestamp("posted_at"));
                jobs.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }
}