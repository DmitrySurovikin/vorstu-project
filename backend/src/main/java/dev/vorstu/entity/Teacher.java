package dev.vorstu.entity;

import jakarta.persistence.*;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vorstu.entity.User;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fio;

    @JsonIgnore
    @ManyToMany(mappedBy = "teachers")
    private Set<Group> groups;

    @JsonIgnore
    @OneToOne(mappedBy = "teacher")
    private User user;

    public Teacher(String fio) {
        this.fio = fio;
    }

}
