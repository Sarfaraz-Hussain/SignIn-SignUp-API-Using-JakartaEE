package com.codingnuts.app.ws.service.impl;

import com.codingnuts.app.ws.exception.*;
import com.codingnuts.app.ws.io.dao.DAO;
import com.codingnuts.app.ws.io.dao.impl.MYSQLDAO;
import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import com.codingnuts.app.ws.utils.AmazonSES;
import com.codingnuts.app.ws.utils.UserProfileUtils;

import java.util.List;

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
        user.setEmailVerificationStatus(false);
        user.setEmailVerificationToken(userProfileUtils.generateEmailVerificationToken(30));
        // Persist the data into database
        returnValue = this.saveUser(user);
        new AmazonSES().verifyEmail(user);
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
    public UserDTO getUserByUserName(String userName) { // using user email as userName
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

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> users = null;
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }
        return users;
    }

    @Override
    public void updateUserDetails(UserDTO storedUserDetails) {
        try {
            this.database.openConnection();
            this.database.updateUser(storedUserDetails);
        } catch (Exception ex) {
            throw new CouldNotUpdateRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser(UserDTO storedUserDetails) {
        try {
            this.database.openConnection();
            this.database.deleteUser(storedUserDetails);
        } catch (Exception ex) {
            throw new CouldNotDeleteRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
        // verify that user is deleted

        try {
            storedUserDetails = getUser(storedUserDetails.getUserId());
        } catch (NoRecordFoundException ex) {
            storedUserDetails = null;
        }
        if (storedUserDetails != null) {
            throw new CouldNotDeleteRecordException(
                    ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
    }

    @Override
    public boolean verifyEmail(String token) {

        if (token == null || token.isEmpty()) {
            throw new EmailVerificationException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        try {

            UserDTO storedUserRecord = getUserByEmailToken(token);

            if (storedUserRecord == null) {
                return false;
            }

            // Update user Record
            storedUserRecord.setEmailVerificationStatus(true);
            storedUserRecord.setEmailVerificationToken(null);

            updateUserDetails(storedUserRecord);


        } catch (Exception ex) {
            throw new EmailVerificationException(ex.getMessage());
        }

        return true;
    }
    private UserDTO getUserByEmailToken(String token) {
        UserDTO returnValue;
        try {
            this.database.openConnection();
            returnValue = this.database.getUserByEmailToken(token);

        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }
}
