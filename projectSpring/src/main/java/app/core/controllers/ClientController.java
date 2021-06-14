package app.core.controllers;

import app.core.security.CredentialsDetails;
import app.core.security.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class ClientController {

    public abstract UserDetails login(CredentialsDetails details);

}
