package com.codingnuts.app.ws.io.dao;

import com.codingnuts.app.ws.shared.dto.UserDTO;

import java.util.List;

public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    void closeConnection();

    UserDTO saveUser(UserDTO user);
    UserDTO getUser(String id);

    void updateUser(UserDTO userProfile);

    List<UserDTO> getUsers(int start, int limit);

    void deleteUser(UserDTO storedUserDetails);
}
