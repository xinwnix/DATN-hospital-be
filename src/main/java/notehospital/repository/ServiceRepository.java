package notehospital.repository;

import notehospital.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findServiceById(long id);
}
