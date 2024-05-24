package recipes.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import recipes.entities.Users;

import java.util.List;

@Service
public interface UserService {
    UserDetails loadUserByEmail(String email);
    Users register(Users users);
    List<Users> findAllUsers();
    Users findByEmail(String email);
}
