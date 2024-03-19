package skylab.skymerch.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import skylab.skymerch.entities.Payment;

import java.util.Date;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
    Payment findById(int id);
    Payment findByType(String type);
    Payment findByTimePaid(Date timePaid);
}
