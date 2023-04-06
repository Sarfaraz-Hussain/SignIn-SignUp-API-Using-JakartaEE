package com.codingnuts.app.ws.ui.entrypoint;


import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.service.impl.UserServiceImpl;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.request.CreateUserRequestModel;
import com.codingnuts.app.ws.ui.model.response.UserProfileRest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

@Path("users")
public class UsersEntryPoint {

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest createUser(CreateUserRequestModel requestObject) {
        UserProfileRest returnValue = new UserProfileRest();
        // Prepare UserDTO
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDTO);
        // Create mew user
        UserService userService = new UserServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDTO);

        // Prepare response
        BeanUtils.copyProperties(createdUserProfile, returnValue);

        return returnValue;
    }




}
