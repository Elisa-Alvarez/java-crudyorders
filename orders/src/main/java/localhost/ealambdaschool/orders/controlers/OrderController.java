package localhost.ealambdaschool.orders.controlers;

import localhost.ealambdaschool.orders.models.Order;
import localhost.ealambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderServices orderServices;

    // http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum)
    {
        Order myOrder = orderServices.findOrderById(ordnum);
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    // http://localhost:2019/orders/advanceamount
    @GetMapping(value = "/advanceamount", produces = "application/json")
    public ResponseEntity<?> getAdvanceAmount()
    {
        List<Order> myList = orderServices.findAdvanceAmount(0);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    // POST http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder)
    {
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderid}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    // PUT http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder,
                                             @PathVariable long ordnum)
    {
        updateOrder.setOrdnum(ordnum);
        orderServices.save(updateOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // DELETE http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderId(@PathVariable long ordnum)
    {
        orderServices.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}