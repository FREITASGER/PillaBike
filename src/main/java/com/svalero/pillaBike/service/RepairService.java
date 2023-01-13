package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Repair;
import com.svalero.pillaBike.exception.RepairNotFoundException;

import java.util.List;

public interface RepairService {

    Repair addRepair(Repair repair); //añadir
    void deleteRepair(long id) throws RepairNotFoundException;
    Repair modifyRepair(long id, Repair newRepair) throws RepairNotFoundException;
    List<Repair> findAll();
    Repair findById(long id) throws RepairNotFoundException;
}
