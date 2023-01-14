package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Supplier;
import com.svalero.pillaBike.exception.SupplierNotFoundException;

import java.util.List;

public interface SupplierService {

    Supplier addSupplier(Supplier supplier); //añadir
    void deleteSupplier(long id) throws SupplierNotFoundException;
    Supplier modifySupplier(long id, Supplier newSupplier) throws SupplierNotFoundException;
    List<Supplier> findAll();
    Supplier findById(long id) throws SupplierNotFoundException;
}
