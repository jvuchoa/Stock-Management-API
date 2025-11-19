package br.com.joaouchoa.AppProdutos.repository;

import br.com.joaouchoa.AppProdutos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByParentIsNull();

    List<Categoria> findByParentId(Long parentId);

    Optional<Categoria> findByNomeAndParentId(String nome, Long parentId);

    boolean existsByParentId(Long parentId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Produtos p WHERE p.categoria.id = :categoriaId")
    boolean hasProdutos(Long categoriaId);
}
