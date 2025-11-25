package br.com.joaouchoa.AppProdutos.controller.dto;

import org.apache.catalina.Role;

import java.util.Set;

public record RegisterRequest(
        String name,
        String email,
        String password
){}
