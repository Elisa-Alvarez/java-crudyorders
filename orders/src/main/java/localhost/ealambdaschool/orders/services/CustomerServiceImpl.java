package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Customer;
import localhost.ealambdaschool.orders.models.Order;
import localhost.ealambdaschool.orders.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;



@Transactional
@Service(value = "customerServices")
public class CustomerServiceImpl implements CustomerServices
{
    //Connect to Repo
    @Autowired
    CustomerRepository custrepos;

    //Get Request
    @Override
    public List<Customer> findAllCustomerOrders() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }


    @Override
    public Customer findByCustomerId(long custcode) {
        return custrepos.findById(custcode)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + custcode + " Not Found!"));

    }

    @Override
    public List<Customer> findAllCustomersByNameLike(String custname)
    {
        return custrepos.findByCustnameContainingIgnoringCase(custname);
    }


    //Update and Delete
    @Transactional
    @Override
    public Customer save(Customer customer)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0)
        {
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " Not Found"));

            newCustomer.setCustcode(customer.getCustcode());

        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        // one to many, orders
        newCustomer.getOrders().clear();
        for(Order o : customer.getOrders())
        {
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(newCustomer);
            newOrder.setOrderdescription(o.getOrderdescription());
            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long custcode)
    {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0){
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(()-> new EntityNotFoundException("Customer" + customer.getCustcode() + "Not Found!"));

        }



        if (customer.getCustname() != null){
            newCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null){
            newCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null){
            newCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null){
            newCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null){
            newCustomer.setGrade(customer.getGrade());
        }

        if (customer.getOpeningamt() > 0){
            newCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.getReceiveamt() > 0){
            newCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.getPaymentamt() > 0){
            newCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.getOutstandingamt() > 0){
            newCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getPhone() != null){
            newCustomer.setPhone(customer.getPhone());
        }

        if (customer.getAgent() != null){
            newCustomer.setAgent(customer.getAgent());
        }

        // one to many, orders
        if (customer.getOrders().size() > 0)
        {
            newCustomer.getOrders()
                    .clear();

            for (Order o : customer.getOrders())
            {
                Order newOrder = new Order();
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setCustomer(newCustomer);
                newOrder.setOrderdescription(o.getOrderdescription());

                newCustomer.getOrders()
                        .add(newOrder);
            }
        }

        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public void delete(long custcode)
    {
        if (custrepos.findById(custcode)
                .isPresent())
        {
            custrepos.deleteById(custcode);
        } else {
            throw new EntityNotFoundException("Customer " + custcode + " Not Found");
        }

    }
}