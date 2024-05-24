package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.adapters.UserAdapter;
import recipes.repositories.UserRepository;

@Service
public class AppUserServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public AppUserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserAdapter(repository.findByEmail(email));
    }
}
