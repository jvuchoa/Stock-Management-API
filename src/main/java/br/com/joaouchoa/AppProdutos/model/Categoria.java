package br.com.joaouchoa.AppProdutos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_categoria",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "parent_id"})
    })
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "parente_id")
    private Categoria parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Categoria> children = new ArrayList<>();



}
