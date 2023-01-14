package com.svalero.pillaBike.service;

import com.svalero.pillaBike.domain.Supplier;
import com.svalero.pillaBike.exception.SupplierNotFoundException;
import com.svalero.pillaBike.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository; //conexion a BBDD

    @Autowired
    private ModelMapper modelMapper;

    //añadir
    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    //borrar
    @Override
    public void deleteSupplier(long id) throws SupplierNotFoundException {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(SupplierNotFoundException::new);
        supplierRepository.delete(supplier);
    }

    //modificar
    @Override
    public Supplier modifySupplier(long id, Supplier newSupplier) throws SupplierNotFoundException {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(SupplierNotFoundException::new);
        newSupplier.setId(id);
        modelMapper.map(newSupplier, existingSupplier);
        return supplierRepository.save(existingSupplier);
    }

    //buscar todos
    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    //buscar por id
    @Override
    public Supplier findById(long id) throws SupplierNotFoundException {
        return supplierRepository.findById(id)
                .orElseThrow(SupplierNotFoundException::new);
    }
}
