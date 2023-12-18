package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.dto.binding.ReCaptchaBindingModel;

import java.util.Optional;

public interface ReCaptchaService {

    Optional<ReCaptchaBindingModel> verify(String token);
}
