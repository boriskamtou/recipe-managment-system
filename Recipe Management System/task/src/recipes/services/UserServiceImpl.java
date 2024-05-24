package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.adapters.UserAdapter;
import recipes.entities.Users;
import recipes.repositories.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByEmail(String email) {
        Users users = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));
        return new UserAdapter(users);
    }

    @Override
    public Users register(Users users) {
        Users usersDetails = userRepository.findByEmail(users.getEmail());
        if (usersDetails == null) {
            return userRepository.save(users);
        } else {
            throw  new UsernameNotFoundException("User Not Found");
        }
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
