package TaskManagementSource.entities.entity;

import TaskManagementSource.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pictures")
@Getter @Setter @NoArgsConstructor
public class Picture extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToMany
    @JoinColumn(name = "user_id")
    private List<User> users = new ArrayList<>();
}
