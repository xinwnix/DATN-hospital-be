package notehospital.repository;

import notehospital.entity.Account;
import notehospital.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u WHERE u.phone = ?1 OR u.email = ?1")
    Account findByUsernameOrPhoneOrEmail(String usernameOrPhoneOrEmail);
    Account findAccountById(long id);

    List<Account> findAccountsByType(AccountType accountType);

}
