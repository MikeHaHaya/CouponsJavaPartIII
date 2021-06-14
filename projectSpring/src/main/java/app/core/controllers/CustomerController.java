package app.core.controllers;

import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.security.UserDetails;
import app.core.security.UserType;
import app.core.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController extends ClientController {

    @Autowired
    CustomerService service;

    /**
     * Logs in a customer type user and returns a jwt token for future validation.
     */
    @Override
    @PostMapping("/login")
    public String login(@RequestBody String email, @RequestBody String password) {

        if (service.login(email, password)) {
            String id = String.valueOf(service.getCustomerId(email, password));
            UserDetails user = new UserDetails(id, email, password, UserType.CUSTOMER);
            return jwtUtil.generateToken(user);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, check your login details. ");
    }

    /**
     * Purchases a coupon from the database with jwt validation.
     */
    @PutMapping("/purchase-coupon")
    public void purchaseCoupon(@RequestParam String jwt, @RequestBody int couponId) {

        try {
            int customerId = getIdFromJwt(jwt);
            service.purchaseCoupon(couponId, customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons purchased by this customer from the database with jwt validation.
     */
    @GetMapping("/get-coupons")
    public List<Coupon> getCoupons(@RequestParam String jwt) {

        try {
            int customerId = getIdFromJwt(jwt);
            return service.getCoupons(customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons purchased by this customer from the database with a category filter and jwt validation.
     */
    @GetMapping("/get-coupons-by-category")
    public List<Coupon> getCouponsByCategory(@RequestParam String jwt, @RequestBody Coupon.Category category) {

        try {
            int customerId = getIdFromJwt(jwt);
            return service.getCouponsByCategory(category, customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons purchased by this customer from the database with a category filter and jwt validation.
     */
    @GetMapping("/get-coupons-by-price")
    public List<Coupon> getCouponsByMaxPrice(@RequestParam String jwt, @RequestBody double maxPrice) {

        try {
            int customerId = getIdFromJwt(jwt);
            return service.getCouponsByMaxPrice(maxPrice, customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all details about this customer from the database.
     */
    @GetMapping("/details")
    public String getCustomerDetails(@RequestParam String jwt) {

        try {
            int customerId = getIdFromJwt(jwt);
            return service.getCustomerDetails(customerId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
