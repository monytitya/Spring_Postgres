package Online_Meansreang.Meangsreang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Online_Meansreang.Meangsreang.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :id")
    Student findByIdWithCourses(@Param("id") Long id);
}