package notehospital.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notehospital.entity.Account;
import notehospital.entity.Prescription;
import notehospital.entity.Service;
import notehospital.enums.OrderStatus;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderResponse {
    long id;
    Date createdAt;
    String note;
    String conclude;
    Date testDate;
    OrderStatus status;
    AccountResponseDTO doctor;
    long facilityod_id;
    AccountResponseDTO patient;
    Prescription prescription;
    Set<ResultResponse> results;
}
