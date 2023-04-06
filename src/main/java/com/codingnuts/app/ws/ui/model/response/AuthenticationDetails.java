package com.codingnuts.app.ws.ui.model.response;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticationDetails {
    private String id;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
