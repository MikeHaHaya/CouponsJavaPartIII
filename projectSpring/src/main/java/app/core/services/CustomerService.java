package app.core.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import app.core.exceptions.ServiceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Coupon;
import app.core.entities.Customer;

@Service("customerService")
@Transactional
@Scope("prototype")
public class CustomerService extends ClientService {

    /**
     * An empty constructor.
     */
    public CustomerService() {
    }

    /**
     * Logs in a customer user.
     */
    @Override
    public boolean login(String email, String password) {

        return custRep.existsCustomerByEmailAndPassword(email, password);
    }

    /**
     * Adds a new purchase to the database
     */
    public void purchaseCoupon(int couponId, int customerId) throws ServiceException {

        Optional<Coupon> optCoupon = couRep.findById(couponId);

        if (optCoupon.isEmpty())
            throw new ServiceException("A coupon with this id does not exist. ");
        Coupon coupon = optCoupon.get();
        List<Integer> couponsId = couRep.findAllByCustomers(customerId);

        if (coupon.getAmount() < 1) {
            throw new ServiceException("The coupon you are trying to buy has run out of stock. ");
        }

        if (coupon.getEndDate().isBefore(LocalDateTime.now())) {
            throw new ServiceException("The coupon you are trying to buy has expired. ");
        }

        if (couponsId.contains(couponId)) {
            throw new ServiceException("You already owns this coupon. ");
        }

        Customer customer = getCustomer(customerId);

        coupon.setAmount(coupon.getAmount() - 1);
        customer.addCoupon(coupon);

        custRep.flush();
    }

    // TODO -- Check if you can get the coupons using only repositories

    /**
     * @return the coupons of the customer. will return empty list in case of no coupons.
     */
    public ArrayList<Coupon> getCoupons(int customerId) {

        ArrayList<Integer> couponsID = new ArrayList<>(couRep.findAllByCustomers(customerId));
        ArrayList<Coupon> coupons = new ArrayList<>();

        for (Integer couponId : couponsID) {

            Optional<Coupon> opt = couRep.findById(couponId);
            if (opt.isPresent()) {
                Coupon coupon = opt.get();
                coupons.add(coupon);
            }
        }
        return coupons;
    }

    /**
     * @return the coupons of the customer that match the given category. will return empty list in case of no coupons.
     */
    public ArrayList<Coupon> getCouponsByCategory(Coupon.Category category, int customerId) {

        ArrayList<Coupon> coupons = getCoupons(customerId);
        coupons.removeIf(coupon -> coupon.getCategory() != category);

        return coupons;
    }

    /**
     * @return the coupons of the customer that under maxPrice. will return empty list in case of no coupons.
     */
    public ArrayList<Coupon> getCouponsByMaxPrice(double maxPrice, int customerId) {

        ArrayList<Coupon> coupons = getCoupons(customerId);
        coupons.removeIf(coupon -> coupon.getPrice() <= maxPrice);
        return coupons;
    }

    /**
     * @return all details about this customer from the database.
     */
    public String getCustomerDetails(int customerId) throws ServiceException {

        Customer customer = getCustomer(customerId);

        String details = "ID: " + customerId + "\n"
                + "Full Name: " + customer.getFirstName() + " " + customer.getLastName() + "\n"
                + "Email: " + customer.getEmail() + "\n"
                + "Coupons purchased: ";

        ArrayList<Coupon> coupons = getCoupons(customerId);
        details += coupons.size();

        return details;
    }

    /**
     * @return the customer's id from the database.
     */
    public int getCustomerId(String email, String password) {
        return custRep.findCustomerByEmailAndPassword(email, password).getId();
    }

    /**
     * @return the customer as an object using it's id.
     */
    private Customer getCustomer(int customerId) throws ServiceException {

        Optional<Customer> opt = custRep.findById(customerId);
        if (opt.isEmpty())
            throw new ServiceException("A customer with this id does not exist. ");
        return opt.get();
    }

}
