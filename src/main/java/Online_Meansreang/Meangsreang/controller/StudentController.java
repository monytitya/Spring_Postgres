package Online_Meansreang.Meangsreang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Online_Meansreang.Meangsreang.entity.Student;
import Online_Meansreang.Meangsreang.repository.StudentRepository;
import Online_Meansreang.Meangsreang.service.StudentService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repo;
    private final StudentService service;

    public StudentController(StudentRepository repo, StudentService service) {
        this.repo = repo;
        this.service = service;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updateStudent) {
        return service.updateStudent(id, updateStudent);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        repo.deleteById(id);
        return "Student is deleted successfully";
    }

}