package com.authentication.authentication.conrtroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.authentication.auth.JwtUtils;
import com.authentication.authentication.dto.AuthRequest;
import com.authentication.authentication.dto.AuthResponse;
import com.authentication.authentication.entity.User;
import com.authentication.authentication.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> postMethodName(@RequestBody AuthRequest req) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        String token = jwtUtils.createToken(req.getUsername(), "USER");
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already present.");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

}
