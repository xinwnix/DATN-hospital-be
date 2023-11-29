package notehospital.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class OrderRequest {
    Date testDate;
    long doctorId;
    long[] services;
    String note;
    long patientId;
}
