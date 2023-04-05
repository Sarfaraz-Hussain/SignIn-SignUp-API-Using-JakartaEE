package com.codingnuts.app.ws.utils;

import com.codingnuts.app.ws.exception.MissingRequiredFieldException;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.response.ErrorMessages;

public class UserProfileUtils {
    public void validateRequiredFields(UserDTO userDTO) throws MissingRequiredFieldException {
        if(userDTO.getFirstName() == null ||
        userDTO.getFirstName().isEmpty() ||
        userDTO.getLastName() == null ||
        userDTO.getLastName().isEmpty() ||
        userDTO.getEmail() == null ||
        userDTO.getEmail().isEmpty() ||
        userDTO.getPassword() == null ||
        userDTO.getPassword().isEmpty()) {
        throw new MissingRequiredFieldException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }
}
