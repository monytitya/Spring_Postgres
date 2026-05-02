package Online_Meansreang.Meangsreang.controller;

import org.springframework.web.bind.annotation.*;

import Online_Meansreang.Meangsreang.entity.Course;
import Online_Meansreang.Meangsreang.entity.Student;
import Online_Meansreang.Meangsreang.repository.StudentRepository;

import java.util.Set;

@RestController
@RequestMapping("/students")
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