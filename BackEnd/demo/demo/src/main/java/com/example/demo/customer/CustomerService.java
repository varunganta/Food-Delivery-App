package com.example.demo.customer;

import com.example.demo.appuser.AppUser;
import com.example.demo.appuser.AppUserRole;
import com.example.demo.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final AppUserService appUserService;

    @Autowired
    public CustomerService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    public List<AppUser> getCustomers() {
        return appUserService.getAllAppUsers()
                .stream()
                .filter(user -> user.getAppUserRole() == AppUserRole.CUSTOMER)
                .collect(Collectors.toList());
    }

    public AppUser getCustomerById(Long id) {
        AppUser customer = appUserService.getAppUserById(id);
        if (customer != null && customer.getAppUserRole() == AppUserRole.CUSTOMER) {
            return customer;
        } else {
            return null;
        }
    }

//    public AppUser createCustomer(AppUser customer) {
//        // Make sure the role is set to 'CUSTOMER'
//        customer.setUserRole(AppUserRole.CUSTOMER);
//        return appUserService.signUpUser(customer);
//    }

    public AppUser updateCustomer(Long id, AppUser updatedCustomer) {
        AppUser existingCustomer = getCustomerById(id);
        if (existingCustomer == null) {
            return null;
        }

        existingCustomer.setFirstName(updatedCustomer.getFirstName());
        existingCustomer.setLastName(updatedCustomer.getLastName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPassword(updatedCustomer.getPassword());

        return appUserService.updateAppUser(existingCustomer);
    }

    public boolean deleteCustomer(Long id) {
        AppUser existingCustomer = getCustomerById(id);
        if (existingCustomer == null) {
            return false;
        }
        appUserService.deleteAppUser(existingCustomer);
        return true;
    }
}
