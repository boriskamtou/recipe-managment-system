package recipes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.Users;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUserByEmail(String username);
    Users findByEmail(String email);
}
