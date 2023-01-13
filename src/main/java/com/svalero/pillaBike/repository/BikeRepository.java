package com.svalero.pillaBike.repository;

import com.svalero.pillaBike.domain.Bike;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BikeRepository extends CrudRepository<Bike, Long> {

    List<Bike> findAll(); //listado de bicis
}

