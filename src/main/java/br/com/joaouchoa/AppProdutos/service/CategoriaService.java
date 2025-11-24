package br.com.joaouchoa.AppProdutos.service;

import br.com.joaouchoa.AppProdutos.model.Categoria;
import br.com.joaouchoa.AppProdutos.repository.CategoriaRepository;
import br.com.joaouchoa.AppProdutos.service.dto.CategoriaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaDto criarCategoria(CategoriaDto dto) {

        validarNomeObrigatorio(dto.nome());
        validarDuplicidade(dto.nome(), dto.parentId());

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        if (dto.parentId() != null) {
            categoria.setParent(
                    categoriaRepository.findById(dto.parentId())
                            .orElseThrow(() -> new RuntimeException("Categoria pai não encontrada"))
            );
        }

        categoriaRepository.save(categoria);
        return toDto(categoria);
    }

    public List<CategoriaDto> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CategoriaDto buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return toDto(categoria);
    }

    public List<CategoriaDto> listarHierarquia() {
        return categoriaRepository.findByParentIsNull()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<CategoriaDto> listarPorPai(Long parentId) {
        return categoriaRepository.findByParentId(parentId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CategoriaDto atualizarCategoria(Long id, CategoriaDto dto) {

        validarNomeObrigatorio(dto.nome());
        validarDuplicidade(dto.nome(), dto.parentId());

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoria.setNome(dto.nome());

        if (dto.parentId() != null) {
            categoria.setParent(
                    categoriaRepository.findById(dto.parentId())
                            .orElseThrow(() -> new RuntimeException("Categoria pai não encontrada"))
            );
        } else {
            categoria.setParent(null);
        }

        categoriaRepository.save(categoria);
        return toDto(categoria);
    }


    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada");
        }

        if (categoriaRepository.existsByParentId(id)) {
            throw new RuntimeException("Categoria possui categorias filhas. Remova-as primeiro.");
        }

        if (categoriaRepository.hasProdutos(id)) {
            throw new RuntimeException("Categoria possui produtos. Remova os produtos primeiro.");
        }

        categoriaRepository.deleteById(id);
    }

    private void validarNomeObrigatorio(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("Nome da categoria é obrigatório");
        }
    }

    private void validarDuplicidade(String nome, Long parentId) {
        boolean existe = categoriaRepository
                .findByNomeAndParentId(nome, parentId)
                .isPresent();

        if (existe) {
            throw new RuntimeException("Já existe uma categoria com este nome neste nível");
        }
    }

    private CategoriaDto toDto(Categoria categoria) {
        return CategoriaDto.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .parentId(categoria.getParent() != null ? categoria.getParent().getId() : null)
                .parentNome(categoria.getParent() != null ? categoria.getParent().getNome() : null)
                .build();
    }
}
