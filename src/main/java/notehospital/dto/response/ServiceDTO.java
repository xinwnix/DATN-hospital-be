package notehospital.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ServiceDTO {
    private long id;
    private String image;
    private String name;
    private String price;
    private String description;
}
