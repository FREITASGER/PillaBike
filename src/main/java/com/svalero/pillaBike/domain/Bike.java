package com.svalero.pillaBike.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String capacity;

    @Column
    private boolean electric;

    @Column
    private String description;

    @Column
    private float price;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking bikesInParking;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier bikesSupplier;

    @OneToMany(mappedBy = "repairBikes")
    @JsonBackReference(value = "repair_bikes")
    private List<Repair> repairs;

    @ManyToMany
    @JoinTable(name = "order",
            joinColumns = @JoinColumn(name = "bike_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

}
