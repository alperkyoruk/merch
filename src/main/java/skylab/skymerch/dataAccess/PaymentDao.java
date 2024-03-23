package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Payment;

import java.util.Date;
import java.util.List;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
    Payment findById(int id);
    Payment findByType(String type);
    Payment findByTimePaid(Date timePaid);
    Payment findByOrderId(int orderId);
    List<Payment> findAllByUserId(int userId);
    List<Payment> findAllByStatus(String status);
}
