package com.codingnuts.app.ws.ui.entrypoints;


import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.service.impl.UserServiceImpl;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.request.CreateUserRequestModel;
import com.codingnuts.app.ws.ui.model.response.UserProfileRest;
import jakarta.ws.rs.*;
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

    @GET
    @Path("user/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest getUserProfile(@PathParam("id") String id) {
        UserProfileRest returnValue = null;
        UserService userService = new UserServiceImpl();
        UserDTO userProfile = userService.getUser(id);
        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);
        return returnValue;
    }
}
