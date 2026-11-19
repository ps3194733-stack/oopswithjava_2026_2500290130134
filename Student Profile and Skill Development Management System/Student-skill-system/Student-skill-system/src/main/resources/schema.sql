CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(20) PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    programming_score DOUBLE NOT NULL CHECK (programming_score BETWEEN 0 AND 100),
    mathematics_score DOUBLE NOT NULL CHECK (mathematics_score BETWEEN 0 AND 100)
);

CREATE TABLE IF NOT EXISTS student_skills (
    skill_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL,
    skill_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);