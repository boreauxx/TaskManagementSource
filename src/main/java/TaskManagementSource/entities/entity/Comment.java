package TaskManagementSource.entities.entity;

import TaskManagementSource.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "comments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Comment extends BaseEntity {

    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "commented_at")
    private String commentedAt;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private User commenter;
}
