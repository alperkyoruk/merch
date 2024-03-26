package skylab.skymerch.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skylab.skymerch.business.abstracts.OrderService;
import skylab.skymerch.business.abstracts.PaymentService;
import skylab.skymerch.business.abstracts.UserService;
import skylab.skymerch.business.constants.PaymentMessages;
import skylab.skymerch.core.utilities.result.*;
import skylab.skymerch.dataAccess.PaymentDao;
import skylab.skymerch.entities.Dtos.RequestPaymentDto;
import skylab.skymerch.entities.Payment;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PaymentManager implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    UserService userService;



    @Override
    public Result addPayment(RequestPaymentDto requestPaymentDto) {
        //add payment
        if(requestPaymentDto == null) {
            return new ErrorResult(PaymentMessages.PaymentCannotBeNull);
        }

        if(requestPaymentDto.getAmount()== 0){
            return new ErrorResult(PaymentMessages.PaymentCannotBeNull);
        }

        var orderResponse = orderService.getById(requestPaymentDto.getOrderId());

        var userResponse = userService.getUserById(requestPaymentDto.getUserId()).getData();



        Payment payment = Payment.builder()
                .user(userResponse)
                .paymentNumber(generatePaymentNumber())
                .amount(requestPaymentDto.getAmount())
                .order(orderResponse.getData())
                .timePaid(new Date())
                .build();

                if(orderResponse.getData().getTotalPrice() != requestPaymentDto.getAmount()){
                    return new ErrorResult(PaymentMessages.PaymentAmountIsNotTrue);
                }

                orderResponse.getData().setPayment(payment);
                orderResponse.getData().setStatus("PAID");


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
                order(order).
                type(requestPaymentDto.getType()).
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

    private String generatePaymentNumber(){
        SecureRandom random = new SecureRandom();
        byte[] randomBytes = new byte[32];
        random.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }


}
