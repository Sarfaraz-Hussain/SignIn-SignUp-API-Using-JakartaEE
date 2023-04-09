package com.codingnuts.app.ws.service;

import com.codingnuts.app.ws.shared.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUser(String id);

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getUsers(int start, int limit);

    void updateUserDetails(UserDTO storedUserDetails);

    void deleteUser(UserDTO storedUserDetails);
    boolean verifyEmail(String token);
}
