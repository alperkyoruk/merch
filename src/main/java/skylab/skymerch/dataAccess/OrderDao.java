package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Order;

import java.util.Date;

public interface OrderDao extends JpaRepository<Order, Integer>{
    Order findById(int id);
    Order findByOrderNumber(String orderNumber);
    Order findByOrderStatus(String orderStatus);
    Order findByCreatedAt(Date createdAt);
}
