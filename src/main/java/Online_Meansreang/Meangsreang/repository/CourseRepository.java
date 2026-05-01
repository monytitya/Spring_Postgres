package Online_Meansreang.Meangsreang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Online_Meansreang.Meangsreang.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
