package notehospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import notehospital.entity.Account;
import notehospital.entity.Facility;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    long id;
    String image;
    String name;
    String price;
    String description;
    private Facility facility;

    public ServiceResponse(long id, String image, String name, String price, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
