package notehospital.dto.response;

import lombok.Data;
import notehospital.entity.Account;
import notehospital.entity.Facility;

import java.util.Set;

@Data

public class ServiceResponse {
    long id;
    String image;
    String name;
    String price;
    String description;
    private Facility facility;
    private Set<Account> doctors;
}
