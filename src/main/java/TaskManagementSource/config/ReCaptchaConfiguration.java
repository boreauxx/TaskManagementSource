package TaskManagementSource.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.recaptcha")
@Getter
public class ReCaptchaConfiguration {

    private String site;
    private String secret;

    public ReCaptchaConfiguration setSite(String site){
        this.site = site;
        return this;
    }
    public ReCaptchaConfiguration setSecret(String secret){
        this.secret = secret;
        return this;
    }
}
