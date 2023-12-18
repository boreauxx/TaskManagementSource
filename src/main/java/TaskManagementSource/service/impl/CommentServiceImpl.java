package TaskManagementSource.service.impl;

import TaskManagementSource.entities.dto.binding.CreateCommentBindingModel;
import TaskManagementSource.entities.entity.Comment;
import TaskManagementSource.entities.entity.Task;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.repository.CommentRepository;
import TaskManagementSource.service.interfaces.CommentService;
import TaskManagementSource.service.interfaces.TaskService;
import TaskManagementSource.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;

    @Override
    public void create(CreateCommentBindingModel commentBindingModel, UserDetails commenter) {
        Task task = this.taskService.findTaskById(commentBindingModel.getTaskId());
        String commentedAt = LocalDate.now().toString();
        User user = this.userService.findUserByUsername(commenter.getUsername());
        Comment comment = new Comment(commentBindingModel.getContent(),
                commentedAt,
                task,
                user);
        commentRepository.save(comment);
    }
}
