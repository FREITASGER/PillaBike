package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Bike;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.repository.BikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeServiceImpl implements BikeService {

    @Autowired
    BikeRepository bikeRepository; //conexion a BBDD

    @Autowired
    private ModelMapper modelMapper;

    //añadir bike
    @Override
    public Bike addBike(Bike bike) {
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
