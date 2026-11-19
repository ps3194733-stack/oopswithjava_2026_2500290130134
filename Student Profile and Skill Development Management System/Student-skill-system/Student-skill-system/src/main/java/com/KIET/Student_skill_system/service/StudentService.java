package com.KIET.Student_skill_system.service;

import com.KIET.Student_skill_system.model.Student;
import com.KIET.Student_skill_system.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // Q2.8 + Q3.1: Add student with duplicate ID & score (0-100) validation
    public Student addStudent(Student s) {
        if (s.getStudentId() == null || s.getStudentId().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student ID is required");
        if (repo.existsById(s.getStudentId().trim()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate Student ID: " + s.getStudentId());
        validateScores(s.getProgrammingScore(), s.getMathematicsScore());

        s.setStudentId(s.getStudentId().trim());
        s.setTechnicalSkills(cleanSkills(s.getTechnicalSkills()));
        return repo.save(s);
    }

    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // Q2.4 + Q3.3: Search by ID, Email, Course
    public Student getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id));
    }

    public Student getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found: " + email));
    }

    public List<Student> getByCourse(String course) {
        return repo.findByCourse(course);
    }

    // Q2.5 + Q3.6: Merit list sorted descending by average score
    public List<Student> getMeritList() {
        List<Student> list = new ArrayList<>(repo.findAll());
        list.sort(Comparator.comparingDouble(Student::getAverageScore).reversed());
        return list;
    }

    // Q2.6: Students having the same technical skills
    public Map<String, List<String>> getSameTechnicalSkills() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        for (Student s : repo.findAll()) {
            for (String skill : new HashSet<>(s.getTechnicalSkills())) {
                map.computeIfAbsent(skill.toLowerCase(), k -> new ArrayList<>())
                        .add(s.getStudentId() + " (" + s.getStudentName() + ")");
            }
        }
        map.entrySet().removeIf(e -> e.getValue().size() < 2);
        return map;
    }

    // Q2.7: Programming < 50 (Needs Improvement) AND Mathematics >= 75
    public List<Student> getWeakProgStrongMath() {
        return repo.findAll().stream()
                .filter(s -> s.getProgrammingScore() < 50.0 && s.getMathematicsScore() >= 75.0)
                .toList();
    }

    // Q3.4: Update scores and skills
    public Student updateScoresAndSkills(String id, Student req) {
        if (!repo.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id);
        validateScores(req.getProgrammingScore(), req.getMathematicsScore());
        repo.updateScoresAndSkills(id, req.getProgrammingScore(), req.getMathematicsScore(),
                cleanSkills(req.getTechnicalSkills()));
        return getById(id);
    }

    // Q3.5: Delete student
    public void deleteStudent(String id) {
        if (repo.deleteById(id) == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found: " + id);
    }

    private void validateScores(double prog, double math) {
        if (prog < 0 || prog > 100 || math < 0 || math > 100)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Scores must be between 0 and 100");
    }

    private List<String> cleanSkills(List<String> skills) {
        if (skills == null)
            return new ArrayList<>();
        Set<String> set = new LinkedHashSet<>();
        for (String sk : skills)
            if (sk != null && !sk.isBlank())
                set.add(sk.trim());
        return new ArrayList<>(set);
    }
}