package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.business.abstracts.PaymentService;
import skylab.skymerch.business.constants.OrderMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.OrderDao;
import skylab.skymerch.dataAccess.PaymentDao;
import skylab.skymerch.entities.Dtos.RequestOrderDto;
import skylab.skymerch.entities.Order;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class OrderManager implements OrderService {


    @Autowired
    private OrderDao orderDao;

    private PaymentService paymentService;

    public OrderManager(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.paymentService = paymentService;
    }

    @Override
    public Result addOrder(Order order) {
        if(order.getUser() == null || order.getProducts() == null || order.getOrderNumber() == null || order.getTotalPrice() == 0)
            return new ErrorResult(OrderMessages.OrderCannotBeNull);

        order.setCreatedAt(new Date());
        order.setOrderNumber(generateOrderNumber());
        orderDao.save(order);
        return new SuccessResult(OrderMessages.orderAdded);
    }

    @Override
    public Result deleteOrder(int orderId) {
        var result = getById(orderId);
        if(!result.isSuccess())
            return new ErrorResult(OrderMessages.orderCannotBeFound);

        var order = result.getData();
        orderDao.delete(order);
        return new SuccessResult(OrderMessages.orderDeleted);
    }

    @Override
    public Result updateOrder(RequestOrderDto order) {
        var result = getById(order.getId());
        if(!result.isSuccess()) {
            return new ErrorResult(OrderMessages.orderCannotBeFound);
        }

        var orderToUpdate = result.getData();
        orderToUpdate.setAddress(order.getAddress());
        orderToUpdate.setStatus(order.getStatus());
        orderToUpdate.setTotalPrice(order.getTotalPrice());


        orderDao.save(orderToUpdate);
        return new SuccessResult(OrderMessages.orderUpdated);
    }

 //  @Override
 //  public Result changeOrderStatus(int orderId, String status) {
 //      var result = getById(orderId);
 //      if(!result.isSuccess()) {
 //          return new ErrorResult(OrderMessages.orderCannotBeFound);
 //      }
 //
 //      var order = result.getData();
 //      order.setStatus(status);
 //      orderDao.save(order);
 //      return new SuccessResult(OrderMessages.orderStatusChanged);
 //  }

 //   @Override
 //   public Result changeOrderAddress(int orderId, int addressId) {
 //       return null;
 //   }

    @Override
    public DataResult<Order> getById(int orderId) {
        var result = orderDao.findById(orderId);
        if(result == null){
            return new ErrorDataResult(OrderMessages.orderCannotBeFound);
        }

        return new SuccessDataResult<>(result, OrderMessages.getOrderByIdSuccess);
    }

    @Override
    public DataResult<List<Order>> getByUserId(int userId) {
        var result = orderDao.findAllByUserId(userId);
        if(result == null){
            return new ErrorDataResult<>(OrderMessages.orderCannotBeFound);
        }

        return new SuccessDataResult<>(result, OrderMessages.getOrdersByUserIdSuccess);
    }

    @Override
    public DataResult<List<Order>> getOrders() {
        var result = orderDao.findAll();
        if(result.isEmpty()){
            return new ErrorDataResult<>(OrderMessages.getOrdersEmpty);
        }

        return new SuccessDataResult<>(result, OrderMessages.getOrdersSuccess);
    }

    @Override
    public DataResult<List<Order>> getOrdersByStatus(String status) {
        var result = orderDao.findAllByStatus(status);
        if(result == null){
            return new ErrorDataResult<>(OrderMessages.getOrdersEmpty);
        }

        return new SuccessDataResult<>(result, OrderMessages.getOrdersByStatusSuccess);
    }

    @Override
    public Result confirmPayment(int orderId) {
        var result = getById(orderId);
        if(!result.isSuccess()) {
            return new ErrorResult(OrderMessages.orderCannotBeFound);
        }


        var order = result.getData();
        if(paymentService.getByOrderId(order.getId()).getData().getAmount() != order.getTotalPrice()){
            return new ErrorResult(OrderMessages.paymentAmountNotEqual);

        }

        order.setStatus("PAID");
        orderDao.save(order);
        return new SuccessResult(OrderMessages.orderPaymentConfirmed);
    }


    private String generateOrderNumber(){
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

}
