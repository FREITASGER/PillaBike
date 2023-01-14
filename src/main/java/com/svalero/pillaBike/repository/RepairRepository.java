package com.svalero.pillaBike.repository;

import com.svalero.pillaBike.domain.Repair;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepairRepository extends CrudRepository<Repair, Long> {

    List<Repair> findAll();
}
