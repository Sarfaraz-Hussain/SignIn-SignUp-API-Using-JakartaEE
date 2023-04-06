package com.codingnuts.app.ws.service.impl;

import com.codingnuts.app.ws.exception.CouldNotCreateRecordException;
import com.codingnuts.app.ws.exception.NoRecordFoundException;
import com.codingnuts.app.ws.io.dao.DAO;
import com.codingnuts.app.ws.io.dao.impl.MYSQLDAO;
import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import com.codingnuts.app.ws.utils.UserProfileUtils;

public class UserServiceImpl implements UserService {
    UserProfileUtils userProfileUtils = new UserProfileUtils();
    DAO database;

    public UserServiceImpl() {
        this.database = new MYSQLDAO();
    }

    public UserDTO createUser(UserDTO user) {
        UserDTO returnValue = null;
        // Validate the required fields
        userProfileUtils.validateRequiredFields(user);
        // Check if user already exist
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }
        // Generate secure public user id
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);
        // Generate Salt
        String salt = userProfileUtils.getSalt(30);

        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);
        // Persist the data into database
        returnValue = this.saveUser(user);

        // Return back to the user profile
        return returnValue;
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }


    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {    // using user email as userName
        UserDTO userDTO = null;
        if (userName == null || userName.isEmpty()) {
            return null;
        }
        // Connect to database
        try {
            this.database.openConnection();
            userDTO = this.database.getUserByUserName(userName);
        } finally {
            this.database.closeConnection();
        }
        return userDTO;
    }
}
