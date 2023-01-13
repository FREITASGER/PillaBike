package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Bike;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.exception.ParkingNotFoundException;
import com.svalero.pillaBike.exception.SupplierNotFoundException;

import java.util.List;

public interface BikeService {

    Bike addBike(Bike bike, long supplierId, long parkingId) throws SupplierNotFoundException, ParkingNotFoundException; //añadir
    void deleteBike(long id) throws BikeNotFoundException;
    Bike modifyBike(long id, Bike newBike) throws BikeNotFoundException;
    List<Bike> findAll();
    Bike findById(long id) throws BikeNotFoundException;
}
