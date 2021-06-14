package app.core.controllers;

import app.core.security.CredentialsDetails;
import app.core.security.JwtGenerate;
import app.core.security.UserDetails;
import app.core.security.UserType;
import app.core.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

    @Autowired
    private AdminService service;
    @Autowired
    private JwtGenerate jwtUtil;

    @Override
    @PostMapping("/login")
    public UserDetails login(@RequestBody CredentialsDetails details) {
        if (service.login(details.email, details.password)) {
            UserDetails user = new UserDetails("0", details.email, details.password, UserType.ADMIN);
            String token = jwtUtil.generateToken(user);
            user.setToken(token);
            return user;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, check your login details.");
    }



}
