package com.codingnuts.app.ws;

import com.codingnuts.app.ws.service.UserService;
import com.codingnuts.app.ws.service.impl.UserServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class MyApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserServiceImpl.class).to(UserService.class);
    }
}
