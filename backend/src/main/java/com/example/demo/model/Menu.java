package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tbl_menus")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "canteen_id", nullable = false)
    private Canteen canteen; // Reference to Canteen

    @NotBlank(message = "Menu name cannot be blank")
    @Size(max = 100, message = "Menu name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status = "My Menu";

    @NotNull(message = "Creation date is required")
    private Date created_at = new Date();

    private Boolean is_active = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name="tbl_menu_menu_items",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns  = @JoinColumn(name = "item_id")
    )
    private List<MenuItem> items = new ArrayList<>();
}
