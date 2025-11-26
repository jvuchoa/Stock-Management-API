package br.com.joaouchoa.AppProdutos.service;

import br.com.joaouchoa.AppProdutos.model.Car;
import br.com.joaouchoa.AppProdutos.model.CarItem;
import br.com.joaouchoa.AppProdutos.model.User;
import br.com.joaouchoa.AppProdutos.repository.CarRepository;
import br.com.joaouchoa.AppProdutos.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarService {

    private final CarRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CarService(CartItemRepository cartItemRepository, CarRepository carRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = carRepository;
    }

    public Car getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Car cart = new Car();
                    cart.setUser(user);
                    cart.setTotal(0);
                    return cartRepository.save(cart);
                });
    }

    public CarItem addItem(Car cart, CarItem item) {
        item.setCar(cart);
        CarItem saved = cartItemRepository.save(item);
        cart.getItems().add(saved);
        recalculateCart(cart);
        return saved;
    }

    public CarItem updateItem(UUID itemId, int quantity, double priceSnapshot) {
        CarItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        item.setQuantity(quantity);
        item.setPriceSnapshot(priceSnapshot);
        recalculateCart(item.getCar());
        return cartItemRepository.save(item);
    }

    public void removeItem(UUID itemId) {
        CarItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        Car cart = item.getCar();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        recalculateCart(cart);
    }

    private void recalculateCart(Car cart) {
        cart.recalculateTotal();
        cartRepository.save(cart);
    }
}
