package notehospital.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import notehospital.dto.request.CreatePrescriptionRequest;
import notehospital.dto.request.OrderRequest;
import notehospital.dto.request.ResultRequest;
import notehospital.entity.Order;
import notehospital.entity.Prescription;
import notehospital.entity.PrescriptionItem;
import notehospital.entity.Result;
import notehospital.enums.OrderStatus;
import notehospital.service.OrderService;
import notehospital.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "api")
@CrossOrigin(origins = "https://hospital-be.vercel.app/")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ResponseHandler responseHandler;

    @PostMapping("order")
    public ResponseEntity createOrder(@RequestBody OrderRequest orderRequest){
        Order order = orderService.createOrder(orderRequest);
        return responseHandler.response(201,"Successfully create new order!", order);
    }

    @GetMapping("order")
    public ResponseEntity getOrder(){
        List<Order> order = orderService.getOrder();
        return responseHandler.response(201,"Successfully create new order!", order);
    }

    @GetMapping("order/{userId}")
    public ResponseEntity getOrderHistory(@PathVariable long userId){
        List<Order> orders = orderService.getOrderHistory(userId);
        return responseHandler.response(200,"Successfully get history order!", orders);
    }

    @GetMapping("order-detail/{orderId}")
    public ResponseEntity getOrderDetail(@PathVariable long orderId){
        Order orders = orderService.getOrderDetail(orderId);
        return responseHandler.response(200,"Successfully get order detail!", orders);
    }

    @GetMapping("schedule/{userId}")
    public ResponseEntity getSchedule(@PathVariable long userId){
        List<Order> orders = orderService.getDoctorSchedule(userId);
        return responseHandler.response(200,"Successfully get schedule!", orders);
    }

    @PatchMapping("order/{orderId}/{status}")
    public ResponseEntity updateStatusOrder(@PathVariable long orderId, @PathVariable OrderStatus status){
        Order orders = orderService.updateStatusOrder(orderId, status);
        return responseHandler.response(200,"Successfully update order status!", orders);
    }

    @PostMapping("/prescription/{orderId}")
    public ResponseEntity createPrescription(@PathVariable long orderId, @RequestBody CreatePrescriptionRequest createPrescriptionRequest){
        Order order = orderService.createPrescription(orderId, createPrescriptionRequest);
        return responseHandler.response(200,"Successfully create prescription!", order);
    }

    @PostMapping("/result/{orderId}")
    public ResponseEntity createResult(@PathVariable long orderId, @RequestBody List<ResultRequest> resultRequests){
        Order order = orderService.createResult(orderId, resultRequests);
        return responseHandler.response(200,"Successfully create result!", order);
    }

    @GetMapping("/health-record/{userId}")
    public ResponseEntity getHealthRecord(@PathVariable long userId){
        List<Result> results = orderService.getHealthRecord(userId);
        return responseHandler.response(200,"Successfully get health record!", results);
    }
}
