package Online_Meansreang.Meangsreang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Online_Meansreang.Meangsreang.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}