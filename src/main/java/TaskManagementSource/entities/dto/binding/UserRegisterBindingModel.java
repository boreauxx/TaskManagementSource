package TaskManagementSource.entities.dto.binding;

import TaskManagementSource.validation.anotations.PasswordMatch;
import TaskManagementSource.validation.anotations.UniqueEmail;
import TaskManagementSource.validation.anotations.UniqueUsername;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@PasswordMatch
@Getter @Setter @NoArgsConstructor
public class UserRegisterBindingModel {

    @NotNull
    @UniqueUsername
    @Size(min = 2, message = "{user.username.length}")
    private String username;

    @NotNull
    @Size(min = 2, message = "{user.full-name.length}")
    private String fullName;

    @NotNull
    @UniqueEmail
    @Email(regexp = ".+[@].+", message = "{user.email}")
    private String email;

    @Positive(message = "{user.age}")
    private int age;

    @Size(min = 2, message = "{user.password.length}")
    private String password;

    @Size(min = 2, message = "{user.confirm-password.length}")
    private String confirmPassword;


}
