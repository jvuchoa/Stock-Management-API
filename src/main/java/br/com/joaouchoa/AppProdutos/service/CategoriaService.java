package br.com.joaouchoa.AppProdutos.service;

import br.com.joaouchoa.AppProdutos.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

}


