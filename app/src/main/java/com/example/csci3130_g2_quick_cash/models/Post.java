package com.example.csci3130_g2_quick_cash.models;

import java.io.Serializable;
import java.util.Objects;

public class Post implements Serializable {
    private String postId;
    private String postCreatedBy;
    private String postTitle;
    private String postDescription;
    private boolean postUrgency;
    private String postCreationDate;
    private String postSalary;
    private String postExpectedTime;
    private String postLocation;
    private boolean isCompleted;
    private String employeeUserId;

    public Post() {
    }

    public Post(String postId, String postCreatedBy, String postTitle, String postDescription, boolean postUrgency, String postCreationDate, String postSalary, String postExpectedTime, String postLocation) {
        this.postId = postId;
        this.postCreatedBy = postCreatedBy;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postUrgency = postUrgency;
        this.postCreationDate = postCreationDate;
        this.postSalary = postSalary;
        this.postExpectedTime = postExpectedTime;
        this.postLocation = postLocation;
    }
    public Post(String postId, String postCreatedBy, String postTitle, String postDescription, boolean postUrgency, String postCreationDate, String postSalary, String postExpectedTime, String postLocation, boolean isCompleted) {
        this.postId = postId;
        this.postCreatedBy = postCreatedBy;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postUrgency = postUrgency;
        this.postCreationDate = postCreationDate;
        this.postSalary = postSalary;
        this.postExpectedTime = postExpectedTime;
        this.postLocation = postLocation;
        this.isCompleted = isCompleted;
    }

    public Post(String postCreatedBy, String postTitle, String postDescription, boolean postUrgency, String postCreationDate, String postSalary, String postExpectedTime, String postLocation) {
        this.postCreatedBy = postCreatedBy;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postUrgency = postUrgency;
        this.postCreationDate = postCreationDate;
        this.postSalary = postSalary;
        this.postExpectedTime = postExpectedTime;
        this.postLocation = postLocation;
    }

    public String getPostCreationDate() {
        return postCreationDate;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public String getPostExpectedTime() {
        return postExpectedTime;
    }

    public String getPostLocation() {
        return postLocation;
    }

    public String getPostSalary() {
        return postSalary;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public boolean getPostUrgency() {
        return postUrgency;
    }

    public String getPostCreatedBy() {
        return postCreatedBy;
    }

    public String getPostId() {
        return postId;
    }

    public String getEmployeeUserId() {
        return employeeUserId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setPostUrgency(boolean postUrgency) {
        this.postUrgency = postUrgency;
    }

    public void setPostCreationDate(String postCreationDate) {
        this.postCreationDate = postCreationDate;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public void setPostExpectedTime(String postExpectedTime) {
        this.postExpectedTime = postExpectedTime;
    }

    public void setPostLocation(String postLocation) {
        this.postLocation = postLocation;
    }

    public void setPostSalary(String postSalary) {
        this.postSalary = postSalary;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostCreatedBy(String postCreatedBy) {
        this.postCreatedBy = postCreatedBy;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setEmployeeUserId(String employeeUserId) {
        this.employeeUserId = employeeUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId) && Objects.equals(postCreatedBy, post.postCreatedBy) && Objects.equals(postTitle, post.postTitle) && Objects.equals(postDescription, post.postDescription) && Objects.equals(postUrgency, post.postUrgency) && Objects.equals(postCreationDate, post.postCreationDate) && Objects.equals(postSalary, post.postSalary) && Objects.equals(postExpectedTime, post.postExpectedTime) && Objects.equals(postLocation, post.postLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, postCreatedBy, postTitle, postDescription, postUrgency, postCreationDate, postSalary, postExpectedTime, postLocation);
    }
}
