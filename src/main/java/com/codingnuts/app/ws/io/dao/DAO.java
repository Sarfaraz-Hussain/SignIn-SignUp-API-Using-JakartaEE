package com.codingnuts.app.ws.io.dao;

import com.codingnuts.app.ws.shared.dto.UserDTO;

public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    void closeConnection();

    UserDTO saveUser(UserDTO user);
}
