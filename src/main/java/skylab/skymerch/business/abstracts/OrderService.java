package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestOrderDto;
import skylab.skymerch.entities.Order;

import java.util.List;

public interface OrderService {
    Result addOrder(Order order);

    Result deleteOrder(int orderId);

    Result updateOrder(RequestOrderDto requestOrderDto);

  //  Result changeOrderStatus(int orderId, String status);

  //  Result changeOrderAddress(int orderId, int addressId);

    DataResult<Order> getById(int orderId);

    DataResult <List<Order>> getByUserId(int userId);

    DataResult<List<Order>> getOrders();

    DataResult<List<Order>> getOrdersByStatus(String status);



    }
