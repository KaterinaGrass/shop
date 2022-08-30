package com.example.shop.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String title;
    @Column
    private boolean deleted;
    @Column
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")//
   // @JsonIgnore
  //  @JsonManagedReference
    private List<Product> products;

    public Category (String title){
        this.title = title;
        this.deleted = false;
        this.active = true;
    }


}
