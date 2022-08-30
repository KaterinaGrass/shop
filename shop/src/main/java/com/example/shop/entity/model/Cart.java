package com.example.shop.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private int totalItems;
    @Column
    private double totalPrice;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id")
    private User user;
    //@ManyToMany
   // @JoinTable(name = "cart_product",
   // joinColumns = @JoinColumn(name = "cart_id"),
   // inverseJoinColumns = @JoinColumn(name = "product_id"))
   // private List<Product> products;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItem> cartItem;

}
