package com.ShopAll.Methaporce.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @JsonIgnore
    private Long id;

    private String name;
    public Rol() {
        // default constructor
    }

    public Rol(String name){
        this.name = name;
    }

}
