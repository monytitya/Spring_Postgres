package Online_Meansreang.Meangsreang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "courses")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students;
}