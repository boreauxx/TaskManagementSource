package TaskManagementSource.entities.entity;

import TaskManagementSource.entities.enums.Status;
import TaskManagementSource.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter @Setter @NoArgsConstructor
public class Task extends BaseEntity {

    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "due_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "assignor_id")
    private User assignor;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
