package com.svalero.pillaBike.repository;

import com.svalero.pillaBike.domain.Supplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    List<Supplier> findAll();
}
