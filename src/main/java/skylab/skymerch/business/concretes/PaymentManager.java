package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.business.abstracts.PaymentService;
import skylab.skymerch.business.constants.PaymentMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.OrderDao;
import skylab.skymerch.dataAccess.PaymentDao;
import skylab.skymerch.entities.Dtos.RequestPaymentDto;
import skylab.skymerch.entities.Payment;

import java.util.Date;
import java.util.List;

@Service
public class PaymentManager implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private OrderService orderService;



    @Override
    public Result addPayment(Payment payment) {
        //add payment
        if(payment == null) {
            return new ErrorResult(PaymentMessages.PaymentCannotBeNull);
        }

        if(payment.getOrder().getTotalPrice() == payment.getAmount()) {
            payment.setStatus("PAID");
            orderService.getById(payment.getOrder().getId()).getData().setStatus("PAID");
        }
        payment.setTimePaid(new Date());
        paymentDao.save(payment);
        return new SuccessResult(PaymentMessages.PaymentAdded);
    }

    @Override
    public Result deletePayment(int paymentId) {
        var result = getById(paymentId);
        if(!result.isSuccess()) {
            return new ErrorResult(PaymentMessages.PaymentCannotBeFound);
        }

        var payment = result.getData();
        paymentDao.delete(payment);

        return new SuccessResult(PaymentMessages.PaymentDeleted);
    }

    @Override
    public Result updatePayment(RequestPaymentDto requestPaymentDto) {
        var order = orderService.getById(requestPaymentDto.getOrderId()).getData();
        Payment payment = Payment.builder().
                id(requestPaymentDto.getId()).
                order(order).
                timePaid(requestPaymentDto.getTimePaid()).
                type(requestPaymentDto.getType()).
                status(requestPaymentDto.getStatus()).
                build();

        paymentDao.save(payment);
        return new SuccessResult(PaymentMessages.PaymentUpdated);
    }


    @Override
    public DataResult<Payment> getById(int paymentId) {
        var result = paymentDao.findById(paymentId);
        if(result == null) {
            return new ErrorDataResult<>(PaymentMessages.PaymentCannotBeFound);
        }

        return new SuccessDataResult<>(result, PaymentMessages.getPaymentByIdSuccess);
    }

    @Override
    public DataResult<Payment> getByOrderId(int orderId) {
        var result = paymentDao.findByOrderId(orderId);
        if(result == null) {
            return new ErrorDataResult<>(PaymentMessages.PaymentCannotBeFound);
        }

        return new SuccessDataResult<>(result, PaymentMessages.getPaymentByOrderIdSuccess);
    }

    @Override
    public DataResult<List<Payment>> getPayments() {
        var result = paymentDao.findAll();
        if(result.isEmpty()) {
            return new ErrorDataResult<>(PaymentMessages.PaymentCannotBeFound);
        }

        return new SuccessDataResult<>(result, PaymentMessages.getPaymentsSuccess);
    }

    @Override
    public DataResult<List<Payment>> getPaymentsByStatus(String status) {
        var result = paymentDao.findAllByStatus(status);
        if(result == null) {
            return new ErrorDataResult<>(PaymentMessages.PaymentCannotBeFound);
        }

        return new SuccessDataResult<>(result, PaymentMessages.getPaymentsByStatusSuccess);
    }

    @Override
    public DataResult<List<Payment>> getPaymentsByUserId(int userId) {
        var result = paymentDao.findAllByUserId(userId);
        if(result == null) {
            return new ErrorDataResult<>(PaymentMessages.PaymentCannotBeFound);
        }

        return new SuccessDataResult<>(result, PaymentMessages.getPaymentsByUserIdSuccess);
    }
}
