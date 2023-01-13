package com.svalero.pillaBike.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    @Email
    private String email;

    @Column
    private String brand;

    @OneToMany(mappedBy = "bikesSupplier")
    @JsonBackReference(value = "bikes_supplier")
    private List<Bike> bikes;
}
