package skylab.skymerch.business.constants;

import skylab.skymerch.entities.Order;

import java.util.List;

public class OrderMessages {
    public static String OrderCannotBeNull = "Order cannot be null";
    public static String orderAdded = "Order added";
    public static String orderCannotBeFound = "Order cannot be found";
    public static String orderDeleted = "Order deleted";
    public static String orderUpdated = "Order updated";
    public static String getOrderByIdSuccess = "Order fetched";
    public static String getOrdersByUserIdSuccess = "Orders fetched";
    public static String getOrdersEmpty = "Orders are empty";
    public static String getOrdersSuccess = "Orders fetched";
    public static String getOrdersByStatusSuccess = "Orders fetched";
    public static String paymentAmountNotEqual = "Payment amount is not equal to order total price";
    public static String orderPaymentConfirmed = "Order payment confirmed";
    public static String addressCannotBeFound = "Address cannot be found";
    public static String orderPaymentNotConfirmed;
}
