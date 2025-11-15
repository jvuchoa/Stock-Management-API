package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.model.Produtos;
import br.com.joaouchoa.AppProdutos.service.ProdutosService;
import br.com.joaouchoa.AppProdutos.service.dto.ProdutoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutosService produtosService;

    @GetMapping
    public ResponseEntity<List<Produtos>> listarProdutos() {
        return ResponseEntity.ok(produtosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtos> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtosService.buscarPorId(id));
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ProdutoDto> buscarDtoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtosService.buscarDtoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produtos> criarProduto(@RequestBody Produtos produto) {
        return ResponseEntity.ok(produtosService.criarProduto(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produtos> atualizarProduto(
            @PathVariable Long id,
            @RequestBody Produtos produto
    ) {
        return ResponseEntity.ok(produtosService.atualizarProduto(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtosService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/by-categoria/{categoriaId}")
    public ResponseEntity<List<Produtos>> listarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(produtosService.listarPorCategoria(categoriaId));
    }
}
