package recipes.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.entities.Users;
import recipes.services.UserService;

@RestController
public class UserController {

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody @Valid Users users) {
        try {
            var userToSave = new Users();
            userToSave.setEmail(users.getEmail());
            userToSave.setPassword(passwordEncoder.encode(users.getPassword()));
            var userStore = userService.register(userToSave);
            return ResponseEntity.ok(userStore);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
