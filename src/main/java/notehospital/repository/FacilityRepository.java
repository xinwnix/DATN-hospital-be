package notehospital.repository;

import notehospital.entity.Account;
import notehospital.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long>{
}
