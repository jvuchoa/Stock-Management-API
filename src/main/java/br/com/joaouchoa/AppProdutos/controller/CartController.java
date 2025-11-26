package br.com.joaouchoa.AppProdutos.controller;

import br.com.joaouchoa.AppProdutos.model.Car;
import br.com.joaouchoa.AppProdutos.model.CarItem;
import br.com.joaouchoa.AppProdutos.model.User;
import br.com.joaouchoa.AppProdutos.service.CarService;
import br.com.joaouchoa.AppProdutos.service.dto.CarItemResquestDTO;
import br.com.joaouchoa.AppProdutos.service.dto.CartDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {


    private final CarService cartService;

    public CartController(CarService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public CartDTO getCart(@AuthenticationPrincipal User user) {
        Car cart = cartService.getOrCreateCart(user);
        return new CartDTO(
                cart.getItems().stream()
                        .map(i -> new CarItemResquestDTO(i.getProductName(), i.getQuantity(), i.getPriceSnapshot()))
                        .collect(Collectors.toList()),
                cart.getTotal()
        );
    }

    @PostMapping("/items")
    public CarItem addItem(@AuthenticationPrincipal User user, @RequestBody CarItemResquestDTO dto) {
        Car cart = cartService.getOrCreateCart(user);
        CarItem item = new CarItem();
        item.setProductName(dto.productName());
        item.setQuantity(dto.quantity());
        item.setPriceSnapshot(dto.priceSnapshot());
        return cartService.addItem(cart, item);
    }

    @PutMapping("/items/{itemId}")
    public CarItem updateItem(@PathVariable UUID itemId, @RequestBody CarItemResquestDTO dto) {
        return cartService.updateItem(itemId, dto.quantity(), dto.priceSnapshot());
    }

    @DeleteMapping("/items/{itemId}")
    public void deleteItem(@PathVariable UUID itemId) {
        cartService.removeItem(itemId);
    }
}
