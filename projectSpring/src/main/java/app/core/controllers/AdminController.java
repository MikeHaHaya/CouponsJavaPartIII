package app.core.controllers;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.security.UserDetails;
import app.core.security.UserType;
import app.core.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController extends ClientController {

    @Autowired
    private AdminService service;

    /**
     * Logs in an admin type user and returns a jwt token for future validation.
     */
    @Override
    @PostMapping("/login")
    public String login(@RequestBody String email, @RequestBody String password) {

        if (service.login(email, password)) {
            UserDetails user = new UserDetails("0", email, password, UserType.ADMIN);
            return jwtUtil.generateToken(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, check your login details.");
    }

    /**
     * Adds a company to the database with jwt validation.
     */
    @PostMapping("/add-company")
    public void addCompany(@RequestParam String jwt, @RequestBody Company company) {

        try {
            jwtValidation(jwt);
            service.addNewCompany(company);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Updates a company from the database with jwt validation.
     */
    @PutMapping("/update-company")
    public void updateCompany(@RequestParam String jwt, @RequestBody Company company) {

        try {
            jwtValidation(jwt);
            service.updateCompany(company.getId(), company);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Deletes a company from the database with jwt validation.
     */
    @DeleteMapping("/delete-company")
    public void deleteCompany(@RequestParam String jwt, @RequestBody int companyId) {

        try {
            jwtValidation(jwt);
            service.deleteCompany(companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all companies from the database with jwt validation.
     */
    @GetMapping("get-companies")
    public List<Company> getAllCompanies(@RequestParam String jwt) {

        try {
            jwtValidation(jwt);
            return service.getAllCompanies();

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns a company from the database with jwt validation.
     */
    @GetMapping("/get-company")
    public Company getOneCompany(@RequestParam String jwt, @RequestBody int companyId) {

        try {
            jwtValidation(jwt);
            return service.getOneCompany(companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Adds a customer to the database with jwt validation.
     */
    @PostMapping("/add-customer")
    public void addCustomer(@RequestParam String jwt, @RequestBody Customer customer) {

        try {
            jwtValidation(jwt);
            service.addNewCustomer(customer);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Updates a customer from the database with jwt validation.
     */
    @PutMapping("/update-customer")
    public void updateCustomer(@RequestParam String jwt, @RequestBody Customer customer) {

        try {
            jwtValidation(jwt);
            service.updateCustomer(customer.getId(), customer);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Deletes a customer from the database with jwt validation.
     */
    @DeleteMapping("/delete-customer")
    public void deleteCustomer(@RequestParam String jwt, @RequestBody int customerId) {

        try {
            jwtValidation(jwt);
            service.deleteCustomer(customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all customers from the database with jwt validation.
     */
    @GetMapping("/get-customers")
    public List<Customer> getAllCustomers(@RequestParam String jwt) {

        try {
            jwtValidation(jwt);
            return service.getAllCustomers();

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Gets a customer from the database with jwt validation.
     */
    @GetMapping("/get-customer")
    public Customer getOneCustomer(@RequestParam String jwt, @RequestBody int customerId) {

        try {
            jwtValidation(jwt);
            return service.getOneCustomer(customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }



}
