package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.service.EstoqueService;
import br.com.joaouchoa.AppProdutos.service.dto.EstoqueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/estoques")
public class EstoqueController {

    private final EstoqueService estoqueService;


    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<EstoqueDto> criaEstoque(@RequestBody EstoqueDto request) {
        EstoqueDto response = estoqueService.criarEstoque(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_SELLER')")
    public ResponseEntity<List<EstoqueDto>> listarEstoques() {
        return ResponseEntity.ok(estoqueService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueDto> buscarEstoquePorId(@PathVariable Long id) {
        return ResponseEntity.ok(estoqueService.buscarPorId(id));
    }
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<EstoqueDto>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.listarPorProduto(produtoId));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or hasAuthority('SCOPE_SELLER')")
    public ResponseEntity<EstoqueDto> atualizaEstoque(
            @PathVariable Long id, @RequestBody EstoqueDto request) {
        return ResponseEntity.ok(estoqueService.atualizarEstoque(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deletaEstoque(@PathVariable Long id) {
        estoqueService.deletarEstoque(id);
        return ResponseEntity.noContent().build();
    }
}
