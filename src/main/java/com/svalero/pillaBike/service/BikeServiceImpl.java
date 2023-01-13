package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Bike;
import com.svalero.pillaBike.domain.Parking;
import com.svalero.pillaBike.domain.Supplier;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.exception.ParkingNotFoundException;
import com.svalero.pillaBike.exception.SupplierNotFoundException;
import com.svalero.pillaBike.repository.BikeRepository;
import com.svalero.pillaBike.repository.ParkingRepository;
import com.svalero.pillaBike.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepository; //conexion a BBDD

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    private ModelMapper modelMapper;

    //añadir bike
    @Override
    public Bike addBike(Bike bike, long supplierId, long parkingId) throws SupplierNotFoundException, ParkingNotFoundException {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(SupplierNotFoundException::new);
        bike.setBikesSupplier(supplier);

        Parking parking = parkingRepository.findById(parkingId)
                .orElseThrow(ParkingNotFoundException::new);
        bike.setBikesInParking(parking);
        return bikeRepository.save(bike);
    }

    //borrar bike
    @Override
    public void deleteBike(long id) throws BikeNotFoundException {
        Bike bike = bikeRepository.findById(id)
                .orElseThrow(BikeNotFoundException::new);
        bikeRepository.delete(bike);
    }

    //modificar
    @Override
    public Bike modifyBike(long id, Bike newBike) throws BikeNotFoundException {
        Bike existingBike = bikeRepository.findById(id)
                .orElseThrow(BikeNotFoundException::new);
        newBike.setId(id);
        modelMapper.map(newBike, existingBike);
        return bikeRepository.save(existingBike);
    }

    //buscar todos
    @Override
    public List<Bike> findAll() {
        return bikeRepository.findAll();
    }

    //buscar por id
    @Override
    public Bike findById(long id) throws BikeNotFoundException {
        return bikeRepository.findById(id)
                .orElseThrow(BikeNotFoundException::new);
    }
}
