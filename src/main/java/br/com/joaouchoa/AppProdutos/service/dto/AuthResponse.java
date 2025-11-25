package br.com.joaouchoa.AppProdutos.service.dto;

public record AuthResponse(
        String accessToken, Long expiresIn
) {
}

