package br.com.joaouchoa.AppProdutos.repository;

import br.com.joaouchoa.AppProdutos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
