package com.jobportal.model;

import java.sql.Timestamp;

public class Job {
    private int jobId;
    private int recruiterId;
    private String title;
    private String description;
    private String location;
    private Timestamp postedAt;

    // Constructors, Getters, and Setters
    public Job() {}

    public Job(int recruiterId, String title, String description, String location) {
        this.recruiterId = recruiterId;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    // --- Add all getters and setters for each field ---
    // (e.g., getJobId, setJobId, getRecruiterId, setRecruiterId, etc.)

    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    public int getRecruiterId() { return recruiterId; }
    public void setRecruiterId(int recruiterId) { this.recruiterId = recruiterId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Timestamp getPostedAt() { return postedAt; }
    public void setPostedAt(Timestamp postedAt) { this.postedAt = postedAt; }
}