package Online_Meansreang.Meangsreang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "students")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;
}