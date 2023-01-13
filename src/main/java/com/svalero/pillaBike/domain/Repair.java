package com.svalero.pillaBike.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "repairs")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int number;

    @Column
    private String comment;

    @Column
    private LocalDate repairDate;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    private Bike repairBikes;

}
