package TaskManagementSource.entities.dto.binding;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ReCaptchaBindingModel {

    private boolean success;
    private List<String> errorCodes;

    public ReCaptchaBindingModel setSuccess(boolean success){
        this.success = success;
        return this;
    }
    public ReCaptchaBindingModel setErrorCodes(List<String> errorCodes){
        this.errorCodes = errorCodes;
        return this;
    }

}
