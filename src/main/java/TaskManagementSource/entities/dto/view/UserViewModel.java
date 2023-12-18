package TaskManagementSource.entities.dto.view;

import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.enums.Level;
import TaskManagementSource.entities.enums.UserRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter @Setter @NoArgsConstructor @SuperBuilder
public class UserViewModel {

    private Long id;
    private String username;
    private Level level;
    private String fullName;
    private Integer age;
    private Role role;
    private String imageUrl;
}
