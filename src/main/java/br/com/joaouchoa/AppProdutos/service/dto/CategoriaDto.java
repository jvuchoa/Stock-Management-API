package br.com.joaouchoa.AppProdutos.service.dto;

import lombok.Builder;

@Builder
public record CategoriaDto(
        Long id,
        String nome,
        Long parentId,
        String parentNome
) {}