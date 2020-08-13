package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Order;

import java.util.List;

public interface OrderServices {

        Order findOrderById(long id);

        List<Order> findAdvanceAmount(double advanceamount);;

        Order save(Order order);

        Order update(Order order, long ordernum);

        void delete(long id);





}
