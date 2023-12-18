package TaskManagementSource.entities.dto.binding;

import TaskManagementSource.entities.entity.Comment;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @SuperBuilder
public class UpdateTaskBindingModel {

    private Long id;
    @Size(min = 3, message = "Title length cannot  be less than 3 characters.")
    private String title;
    @Size(min = 15, message = "Description length cannot be less than 15 characters.")
    private String description;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @FutureOrPresent(message = "Due date cannot be in the past.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    private LocalDate dueDate;
    private User assignee;
    private User assignor;
    private Set<Comment> comments;
}
