package Online_Meansreang.Meangsreang.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @Generated(strategy = GenerationType.INDENTTITY)
    private long id;
    private String title;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;

    public Course() {
    }

    pubilc Course

}
