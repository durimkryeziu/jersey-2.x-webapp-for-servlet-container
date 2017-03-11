package com.programmingskillz.providers;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author Durim Kryeziu
 */
@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_TYPE = "Basic ";
    private static final NotAuthorizedException notAuthorizedException = new NotAuthorizedException("Cannot access the resource!");
    private static final String USERNAME = "durimkryeziu";
    private static final String PASSWORD = "password";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String auth = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (auth == null || auth.isEmpty() || !auth.startsWith("Basic")) {
            throw notAuthorizedException;
        }

        auth = auth.replace(AUTHORIZATION_TYPE, "");

        String[] decodedUsernameAndPassword = decode(auth);

        if (decodedUsernameAndPassword.length < 2) {
            throw notAuthorizedException;
        }

        String username = decodedUsernameAndPassword[0];
        String password = decodedUsernameAndPassword[1];

        if (!checkUsernameAndPassword(username, password)) {
            throw notAuthorizedException;
        }
    }

    private String[] decode(String auth) throws UnsupportedEncodingException {

        byte[] decodedBytes = Base64.getDecoder().decode(auth);

        String decodedString = new String(decodedBytes, "UTF-8");

        return decodedString.split("[:]", 2);
    }

    private boolean checkUsernameAndPassword(String username, String password) {

        // Here we use fixed username and password. You should do a proper validation
        return username.equals(USERNAME) && password.equals(PASSWORD);
    }
}