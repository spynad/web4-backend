package app.controller;

import app.configuration.security.JwtTokenProvider;
import app.model.User;
import app.payload.request.UserRequest;
import app.payload.response.JwtResponse;
import app.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController (AuthenticationManager manager, JwtTokenProvider provider, UserRepository repository, PasswordEncoder passwordEncoder) {
        authenticationManager = manager;
        jwtTokenProvider = provider;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        if (request.getUsername().equals("") || request.getPassword().equals("")) {
            throw new RuntimeException("No username or password");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            String token = jwtTokenProvider.createJwtToken(user);

            return ResponseEntity.ok()
                    .body(new JwtResponse(token, user.getId(), user.getUsername()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        if (request.getUsername().equals("") || request.getPassword().equals("")) {
            throw new RuntimeException("No username or password");
        }

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User exists");
        }

        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        repository.save(user);

        return ResponseEntity.ok().build();
    }

}
