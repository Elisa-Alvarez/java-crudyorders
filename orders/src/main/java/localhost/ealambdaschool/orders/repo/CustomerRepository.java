package localhost.ealambdaschool.orders.repo;


import localhost.ealambdaschool.orders.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
    Customer findByCustname(String name);

    List<Customer> findByCustnameContainingIgnoringCase(String likename);
    ;
}
