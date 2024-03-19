package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Payment;

import java.util.List;

public interface PaymentService {
    Result addPayment(Payment payment);

    Result deletePayment(int paymentId);

    Result updatePayment(Payment payment);

    Result makePayment(Payment payment);

    DataResult<Payment> getById(int paymentId);

    DataResult<Payment> getByOrderId(int orderId);

    DataResult <List<Payment>> getPayments();

    DataResult<List<Payment>> getPaymentsByStatus(String status);

    DataResult<List<Payment>> getPaymentsByUserId(int userId);

}
