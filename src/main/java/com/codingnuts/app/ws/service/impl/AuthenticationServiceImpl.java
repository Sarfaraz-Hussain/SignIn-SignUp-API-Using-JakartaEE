package com.codingnuts.app.ws.service.impl;

import com.codingnuts.app.ws.exception.AuthenticationException;
import com.codingnuts.app.ws.exception.EmailVerificationException;
import com.codingnuts.app.ws.io.dao.DAO;
import com.codingnuts.app.ws.io.dao.impl.MYSQLDAO;
import com.codingnuts.app.ws.service.AuthenticationService;
import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;
import com.codingnuts.app.ws.utils.UserProfileUtils;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationServiceImpl implements AuthenticationService {
    DAO database;

    @Override
    public UserDTO authenticate(String userName, String password) {
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUserByUserName(userName);
        if (storedUser == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }

        if(!storedUser.getEmailVerificationStatus()) {
            throw new EmailVerificationException(ErrorMessages.EMAIL_ADDRESS_NOT_VERIFIED.getErrorMessage());
        }

        String encryptedPassword = null;
        encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());
        boolean authenticated = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }
        if (!authenticated) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        String returnValue = null;
        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;
        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (Exception ex) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Failed to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        updateUserProfile(userProfile);
        return returnValue;
    }

    @Override
    public void resetSecurityCredentials(String userPassword, UserDTO authenticatedUser) {
        // Gerenerate a new salt
        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);

        // Generate a new password
        String securePassword = userUtils.generateSecurePassword(userPassword, salt);
        authenticatedUser.setSalt(salt);
        authenticatedUser.setEncryptedPassword(securePassword);

        // Update user profile
        updateUserProfile(authenticatedUser);
    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MYSQLDAO();
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            this.database.closeConnection();
        }
    }

}
