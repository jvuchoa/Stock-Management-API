package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.service.CategoriaService;
import br.com.joaouchoa.AppProdutos.service.dto.CategoriaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<CategoriaDto> criaCategoria(@RequestBody CategoriaDto request) {
        CategoriaDto response = categoriaService.criarCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> buscarCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @GetMapping("/hierarquia")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<CategoriaDto>> listarHierarquia() {
        return ResponseEntity.ok(categoriaService.listarHierarquia());
    }

    @GetMapping("/pai/{parentId}")
    public ResponseEntity<List<CategoriaDto>> listarPorPai(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoriaService.listarPorPai(parentId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<CategoriaDto> atualizaCategoria(
            @PathVariable Long id, @RequestBody CategoriaDto request) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deletaCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
