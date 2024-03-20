package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer>{
    Order findById(int id);
    Order findByOrderNumber(String orderNumber);
    Order findByStatus(String status);
    Order findByCreatedAt(Date createdAt);

    List<Order> findAllByStatus(String status);

    List<Order> findAllByUserId(int userId);
}
