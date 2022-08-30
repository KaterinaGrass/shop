package com.example.shop.entity.model;

import com.example.shop.entity.enam.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable = false, updatable = false)
    private Integer id;
    @Column
    private String username;
    @Column
    private String password;
    @Column( unique = true, nullable = false)
    private String email;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Column
    private boolean active = true;


   // @ManyToMany
   // @JoinTable(name = "user_product",
          //  joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
          //  inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    //private List<Product> productList;//
    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "user")
    private List<Order> orders;
}
