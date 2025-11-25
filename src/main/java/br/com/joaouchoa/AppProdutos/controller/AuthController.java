package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.model.Role;
import br.com.joaouchoa.AppProdutos.model.User;
import br.com.joaouchoa.AppProdutos.repository.UserRepository;
import br.com.joaouchoa.AppProdutos.service.dto.AuthResponse;
import br.com.joaouchoa.AppProdutos.service.dto.CreateUserDTO;
import br.com.joaouchoa.AppProdutos.service.dto.LoginRequest;
import br.com.joaouchoa.AppProdutos.service.dto.RegisterRequest;
import br.com.joaouchoa.AppProdutos.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtEncoder  jwtEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                           JwtEncoder jwtEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CreateUserDTO createUserDTO) {

        // Verifica se já existe usuário com esse e-mail
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Busca a role BASIC (igual ao código 1)
        var roleName = Role.Values.SELLER.getName();
        var customerRole = roleRepository.findByName(roleName);

        if (customerRole == null) {
            throw new RuntimeException("Role SELLER not found in database");
        }

        var userFromDb = userRepository.findByEmail(createUserDTO.email());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Cria o usuário
        User user = new User();
        user.setName(createUserDTO.name());
        user.setEmail(createUserDTO.email());
        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        user.setRoles(Set.of(customerRole)); // <-- Igual código 1

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        var user = userRepository.findByEmail(request.email());

        if (user.isEmpty() || !user.get().isLoginCorrect(request, passwordEncoder)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.get().getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getName().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new AuthResponse(jwtValue, expiresIn));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}

