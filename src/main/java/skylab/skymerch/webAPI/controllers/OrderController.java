package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestOrderDto;
import skylab.skymerch.entities.Order;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody RequestOrderDto requestOrderDto){
        return orderService.addOrder(requestOrderDto);
    }

    @PostMapping("/deleteOrder")
    public Result deleteOrder(@RequestBody int orderId){
        return orderService.deleteOrder(orderId);
    }

    @PostMapping("/updateOrder")
    public Result updateOrder(@RequestBody RequestOrderDto requestOrderDto){
        return orderService.updateOrder(requestOrderDto);
    }

    @GetMapping("/getOrderById")
    public DataResult<Order> getOrderById(@RequestParam int orderId){
        return orderService.getById(orderId);
    }

    @GetMapping("/getOrders")
    public DataResult<List<Order>> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping("/getOrdersByStatus")
    public DataResult<List<Order>> getOrdersByStatus(@RequestParam String status){
        return orderService.getOrdersByStatus(status);
    }

    @GetMapping("/getOrdersByUserId")
    public DataResult<List<Order>> getOrdersByUserId(@RequestParam int userId){
        return orderService.getByUserId(userId);
    }




}
