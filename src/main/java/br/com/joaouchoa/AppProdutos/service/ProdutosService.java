package br.com.joaouchoa.AppProdutos.service;

import br.com.joaouchoa.AppProdutos.model.Categoria;
import br.com.joaouchoa.AppProdutos.model.Produtos;
import br.com.joaouchoa.AppProdutos.repository.CategoriaRepository;
import br.com.joaouchoa.AppProdutos.repository.ProdutosRepository;
import br.com.joaouchoa.AppProdutos.service.dto.ProdutoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutosService {

    private final ProdutosRepository produtosRepository;
    private final CategoriaRepository categoriaRepository;

    public Produtos criarProduto(Produtos produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("Categoria é obrigatória");
        }

        Categoria categoria = categoriaRepository.findById(produto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        produto.setCategoria(categoria);
        return produtosRepository.save(produto);
    }

    public List<Produtos> listarTodos() {
        return produtosRepository.findAll();
    }

    public Produtos buscarPorId(Long id) {
        return produtosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public ProdutoDto buscarDtoPorId(Long id) {
        ProdutoDto dto = produtosRepository.findByIdDto(id);
        if (dto == null) throw new RuntimeException("Produto não encontrado");
        return dto;
    }

    public Produtos atualizarProduto(Long id, Produtos atualizado) {
        Produtos existente = buscarPorId(id);

        existente.setNome(atualizado.getNome());
        existente.setPreco(atualizado.getPreco());
        existente.setCodigoBarras(atualizado.getCodigoBarras());
        existente.setEstoque(atualizado.getEstoque());

        if (atualizado.getCategoria() != null && atualizado.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(atualizado.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            existente.setCategoria(categoria);
        }

        return produtosRepository.save(existente);
    }

    public void deletarProduto(Long id) {
        if (!produtosRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produtosRepository.deleteById(id);
    }

    public List<Produtos> listarPorCategoria(Long categoriaId) {
        return produtosRepository.findByCategoriaId(categoriaId);
    }
}
