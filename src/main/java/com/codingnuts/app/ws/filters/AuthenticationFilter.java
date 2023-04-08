package com.codingnuts.app.ws.filters;

import com.codingnuts.app.ws.annotations.Secured;
import com.codingnuts.app.ws.exception.AuthenticationException;
import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.service.impl.UserServiceImpl;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.utils.UserProfileUtils;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Extract Authorization header details
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new AuthenticationException("Authorization header must be provided");
        }

        // Extract the token
        String token = authorizationHeader.substring("Bearer".length()).trim();

        // Extract user id
        String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");

        validateToken(token, userId);

    }

    private void validateToken(String token, String userId) throws AuthenticationException {

        // Get user profile details
        UserService usersService = new UserServiceImpl();
        UserDTO userProfile = usersService.getUser(userId);

        // Accessible Access token using two parts. One from DB and one from http request.
        String completeToken = userProfile.getToken() + token;
        System.out.println(userProfile.getToken());
        System.out.println(completeToken);
        // Create Access token material out of the useId received and salt kept database
        String securePassword = userProfile.getEncryptedPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;
        byte[] encryptedAccessToken = null;

        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(securePassword, accessTokenMaterial);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Failed to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

//        System.out.println(encryptedAccessTokenBase64Encoded + " -- " + completeToken);
        // Compare two access tokens
        if (!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
            throw new AuthenticationException("Authorization token did not match");
        }
    }
}
