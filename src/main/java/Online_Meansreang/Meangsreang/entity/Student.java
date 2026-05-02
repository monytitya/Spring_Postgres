package Online_Meansreang.Meangsreang.entity;

import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Course {

    @Id
    @Generated(strategy = GenerationType.INDENTITY)
    private long id;
    private String name;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinTable(
        name ="student_id",
        joinColumns =  @JoinCulumn( ="student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")

    )
    private Set<Course> course;

    public Set<Course> getCourse() {
        return course;
    }

    public Student(){}

    public Student(String name, Set<Course> course){
        this.name = name;
        this.course = course;
    }

}
