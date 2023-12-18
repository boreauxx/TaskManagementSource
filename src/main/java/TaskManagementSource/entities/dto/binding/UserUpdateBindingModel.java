package TaskManagementSource.entities.dto.binding;

import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.enums.Level;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @SuperBuilder
public class UserUpdateBindingModel {

    private Long id;
    @Size(min = 2, message = "{user.username.length}")
    private String username;
    @Size(min = 2, message = "{user.full-name.length}")
    private String fullName;
    @Positive(message = "{user.age}")
    private Integer age;
    private Level level;
    private Role role;
}
