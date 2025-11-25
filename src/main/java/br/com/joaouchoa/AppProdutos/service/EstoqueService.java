package br.com.joaouchoa.AppProdutos.service;

import br.com.joaouchoa.AppProdutos.model.Estoque;
import br.com.joaouchoa.AppProdutos.model.Produtos;
import br.com.joaouchoa.AppProdutos.repository.EstoqueRepository;
import br.com.joaouchoa.AppProdutos.repository.ProdutosRepository;
import br.com.joaouchoa.AppProdutos.service.dto.EstoqueDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutosRepository produtoRepository;
    private EstoqueDto toDto(Estoque estoque) {
        return new EstoqueDto(
                estoque.getId(),
                estoque.getProduto().getId(),
                estoque.getQuantidade(),
                LocalDateTime.now()
        );
    }

    private Estoque toEntity(EstoqueDto dto) {
        Produtos produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));
        return new Estoque(
                dto.id(),
                dto.quantidade(),
                produto
        );
    }
    @Transactional
    public EstoqueDto criarEstoque(EstoqueDto request) {
        estoqueRepository.findByProdutoId(request.produtoId()).ifPresent(e -> {
            throw new IllegalArgumentException("Estoque para o Produto ID " + request.produtoId() + " já existe.");
        });
        Estoque novoEstoque = toEntity(request);
        novoEstoque.setId(null);
        Estoque savedEstoque = estoqueRepository.save(novoEstoque);

        return toDto(savedEstoque);
    }

    public List<EstoqueDto> listarTodos() {
        return estoqueRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EstoqueDto buscarPorId(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado com ID: " + id));
        return toDto(estoque);
    }

    public List<EstoqueDto> listarPorProduto(Long produtoId) {
        return estoqueRepository.findByProdutoId(produtoId)
                .map(this::toDto)
                .stream().collect(Collectors.toList());
    }

    @Transactional
    public EstoqueDto atualizarEstoque(Long id, EstoqueDto request) {
        Estoque estoqueExistente = estoqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado para atualização com ID: " + id));

        if (request.quantidade() != null) {
            estoqueExistente.setQuantidade(request.quantidade());
        }

        Estoque updatedEstoque = estoqueRepository.save(estoqueExistente);
        return toDto(updatedEstoque);
    }

    @Transactional
    public void deletarEstoque(Long id) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado para exclusão com ID: " + id));

        estoqueRepository.delete(estoque);
    }
}
