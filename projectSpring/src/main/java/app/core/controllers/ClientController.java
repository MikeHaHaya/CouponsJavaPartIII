package app.core.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

public abstract class ClientController {

    public abstract boolean login(String email, String password);

}
