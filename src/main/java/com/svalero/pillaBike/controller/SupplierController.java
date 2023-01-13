package com.svalero.pillaBike.controller;

import com.svalero.pillaBike.domain.Repair;
import com.svalero.pillaBike.domain.Supplier;
import com.svalero.pillaBike.exception.ErrorMessage;
import com.svalero.pillaBike.exception.RepairNotFoundException;
import com.svalero.pillaBike.exception.SupplierNotFoundException;
import com.svalero.pillaBike.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    private final Logger logger = LoggerFactory.getLogger(SupplierController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir
    @PostMapping("/suppliers")
    public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier) {
        logger.debug("begin addSupplier");
        Supplier newSupplier = supplierService.addSupplier(supplier);
        logger.debug("end addSupplier");
        return ResponseEntity.status(HttpStatus.CREATED).body(newSupplier);
    }

    //borrar
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable long id) throws SupplierNotFoundException {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    //modificar
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> modifySuppliers(@PathVariable long id, @RequestBody Supplier supplier) throws SupplierNotFoundException {
        logger.debug("begin modifySupplier");
        Supplier modifiedSupplier = supplierService.modifySupplier(id, supplier);
        logger.debug("end modifySupplier");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedSupplier);
    }

    //buscar todos
    @GetMapping("/suppliers")
    public ResponseEntity<List<Supplier>> getSuppliers() {
        return ResponseEntity.ok(supplierService.findAll()); //me devuelve desde el service
    }

    //buscar por id
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable long id) throws SupplierNotFoundException {
        logger.debug("begin getSupplier");
        Supplier supplier = supplierService.findById(id);
        logger.debug("end getSupplier");
        return ResponseEntity.ok(supplier);
    }

    //Excepción 404: Supplier not found
    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleSupplierNotFoundException(SupplierNotFoundException snfe) {
        logger.error((snfe.getMessage()), snfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, snfe.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    //Excetion 400: Bad request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(MethodArgumentNotValidException manve) {
        logger.error((manve.getMessage()), manve); //traza de log
        Map<String, String> errors = new HashMap<>(); //Montamos un Map de errores
        manve.getBindingResult().getAllErrors().forEach(error -> { //para la exception manve recorremos todos los campos
            String fieldName = ((FieldError) error).getField(); //Extraemos con getField el nombre del campo que no ha pasado la validación
            String message = error.getDefaultMessage(); // y el mensaje asociado
            errors.put(fieldName, message);
        });

        ErrorMessage errorMessage = new ErrorMessage(400, "Bad Request");
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    //cualquier exception. 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        logger.error((e.getMessage()), e); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(500, "Internal Server Error");
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

