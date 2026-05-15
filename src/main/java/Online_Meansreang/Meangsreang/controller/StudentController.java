package Online_Meansreang.Meangsreang.controller;

import Online_Meansreang.Meangsreang.entity.Student;
import Online_Meansreang.Meangsreang.repository.StudentRepository;
import Online_Meansreang.Meangsreang.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
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
    public Student getStudentById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updateStudent) {
        return service.updateStudent(id, updateStudent);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        repo.deleteById(id);
        return "Student is deleted successfully";
    }
}