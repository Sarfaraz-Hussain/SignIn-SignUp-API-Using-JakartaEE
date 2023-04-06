package com.codingnuts.app.ws.service;

import com.codingnuts.app.ws.shared.dto.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUser(String id);
    UserDTO getUserByUserName(String userName);
}
