package br.com.joaouchoa.AppProdutos.config;

import br.com.joaouchoa.AppProdutos.model.Role;
import br.com.joaouchoa.AppProdutos.model.User;
import br.com.joaouchoa.AppProdutos.repository.UserRepository;
import br.com.joaouchoa.AppProdutos.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName(Role.Values.ADMIN.name());
            roleRepository.save(roleAdmin);
        }

        var roleSeller = roleRepository.findByName(Role.Values.SELLER.name());
        if (roleSeller == null) {
            roleSeller = new Role();
            roleSeller.setName(Role.Values.SELLER.name());
            roleRepository.save(roleSeller);
        }

        var roleCustomer = roleRepository.findByName(Role.Values.CUSTOMER.name());
        if (roleCustomer == null) {
            roleCustomer = new Role();
            roleCustomer.setName(Role.Values.CUSTOMER.name());
            roleRepository.save(roleCustomer);
        }

        // Cria usuário admin se não existir
        var userAdmin = userRepository.findByEmail("admin@example.com");

        Role finalRoleAdmin = roleAdmin;
        userAdmin.ifPresentOrElse(
                user -> System.out.println("Admin já existe"),
                () -> {
                    User admin = new User();
                    admin.setName("Administrador");
                    admin.setEmail("admin@example.com");
                    admin.setPassword(passwordEncoder.encode("123"));
                    admin.setRoles(Set.of(finalRoleAdmin));
                    userRepository.save(admin);
                    System.out.println("Admin criado com sucesso!");
                }
        );
    }
}
