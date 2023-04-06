package com.codingnuts.app.ws.service;

import com.codingnuts.app.ws.exception.AuthenticationException;
import com.codingnuts.app.ws.shared.dto.UserDTO;

public interface AuthenticationService {
    UserDTO authenticate(String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
}
