package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Order;
import notehospital.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByPatient(Account patient);
    List<Order> findOrdersByDoctorAndStatus(Account doctor, OrderStatus orderStatus);

    Order findOrderById(long orderId);

    @Query("SELECT o FROM Order o WHERE o.status='CONFIRM'")
    List<Order> findAllConfirm();
}
