package notehospital.dto.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Data;
import notehospital.enums.AccountStatus;
import notehospital.enums.AccountType;
import notehospital.enums.Gender;

import java.time.LocalDate;

@Data
public class AccountResponseDTO {
    private long id;
    private String phone;
    private String fullName;
    private String email;
    private Gender gender;
    private String address;
    private String avatarURL;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
