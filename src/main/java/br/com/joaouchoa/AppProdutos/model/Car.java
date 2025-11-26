package br.com.joaouchoa.AppProdutos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {


    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarItem> items = new ArrayList<>();

    private double total;

    public void recalculateTotal() {
        this.total = items.stream()
                .mapToDouble(item -> item.getPriceSnapshot() * item.getQuantity())
                .sum();
    }
}
