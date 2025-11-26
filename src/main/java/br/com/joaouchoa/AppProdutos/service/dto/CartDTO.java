package br.com.joaouchoa.AppProdutos.service.dto;

import java.util.List;

public record CartDTO(List<CarItemResquestDTO> items,
                      double total) {

}
