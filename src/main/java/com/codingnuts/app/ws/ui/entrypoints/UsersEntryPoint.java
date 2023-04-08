package com.codingnuts.app.ws.ui.entrypoints;


import com.codingnuts.app.ws.annotations.Secured;
import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.service.impl.UserServiceImpl;
import com.codingnuts.app.ws.shared.dto.UserDTO;
import com.codingnuts.app.ws.ui.model.request.CreateUserRequestModel;
import com.codingnuts.app.ws.ui.model.request.UpdateUserRequestModel;
import com.codingnuts.app.ws.ui.model.response.UserProfileRest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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

    // We are making this web service as protected and need Access Token to access this
    @Secured
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

    @GET
    @Path("list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfileRest> getUsers(@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("50") @QueryParam("limit") int limit) {
        UserService userService = new UserServiceImpl();
        List<UserDTO> users = userService.getUsers(start, limit);
        // prepare a return value
        List<UserProfileRest> returnValue = new ArrayList<>();
        for(UserDTO user : users) {
            UserProfileRest userModel = new UserProfileRest();
            BeanUtils.copyProperties(user, userModel);
            userModel.setHref("/users/user/" + user.getUserId());
            returnValue.add(userModel);
        }
        return returnValue;
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest updateUserDetails(@PathParam("id") String id, UpdateUserRequestModel userDetails) {
        UserService userService = new UserServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);
        if(storedUserDetails.getFirstName() != null && !storedUserDetails.getFirstName().isEmpty()) {
            storedUserDetails.setFirstName(userDetails.getFirstName());
        }
        storedUserDetails.setLastName(userDetails.getLastName());
        userService.updateUserDetails(storedUserDetails);
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        return returnValue;
    }
}