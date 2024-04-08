package com.example.Cashfree.Integration.Gateway.Made.Easy;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cashfree.ApiException;
import com.cashfree.ApiResponse;
import com.cashfree.Cashfree;
import com.cashfree.model.CreateOrderRequest;
import com.cashfree.model.CustomerDetails;
import com.cashfree.model.OrderEntity;
import com.cashfree.model.OrderMeta;

@RestController
@RequestMapping("/cashfree")
public class CashFreeController {

    // Your Cashfree API credentials
    private static final String X_CLIENT_ID = "Your Cashfree Client Id";
    private static final String X_CLIENT_SECRET = "Your Cashfree Secret";

    private Cashfree cashfree; // Cashfree object for making API calls

    // Constructor to initialize Cashfree object with API credentials
    public CashFreeController() {
        Cashfree.XClientId = X_CLIENT_ID;
        Cashfree.XClientSecret = X_CLIENT_SECRET;
        Cashfree.XEnvironment = Cashfree.SANDBOX; // Set The Environment as SANDBOX OR PRODUCTION
        cashfree = new Cashfree();
    }

    // API endpoint to create an order
    @GetMapping("/createOrder")
    public ResponseEntity<?> createOrder(@RequestParam String customerId, @RequestParam String customerPhone,
            @RequestParam String customerEmail, @RequestParam Double amount, @RequestParam String customerNote) {
        // Create customer details object
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setCustomerId(customerId);
        customerDetails.setCustomerPhone(customerPhone);
        customerDetails.setCustomerEmail(customerEmail);

        // Create order request object
        CreateOrderRequest request = new CreateOrderRequest();
        request.setOrderAmount(amount);
        request.setOrderCurrency("SET ORDER CURRENCY TYPE");
        request.setCustomerDetails(customerDetails);
        request.setOrderNote(customerNote);

        // Set order meta data
        OrderMeta orderMeta = new OrderMeta();
        orderMeta.setPaymentMethods("cc,dc,upi");
        request.setOrderMeta(orderMeta);

        try {
            // Make API call to create order
            ApiResponse<OrderEntity> response = cashfree.PGCreateOrder("SET YOUR API VERSION", request, null, null,
                    null);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    // API endpoint to get order details
    @GetMapping("getOrder")
    public ResponseEntity<?> getOrder(@RequestParam String orderId) {
        try {
            // Make API call to fetch order details
            ApiResponse<OrderEntity> responseFetchOrder = cashfree.PGFetchOrder("SET YOUR API VERSION", orderId, null,
                    null,
                    null);
            return new ResponseEntity<>(responseFetchOrder, HttpStatus.CREATED);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

}
