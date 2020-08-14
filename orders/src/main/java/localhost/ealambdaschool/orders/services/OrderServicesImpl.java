package localhost.ealambdaschool.orders.services;

import localhost.ealambdaschool.orders.models.Order;
import localhost.ealambdaschool.orders.models.Payment;
import localhost.ealambdaschool.orders.repo.CustomerRepository;
import localhost.ealambdaschool.orders.repo.OrderRepositories;
import localhost.ealambdaschool.orders.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service(value = "orderServices")
public class OrderServicesImpl implements OrderServices
{
    @Autowired
    OrderRepositories orderrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Autowired
    CustomerRepository custrepos;

    @Override
    public Order findOrderById(long id)
    {
        return orderrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));
    }

    @Override
    public List<Order> findAdvanceAmount(double advanceamount)
    {
        List<Order> list = orderrepos.findAllByAdvanceamountGreaterThan(advanceamount);
        return list;
    }

    @Transactional
    @Override
    public Order save(Order order)
    {
        Order newOrder = new Order();

        if (order.getOrdnum() != 0)
        {
            orderrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " Not Found"));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setCustomer(order.getCustomer());


        newOrder.getPayments()
                .clear();
        for (Payment p : order.getPayments())
        {
            Payment newPay = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));

            //newOrder.getPayments().add(newPay);
            newOrder.addPayments(newPay);
        }

        return orderrepos.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        if (orderrepos.findById(id)
                .isPresent())
        {
            orderrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException("Order " + id + " Not Found");
        }

    }

    @Transactional
    @Override
    public Order update(Order order, long ordernum)
    {
        Order updateOrder = orderrepos.findById(ordernum)
                .orElseThrow(() -> new EntityNotFoundException("Order " + ordernum + " Not Found"));

        if (order.getOrdamount() > 0)
        {
            updateOrder.setOrdamount(order.getOrdamount());
        }

        if (order.getAdvanceamount() > 0)
        {
            updateOrder.setAdvanceamount(order.getAdvanceamount());
        }

        if (order.getOrderdescription() != null)
        {
            updateOrder.setOrderdescription(order.getOrderdescription());
        }

        if (order.getCustomer() != null)
        {
            updateOrder.setCustomer(order.getCustomer());
        }

        if (order.getPayments()
                .size() > 0)
        {
            updateOrder.getPayments()
                    .clear();
            for (Payment p : order.getPayments())
            {
                Payment newPay = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));

                updateOrder.addPayments(newPay);

            }
        }

        return orderrepos.save(updateOrder);

    }

}