package com.learningauth0.auth0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should configure CORS for their use case.
@CrossOrigin(origins = "*")
public class ApiController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    @GetMapping(value = "/welcome")
    public Mono<String> privateEndpoint() {
        return Mono.just("All good. You can see this because you are Authenticated.");
    }

    @GetMapping(value = "/private-scoped")
    public Mono<Map<String, String>> scopedEndpoint() {
        LOG.debug("Scoped route being requested");
        return Mono.just(new HashMap<>(){{
            put("description", "All good. You have the right scope to see this.");
        }});
    }

    @GetMapping(value = "/private-scoped/write")
    public Mono<String> scopedWriteEndpoint() {
        return Mono.just("All good. You have the right scope to write here.");
    }
}
