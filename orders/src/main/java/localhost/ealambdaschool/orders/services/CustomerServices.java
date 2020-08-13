package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CustomerServices {

        List<Customer> findAllCustomerOrders();

        Customer findByCustomerId(long custcode);

        List<Customer> findAllCustomersByNameLike(String custname);

        //Update and Delete
        @Transactional
        Customer save(Customer customer);

        Customer update(Customer customer, long custcode);

        void delete(long custcode);




}