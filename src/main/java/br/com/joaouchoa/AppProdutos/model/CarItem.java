package br.com.joaouchoa.AppProdutos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_carItem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private String productName;
    private int quantity;
    private double priceSnapshot;

}
