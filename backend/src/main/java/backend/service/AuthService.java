package backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.dto.AuthResponse;
import backend.dto.LoginRequest;
import backend.entity.User;
import backend.repository.UserRepository;
import backend.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        if (!user.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Compte désactivé");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole(),
                user.getSchoolId()
        );

        return new AuthResponse(token, user.getRole(), user.getSchoolId(), user.getEmail());
    }
}