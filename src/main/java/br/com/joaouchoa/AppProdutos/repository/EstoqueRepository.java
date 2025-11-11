package br.com.joaouchoa.AppProdutos.repository;

import br.com.joaouchoa.AppProdutos.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque,Long> {
}
