package app.controller;

import app.configuration.security.JwtTokenProvider;
import app.model.User;
import app.payload.request.UserRequest;
import app.payload.response.JwtResponse;
import app.repository.UserRepository;
import app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    UserService userService;

    public UserController (AuthenticationManager manager, JwtTokenProvider provider, UserService service) {
        authenticationManager = manager;
        jwtTokenProvider = provider;
        userService = service;
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        if (request.getUsername().equals("") || request.getPassword().equals("")) {
            throw new BadCredentialsException("No username or password");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

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
            throw new BadCredentialsException("No username or password");
        }
        userService.saveUser(request);

        return ResponseEntity.ok().build();
    }

}
