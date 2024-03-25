package skylab.skymerch.webAPI.controllers;

import org.springframework.web.bind.annotation.*;
import skylab.skymerch.business.abstracts.PaymentService;
import skylab.skymerch.core.utilities.result.DataResult;
import skylab.skymerch.core.utilities.result.Result;
import skylab.skymerch.entities.Dtos.RequestPaymentDto;
import skylab.skymerch.entities.Payment;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/addPayment")
    public Result addPayment(@RequestBody RequestPaymentDto payment) {
        return paymentService.addPayment(payment);
    }

    @PostMapping("/deletePayment")
    public Result deletePayment(@RequestBody int paymentId) {
        return paymentService.deletePayment(paymentId);
    }

    @PostMapping("/updatePayment")
    public Result updatePayment(@RequestBody RequestPaymentDto requestPaymentDto) {
        return paymentService.updatePayment(requestPaymentDto);
    }

    @GetMapping("/getPaymentById")
    public DataResult<Payment> getPaymentById(@RequestParam int paymentId) {
        return paymentService.getById(paymentId);
    }

    @GetMapping("/getPayments")
    public DataResult<List<Payment>> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/getPaymentsByUserId")
    public DataResult<List<Payment>> getPaymentsByUserId(@RequestParam int userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @GetMapping("/getPaymentsByStatus")
    public DataResult<List<Payment>> getPaymentsByStatus(@RequestParam String status) {
        return paymentService.getPaymentsByStatus(status);
    }

    @GetMapping("/getPaymentsByOrderId")
    public DataResult<Payment> getPaymentsByOrderId(@RequestParam int orderId) {
        return paymentService.getByOrderId(orderId);
    }




}
