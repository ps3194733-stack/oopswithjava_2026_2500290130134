package com.KIET.Student_skill_system.controller;

import com.KIET.Student_skill_system.model.Student;
import com.KIET.Student_skill_system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping // Q3.1: Add student
    @ResponseStatus(HttpStatus.CREATED)
    public Student add(@RequestBody Student s) {
        return service.addStudent(s);
    }

    @GetMapping // Q3.2: Get all students
    public List<Student> getAll() {
        return service.getAllStudents();
    }

    @GetMapping("/{id}") // Q3.3 & Q2.4: Search by ID
    public Student getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/search/email") // Q2.4: Search by email
    public Student getByEmail(@RequestParam String email) {
        return service.getByEmail(email);
    }

    @GetMapping("/search/course") // Q2.4: Search by course
    public List<Student> getByCourse(@RequestParam String course) {
        return service.getByCourse(course);
    }

    @PutMapping("/{id}") // Q3.4: Update scores & skills
    public Student update(@PathVariable String id, @RequestBody Student req) {
        return service.updateScoresAndSkills(id, req);
    }

    @DeleteMapping("/{id}") // Q3.5: Delete student
    public Map<String, String> delete(@PathVariable String id) {
        service.deleteStudent(id);
        return Map.of("message", "Deleted student: " + id);
    }

    @GetMapping("/merit-list") // Q3.6 & Q2.5: Merit list
    public List<Student> meritList() {
        return service.getMeritList();
    }

    @GetMapping("/same-skills") // Q2.6: Shared skills
    public Map<String, List<String>> sameSkills() {
        return service.getSameTechnicalSkills();
    }

    @GetMapping("/weak-prog-strong-math") // Q2.7: Prog < 50 & Math >= 75
    public List<Student> weakProgStrongMath() {
        return service.getWeakProgStrongMath();
    }
}
