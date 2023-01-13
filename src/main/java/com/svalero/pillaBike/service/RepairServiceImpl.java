package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Bike;
import com.svalero.pillaBike.domain.Repair;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.exception.RepairNotFoundException;
import com.svalero.pillaBike.repository.BikeRepository;
import com.svalero.pillaBike.repository.RepairRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    RepairRepository repairRepository; //conexion a BBDD

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    private ModelMapper modelMapper;

    //añadir
    @Override
    public Repair addRepair(Repair repair, long bikeId) throws BikeNotFoundException{

        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(BikeNotFoundException::new);
        repair.setRepairBikes(bike);

        return repairRepository.save(repair);
    }

    //borrar
    @Override
    public void deleteRepair(long id) throws RepairNotFoundException {
        Repair repair = repairRepository.findById(id)
                .orElseThrow(RepairNotFoundException::new);
        repairRepository.delete(repair);
    }

    //modificar
    @Override
    public Repair modifyRepair(long id, Repair newRepair) throws RepairNotFoundException {
        Repair existingRepair = repairRepository.findById(id)
                .orElseThrow(RepairNotFoundException::new);
        newRepair.setId(id);
        modelMapper.map(newRepair, existingRepair);
        return repairRepository.save(existingRepair);
    }

    //buscar todos
    @Override
    public List<Repair> findAll() {
        return repairRepository.findAll();
    }

    //buscar por id
    @Override
    public Repair findById(long id) throws RepairNotFoundException {
        return repairRepository.findById(id)
                .orElseThrow(RepairNotFoundException::new);
    }
}
