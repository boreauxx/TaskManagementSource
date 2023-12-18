package TaskManagementSource.entities.entity;

import TaskManagementSource.entities.enums.Level;
import TaskManagementSource.util.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "users")
    private List<Picture> picture;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "assignee")
    private List<Task> myTasks;

    // NEW
    @OneToMany(mappedBy = "assignor")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments;

    public User setDefault(Role role, Level level){
        this.role = role;
        this.level = level;
        return this;
    }
}
