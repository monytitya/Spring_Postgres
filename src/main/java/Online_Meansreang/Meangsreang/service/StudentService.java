package Online_Meansreang.Meangsreang.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import Online_Meansreang.Meangsreang.entity.Student;
import Online_Meansreang.Meangsreang.repository.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public Student createStudent(Student student) {
        if (student.getName() == null || student.getName().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be null or empty");
        }
        return repository.save(student);
    }

    public Optional<Student> getStudent(Long id) {
        return Optional.ofNullable(repository.findByIdWithCourses(id));
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return getStudent(id).map(student -> {
            if (updatedStudent.getName() != null && !updatedStudent.getName().isEmpty()) {
                student.setName(updatedStudent.getName());
            }
            if (updatedStudent.getCourses() != null) {
                student.setCourses(updatedStudent.getCourses());
            }
            return repository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
}