package backend.repository;

import backend.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findBySubdomain(String subdomain);
    Optional<School> findByEmail(String email);
    boolean existsBySubdomain(String subdomain);
    boolean existsByEmail(String email);
}