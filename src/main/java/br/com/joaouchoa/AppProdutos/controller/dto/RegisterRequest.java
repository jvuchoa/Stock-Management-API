package br.com.joaouchoa.AppProdutos.controller.dto;

import br.com.joaouchoa.AppProdutos.domain.user.Role;

public record RegisterRequest(
        String name,
        String email,
        String password,
        Role role
) {
}
