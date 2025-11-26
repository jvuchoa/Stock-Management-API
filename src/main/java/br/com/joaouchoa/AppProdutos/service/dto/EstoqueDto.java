package br.com.joaouchoa.AppProdutos.service.dto;

import java.time.LocalDateTime;
public record EstoqueDto(
        Long id,
        Long produtoId,
        Integer quantidade,
        LocalDateTime dataAtualizacao
) {}
