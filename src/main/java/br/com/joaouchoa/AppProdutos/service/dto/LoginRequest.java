package br.com.joaouchoa.AppProdutos.service.dto;

public record LoginRequest(
        String name,
        String email,
        String password
) {
}

