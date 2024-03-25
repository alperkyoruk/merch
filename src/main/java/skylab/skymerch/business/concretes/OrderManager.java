package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.OrderMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.OrderDao;
import skylab.skymerch.entities.Dtos.RequestOrderDto;
import skylab.skymerch.entities.Order;
import skylab.skymerch.entities.Product;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class OrderManager implements OrderService {


    @Autowired
    private OrderDao orderDao;


    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;




    @Override
    public Result addOrder(RequestOrderDto requestOrderDto) {

        if(requestOrderDto.getAddressId() == 0){
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }


        var addressResponse = addressService.getById(requestOrderDto.getAddressId()).getData();
        var userResponse = userService.getUserById(requestOrderDto.getUserId()).getData();

        if(addressResponse == null){
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }

        if(userResponse == null){
            return new ErrorResult(OrderMessages.userCannotBeFound);
        }

        var totalPrice = requestOrderDto.getTotalPrice();

        Order order = Order.builder()
                .address(addressResponse)
                .orderNumber(generateOrderNumber())
                .createdAt(new Date())
                .status(requestOrderDto.getStatus())
                .user(userResponse)
                .products(requestOrderDto.getProducts())
                .totalPrice(calculateTotalPrice(requestOrderDto.getId()))
                .build();


        for (Product product : order.getProducts()) {
            product.setStock(product.getStock() - 1);
        }

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
    public Result updateOrder(RequestOrderDto requestOrderDto) {
        var result = getById(requestOrderDto.getId());
        if(!result.isSuccess()) {
            return new ErrorResult(OrderMessages.orderCannotBeFound);
        }

        var addressResponse = addressService.getById(requestOrderDto.getAddressId()).getData();
        if(addressResponse == null){
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }


        var order = Order.builder()
                .id(requestOrderDto.getId())
                .address(addressResponse)
                .orderNumber(requestOrderDto.getOrderNumber())
                .status(requestOrderDto.getStatus())
                .build();

        orderDao.save(order);
        return new SuccessResult(OrderMessages.orderUpdated);
    }

    @Override
    public DataResult<Order> getById(int orderId) {
        var result = orderDao.findById(orderId);
        if(result == null){
            return new ErrorDataResult<>(OrderMessages.orderCannotBeFound);
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



    private String generateOrderNumber(){
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    private float calculateTotalPrice(int orderId) {

        var order = getById(orderId).getData();

        float totalPrice = 0;
        for( Product product : order.getProducts() ){
            totalPrice += product.getPrice();
        }

        return totalPrice;
    }



}
