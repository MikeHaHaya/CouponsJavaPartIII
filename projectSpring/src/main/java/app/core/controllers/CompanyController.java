package app.core.controllers;

import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.security.UserDetails;
import app.core.security.UserType;
import app.core.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController extends ClientController {

    @Autowired
    CompanyService service;

    /**
     * Logs in a company type user and returns a jwt token for future validation.
     */
    @Override
    @PostMapping("/login")
    public String login(@RequestBody String email, @RequestBody String password) {

        if (service.login(email, password)) {
            String id = String.valueOf(service.getCompanyId(email, password));
            UserDetails user = new UserDetails(id, email, password, UserType.COMPANY);
            return jwtUtil.generateToken(user);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong, check your login details. ");
    }

    /**
     * Adds a coupon to the database with jwt validation.
     */
    @PostMapping("/add")
    public void addCoupon(@RequestParam String jwt, @RequestBody Coupon coupon) {

        try {
            int companyId = getIdFromJwt(jwt);
            service.addNewCoupon(coupon, companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Updates a coupon from the database with jwt validation.
     */
    @PutMapping("/update")
    public void updateCoupon(@RequestParam String jwt, @RequestBody Coupon coupon) {

        try {
            int companyId = getIdFromJwt(jwt);
            service.updateCoupon(coupon, companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Deletes a coupon from the database with jwt validation.
     */
    @DeleteMapping("/delete")
    public void deleteCoupon(@RequestParam String jwt, @RequestBody int couponId) {

        try {
            int companyId = getIdFromJwt(jwt);
            service.deleteCoupon(couponId, companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons by this company from the database with jwt validation.
     */
    @GetMapping("/get-coupons")
    public List<Coupon> getCompanyCoupons(@RequestParam String jwt) {

        try {
            int companyId = getIdFromJwt(jwt);
            return service.getCoupons(companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons by this company from the database with a category filter and jwt validation.
     */
    @GetMapping("/get-coupons-by-category")
    public List<Coupon> getCouponsByCategory(@RequestParam String jwt, @RequestBody Coupon.Category category) {

        try {
            int companyId = getIdFromJwt(jwt);
            return service.getCouponsByCategory(category, companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all coupons by this company from the database with a price filter and jwt validation.
     */
    @GetMapping("/get-coupons-by-price")
    public List<Coupon> getCouponsByMaxPrice(@RequestParam String jwt, @RequestBody double maxPrice) {

        try {
            int companyId = getIdFromJwt(jwt);
            return service.getCouponsByMaxPrice(maxPrice, companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Returns all details about this company from the database.
     */
    @GetMapping("/details")
    public String getCompanyDetails(@RequestParam String jwt) {

        try {
            int companyId = getIdFromJwt(jwt);
            return this.service.getCompanyDetails(companyId);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
