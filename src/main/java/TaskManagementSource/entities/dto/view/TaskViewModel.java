package TaskManagementSource.entities.dto.view;

import TaskManagementSource.entities.entity.Comment;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @SuperBuilder
public class TaskViewModel {

    private Long id;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    private Status status;
    private User assignee;
    private User assignor;
    private List<Comment> comments;
}
