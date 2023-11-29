package notehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import notehospital.enums.OrderStatus;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date createdAt;
    private String note;
    private String conclude;
    private Date testDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CONFIRM;

    @ManyToOne()
    @JoinColumn(name = "account_id")
    private Account patient;

    @ManyToOne()
    @JoinColumn(name = "doctor_id")
    private Account doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Result> results;
}
