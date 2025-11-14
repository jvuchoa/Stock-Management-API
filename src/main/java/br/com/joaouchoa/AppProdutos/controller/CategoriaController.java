package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.service.CategoriaService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;


}