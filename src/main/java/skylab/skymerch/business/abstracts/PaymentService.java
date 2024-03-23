package skylab.skymerch.business.abstracts;

import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestPaymentDto;
import skylab.skymerch.entities.Payment;

import java.util.List;

public interface PaymentService {
    Result addPayment(Payment payment);

    Result deletePayment(int paymentId);

    Result updatePayment(RequestPaymentDto requestPaymentDto);

    DataResult<Payment> getById(int paymentId);

    DataResult<Payment> getByOrderId(int orderId);

    DataResult <List<Payment>> getPayments();

    DataResult<List<Payment>> getPaymentsByStatus(String status);

    DataResult<List<Payment>> getPaymentsByUserId(int userId);

}
