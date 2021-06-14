package app.core.controllers;

import app.core.security.CredentialsDetails;
import app.core.security.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController extends ClientController {


    @Override
    @PostMapping("/login")
    public UserDetails login(@RequestBody CredentialsDetails details) {
        return null;
    }

}
