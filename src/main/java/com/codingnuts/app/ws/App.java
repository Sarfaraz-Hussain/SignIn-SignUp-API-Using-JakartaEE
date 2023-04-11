package com.codingnuts.app.ws;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class App extends ResourceConfig {

    public App() {
        packages("com.codingnuts.app.ws");
        register(new MyApplicationBinder());
    }

}
