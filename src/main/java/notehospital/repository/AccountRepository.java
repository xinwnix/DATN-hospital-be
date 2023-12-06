package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Service;
import notehospital.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.phone = ?1 OR u.email = ?1")
    Account findByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail);

    Account findAccountById(long id);
    @Query("SELECT d FROM Account d WHERE d.type = :accountType AND d.service.id = :serviceId")
    List<Account> findDoctorsByServiceId(@Param("accountType") AccountType accountType,
                                         @Param("serviceId") Long serviceId);
}
