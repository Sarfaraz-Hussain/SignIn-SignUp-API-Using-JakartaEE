package com.codingnuts.app.ws.ui.entrypoints;

import com.codingnuts.app.ws.service.AuthenticationService;
import com.codingnuts.app.ws.service.impl.AuthenticationServiceImpl;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.request.LoginCredentials;
import com.codingnuts.app.ws.ui.model.response.AuthenticationDetails;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("authentication")
public class AuthenticationEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials) {
        AuthenticationDetails returnValue = new AuthenticationDetails();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getUserPassword());
        // Reset Access Token
        authenticationService.resetSecurityCredentials(loginCredentials.getUserPassword(), authenticatedUser);
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);
        returnValue.setId(authenticatedUser.getUserId());
        returnValue.setToken(accessToken);
        return returnValue;
    }
}
