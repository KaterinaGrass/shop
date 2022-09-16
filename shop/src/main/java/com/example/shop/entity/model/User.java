package com.example.shop.entity.model;


import com.example.shop.entity.enam.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
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
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToMany(cascade = CascadeType.REMOVE,mappedBy = "user")
    private List<Order> orders;


}
