package TaskManagementSource.service.impl;

import TaskManagementSource.config.ReCaptchaConfiguration;
import TaskManagementSource.entities.dto.binding.ReCaptchaBindingModel;
import TaskManagementSource.service.interfaces.ReCaptchaService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReCaptchaServiceImpl implements ReCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaServiceImpl.class);
    private final WebClient webClient;
    private final ReCaptchaConfiguration reCaptchaConfiguration;

    @Override
    public Optional<ReCaptchaBindingModel> verify(String token) {
        ReCaptchaBindingModel response = webClient
                .post()
                .uri(this::buildReCaptchaURI)
                .body(BodyInserters
                        .fromFormData("secret", reCaptchaConfiguration.getSecret())
                        .with("response",token))
                .retrieve()
                .bodyToMono(ReCaptchaBindingModel.class)
                .doOnError(t -> LOGGER.error("Error fetching google response...", t))
                .onErrorComplete()
                .block();
        return Optional.ofNullable(response);
    }
    private URI buildReCaptchaURI(UriBuilder uriBuilder) {
        // REST endpoint for google verification.
        // https://www.google.com/recaptcha/api/siteverify
        return uriBuilder
                .scheme("https")
                .host("www.google.com")
                .path("/recaptcha/api/siteverify")
                .build();
    }
}

