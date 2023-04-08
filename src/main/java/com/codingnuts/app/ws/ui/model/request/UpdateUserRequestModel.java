package com.codingnuts.app.ws.ui.model.request;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateUserRequestModel {
    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
