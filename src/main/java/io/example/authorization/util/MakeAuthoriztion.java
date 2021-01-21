package io.example.authorization.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Slf4j
public class MakeAuthoriztion {

    private static void makeAuthorizationRequestHeader() {
        String oauthClientId = "client";
        String oauthClientSecret = "secret";

        Base64.Encoder encoder = Base64.getEncoder();
        try {
            String toEncodeString = String.format("%s:%s", oauthClientId, oauthClientSecret);
            String authorizationRequestHeader = "Basic " + encoder.encodeToString(toEncodeString.getBytes("UTF-8"));
            log.debug("AuthorizationRequestHeader : [{}] ", authorizationRequestHeader);            // Y2xpZW50OnNlY3JldA==
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
