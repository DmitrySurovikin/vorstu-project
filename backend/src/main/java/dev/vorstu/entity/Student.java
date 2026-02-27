package dev.vorstu.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.vorstu.entity.User;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import dev.vorstu.entity.Group;


@Entity
@Table(name = "students")
public class Student {
    public Student() {}

    public Student(Long id, String fio, Group group, String phoneNumber) {
        this(fio, group, phoneNumber);
        this.id = id;
    }

    public Student(String fio, Group group, String phoneNumber) {
        this.fio = fio;
        this.group = group;
        this.phoneNumber = phoneNumber;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String fio;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "student")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }

    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
