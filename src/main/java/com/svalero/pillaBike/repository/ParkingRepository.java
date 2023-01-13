package com.svalero.pillaBike.repository;

import com.svalero.pillaBike.domain.Parking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkingRepository extends CrudRepository<Parking, Long> {

    List<Parking> findAll();
}
