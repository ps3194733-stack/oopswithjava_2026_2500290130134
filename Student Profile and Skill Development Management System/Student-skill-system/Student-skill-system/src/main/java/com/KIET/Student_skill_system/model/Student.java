package com.KIET.Student_skill_system.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

public class Student {
    private String studentId;
    private String studentName;
    private String email;
    private String courseName;
    private double programmingScore;
    private double mathematicsScore;
    private List<String> technicalSkills = new ArrayList<>();

    public Student() {
    }

    public Student(String studentId, String studentName, String email, String courseName,
            double programmingScore, double mathematicsScore, List<String> technicalSkills) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.email = email;
        this.courseName = courseName;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        if (technicalSkills != null)
            this.technicalSkills = new ArrayList<>(technicalSkills);
    }

    // Getters & Setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<String> technicalSkills) {
        this.technicalSkills = (technicalSkills != null) ? new ArrayList<>(technicalSkills) : new ArrayList<>();
    }

    // Q2.1: Average Score
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public double getAverageScore() {
        return Math.round(((programmingScore + mathematicsScore) / 2.0) * 100.0) / 100.0;
    }

    // Q2.2: Performance Category
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getPerformanceCategory() {
        double avg = getAverageScore();
        if (avg >= 85.0)
            return "Excellent";
        if (avg >= 70.0)
            return "Good";
        if (avg >= 50.0)
            return "Satisfactory";
        return "Needs Improvement";
    }

    // Q2.3: Stronger Subject
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getStrongerSubject() {
        if (programmingScore > mathematicsScore)
            return "Programming";
        if (mathematicsScore > programmingScore)
            return "Mathematics";
        return "Equal in Both";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Student s))
            return false;
        return Objects.equals(studentId, s.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "Student{id='" + studentId + "', name='" + studentName + "', avg=" + getAverageScore() +
                ", category='" + getPerformanceCategory() + "', stronger='" + getStrongerSubject() +
                "', skills=" + technicalSkills + "}";
    }
}