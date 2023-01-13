package com.svalero.pillaBike.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "parking")
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String ciudad;

    @Column
    private String direction;

    @OneToMany(mappedBy = "bikesInParking")
    @JsonBackReference(value = "bikes_parking")
    private List<Bike> bikes;

}
