package br.com.joaouchoa.AppProdutos.service.dto;

public record RegisterRequest(
        String name,
        String email,
        String password
){}
