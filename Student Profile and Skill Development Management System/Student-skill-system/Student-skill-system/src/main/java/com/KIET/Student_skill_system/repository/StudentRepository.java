package com.KIET.Student_skill_system.repository;

import com.KIET.Student_skill_system.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        String id = rs.getString("student_id");
        List<String> skills = jdbc.queryForList(
                "SELECT skill_name FROM student_skills WHERE student_id = ?", String.class, id);
        return new Student(
                id,
                rs.getString("student_name"),
                rs.getString("email"),
                rs.getString("course_name"),
                rs.getDouble("programming_score"),
                rs.getDouble("mathematics_score"),
                skills);
    }

    public boolean existsById(String id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM students WHERE student_id = ?", Integer.class, id);
        return count != null && count > 0;
    }

    @Transactional
    public Student save(Student s) {
        jdbc.update("INSERT INTO students VALUES (?, ?, ?, ?, ?, ?)",
                s.getStudentId(), s.getStudentName(), s.getEmail(),
                s.getCourseName(), s.getProgrammingScore(), s.getMathematicsScore());
        insertSkills(s.getStudentId(), s.getTechnicalSkills());
        return findById(s.getStudentId()).orElse(s);
    }

    public List<Student> findAll() {
        return jdbc.query("SELECT * FROM students", this::mapRow);
    }

    public Optional<Student> findById(String id) {
        return jdbc.query("SELECT * FROM students WHERE student_id = ?", this::mapRow, id).stream().findFirst();
    }

    public Optional<Student> findByEmail(String email) {
        return jdbc.query("SELECT * FROM students WHERE LOWER(email) = LOWER(?)", this::mapRow, email).stream()
                .findFirst();
    }

    public List<Student> findByCourse(String course) {
        return jdbc.query("SELECT * FROM students WHERE LOWER(course_name) = LOWER(?)", this::mapRow, course);
    }

    @Transactional
    public void updateScoresAndSkills(String id, double prog, double math, List<String> skills) {
        jdbc.update("UPDATE students SET programming_score = ?, mathematics_score = ? WHERE student_id = ?", prog, math,
                id);
        jdbc.update("DELETE FROM student_skills WHERE student_id = ?", id);
        insertSkills(id, skills);
    }

    public int deleteById(String id) {
        return jdbc.update("DELETE FROM students WHERE student_id = ?", id);
    }

    private void insertSkills(String id, List<String> skills) {
        if (skills == null)
            return;
        for (String skill : skills) {
            if (skill != null && !skill.isBlank()) {
                jdbc.update("INSERT INTO student_skills (student_id, skill_name) VALUES (?, ?)", id, skill.trim());
            }
        }
    }
}