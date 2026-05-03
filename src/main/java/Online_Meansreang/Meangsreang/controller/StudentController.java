package Online_Meansreang.Meangsreang.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Online_Meansreang.Meangsreang.entity.Course;
import Online_Meansreang.Meangsreang.entity.Student;
import Online_Meansreang.Meangsreang.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Student saveStudent() {

        Course c1 = Course.builder()
                .title("Java")
                .build();

        Course c2 = Course.builder()
                .title("Spring Boot")
                .build();

        Student student = Student.builder()
                .name("John Doe")
                .courses(Set.of(c1, c2))
                .build();

        return repo.save(student);
    }

    @GetMapping
    public java.util.List<Student> getAll() {
        return repo.findAll();
    }
}