package app.core.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

    @Override
    public boolean login(String email, String password) {
        return false;
    }

}
