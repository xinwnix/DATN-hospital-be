package notehospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWithDataDTO<T>{
    int code;
    String message;
    T data;
}
