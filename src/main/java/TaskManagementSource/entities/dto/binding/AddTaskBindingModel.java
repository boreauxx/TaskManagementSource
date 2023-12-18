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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class AddTaskBindingModel {

    @Size(min = 3, message = "Title length cannot  be less than 3 characters.")
    private String title;
    @Size(min = 15, message = "Description length cannot be less than 15 characters.")
    private String description;
    @NotNull(message = "Status cannot be null")
    private Status status;
    @FutureOrPresent(message = "Due date cannot be in the past.")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;
    private User assignor;
    private Set<Comment> comments;

    public AddTaskBindingModel setTitle(String title){
        this.title = title;
        return this;
    }
    public AddTaskBindingModel setDescription(String description){
        this.description = description;
        return this;
    }
    public AddTaskBindingModel setStatus(Status status){
        this.status = status;
        return this;
    }
    public AddTaskBindingModel setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
        return this;
    }
    public AddTaskBindingModel setAssignee(User assignor){
        this.assignor = assignor;
        return this;
    }
    public AddTaskBindingModel setComments(Set<Comment> comments){
        this.comments = comments;
        return this;
    }
}
