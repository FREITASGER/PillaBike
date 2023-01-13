package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Parking;
import com.svalero.pillaBike.exception.ParkingNotFoundException;
import com.svalero.pillaBike.repository.ParkingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    ParkingRepository parkingRepository; //conexion a BBDD

    @Autowired
    private ModelMapper modelMapper;

    //añadir
    @Override
    public Parking addParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    //borrar
    @Override
    public void deleteParking(long id) throws ParkingNotFoundException {
        Parking parking = parkingRepository.findById(id)
                .orElseThrow(ParkingNotFoundException::new);
        parkingRepository.delete(parking);
    }

    //modificar
    @Override
    public Parking modifyParking(long id, Parking newParking) throws ParkingNotFoundException {
        Parking existingParking = parkingRepository.findById(id)
                .orElseThrow(ParkingNotFoundException::new);
        newParking.setId(id);
        modelMapper.map(newParking, existingParking);
        return parkingRepository.save(existingParking);
    }

    //buscar todos
    @Override
    public List<Parking> findAll() {
        return parkingRepository.findAll();
    }

    //buscar por id
    @Override
    public Parking findById(long id) throws ParkingNotFoundException {
        return parkingRepository.findById(id)
                .orElseThrow(ParkingNotFoundException::new);
    }
}
