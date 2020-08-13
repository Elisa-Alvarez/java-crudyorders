package localhost.ealambdaschool.orders.repo;

import localhost.ealambdaschool.orders.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepositories extends CrudRepository<Order,Long>
{
    List<Order> findAllByAdvanceamountGreaterThan(double advanceamount);

}
