package br.com.joaouchoa.AppProdutos.repository;

import br.com.joaouchoa.AppProdutos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(
            nativeQuery = true,
            value = """
                SELECT 
                    CASE 
                        WHEN COUNT(p.id) > 0 THEN TRUE
                        ELSE FALSE
                    END AS hasProdutos
                FROM tb_produtos p
                WHERE p.categoria_id = :categoriaId
                """
    )
    boolean hasProdutos(Long categoriaId);
}
