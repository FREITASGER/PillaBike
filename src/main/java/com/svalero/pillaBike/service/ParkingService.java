package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Parking;
import com.svalero.pillaBike.exception.ParkingNotFoundException;

import java.util.List;

public interface ParkingService {

    Parking addParking(Parking parking); //añadir
    void deleteParking(long id) throws ParkingNotFoundException;
    Parking modifyParking(long id, Parking newParking) throws ParkingNotFoundException;
    List<Parking> findAll();
    Parking findById(long id) throws ParkingNotFoundException;
}
