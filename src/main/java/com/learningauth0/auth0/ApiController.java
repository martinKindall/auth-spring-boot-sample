package com.learningauth0.auth0;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class ApiController {

    @GetMapping(value = "/welcome")
    public Mono<String> privateEndpoint() {
        return Mono.just("All good. You can see this because you are Authenticated.");
    }

    @GetMapping(value = "/private-scoped")
    public Mono<String> publicEndpoint() {
        return Mono.just("All good. You have the right scope to see this.");
    }
}
