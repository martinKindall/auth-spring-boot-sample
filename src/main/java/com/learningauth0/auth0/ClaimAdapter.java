package com.learningauth0.auth0;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;

import java.util.Collections;
import java.util.Map;

public class ClaimAdapter implements
        Converter<Map<String, Object>, Map<String, Object>> {

    private static final Logger LOG = LoggerFactory.getLogger(ClaimAdapter.class);

    private final MappedJwtClaimSetConverter delegate =
            MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);
        LOG.debug("Here is the Scope claim {}", convertedClaims.get("scope"));
        return convertedClaims;
    }
}
