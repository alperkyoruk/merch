package skylab.skymerch.business.concretes;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.AddressService;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.business.abstracts.ProductService;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.OrderMessages;
import skylab.skymerch.business.constants.ProductMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.OrderDao;
import skylab.skymerch.entities.Dtos.RequestOrderDto;
import skylab.skymerch.entities.Order;
import skylab.skymerch.entities.Product;

import java.security.SecureRandom;
import java.util.ArrayList;
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

    @Autowired
    private ProductService productService;




    public Result addOrder(RequestOrderDto requestOrderDto) {
        // Check if addressId is provided
        if (requestOrderDto.getAddressId() == 0) {
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }

        // Fetch address and user information
        var addressResponse = addressService.getById(requestOrderDto.getAddressId()).getData();
        var userResponse = userService.getUserById(requestOrderDto.getUserId()).getData();

        // Check if address and user are found
        if (addressResponse == null) {
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }

        if (userResponse == null) {
            return new ErrorResult(OrderMessages.userCannotBeFound);
        }

        // Fetch products by their IDs
            List<Product> productsResponse = productService.getProductsByIds(requestOrderDto.getProducts()).getData();



        // Check if products are found
        if (productsResponse.isEmpty()) {
            return new ErrorResult(ProductMessages.productsCannotBeFound);
        }



        // Calculate total price
        float totalPrice = 0;

        // Calculate total price and update stock for products
        for (Product product : productsResponse) {
            totalPrice += product.getPrice();
            product.setStock(product.getStock() - 1);
        }




        // Build the order
        Order order = Order.builder()
                .address(addressResponse)
                .orderNumber(generateOrderNumber())
                .createdAt(new Date())
                .status("CREATED")
                .user(userResponse)
                .products(productsResponse) // Associate products with the order
                .totalPrice(totalPrice)
                .build();

        // Save the order
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
        var result = getByOrderNumber(requestOrderDto.getOrderNumber());
        if(!result.isSuccess()) {
            return new ErrorResult(OrderMessages.orderCannotBeFound);
        }

        var addressResponse = addressService.getById(requestOrderDto.getAddressId()).getData();
        if(addressResponse == null){
            return new ErrorResult(OrderMessages.addressCannotBeFound);
        }


        var order = Order.builder()
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
    public DataResult<Order> getByOrderNumber(String orderNumber) {
        var result = orderDao.findByOrderNumber(orderNumber);
        if(result == null){
            return new ErrorDataResult<>(OrderMessages.orderCannotBeFound);
        }

        return new SuccessDataResult<>(result, OrderMessages.getOrderByOrderNumberSuccess);
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
