package TaskManagementSource.entities.dto.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@Getter @Setter @NoArgsConstructor @SuperBuilder
public class CreateCommentBindingModel {

    private Long taskId;

    @Length(min = 10, message = "{comment.length}")
    private String content;
}
