package com.svalero.pillaBike.controller;

import com.svalero.pillaBike.domain.Parking;
import com.svalero.pillaBike.domain.Repair;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.exception.ErrorMessage;
import com.svalero.pillaBike.exception.ParkingNotFoundException;
import com.svalero.pillaBike.exception.RepairNotFoundException;
import com.svalero.pillaBike.service.RepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RepairController {

    @Autowired
    RepairService repairService;

    private final Logger logger = LoggerFactory.getLogger(RepairController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir
    @PostMapping("/bike/{bikeId}/repairs")
    public ResponseEntity<Repair> addRepair(@Valid @PathVariable long bikeId, @RequestBody Repair repair) throws BikeNotFoundException {
        logger.debug("begin addRepair");
        Repair newRepair = repairService.addRepair(repair, bikeId);
        logger.debug("end addRepair");
        return new ResponseEntity<>(newRepair, HttpStatus.CREATED);
    }

    //borrar
    @DeleteMapping("/repairs/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable long id) throws RepairNotFoundException {
        repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }

    //modificar
    @PutMapping("/repairs/{id}")
    public ResponseEntity<Repair> modifyRepair(@PathVariable long id, @RequestBody Repair repair) throws RepairNotFoundException {
        logger.debug("begin modifyRepair");
        Repair modifiedRepair = repairService.modifyRepair(id, repair);
        logger.debug("end modifyRepair");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedRepair);
    }

    //buscar todos
    @GetMapping("/repairs")
    public ResponseEntity<List<Repair>> getRepairs() {
        return ResponseEntity.ok(repairService.findAll()); //me devuelve desde el service
    }

    //buscar por id
    @GetMapping("/repairs/{id}")
    public ResponseEntity<Repair> getRepair(@PathVariable long id) throws RepairNotFoundException {
        logger.debug("begin getRepair");
        Repair repair = repairService.findById(id);
        logger.debug("end getRepair");
        return ResponseEntity.ok(repair);
    }

    //Excepción 404: Repair not found
    @ExceptionHandler(RepairNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleRepairNotFoundException(RepairNotFoundException rnfe) {
        logger.error((rnfe.getMessage()), rnfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, rnfe.getMessage());
        return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleBikeNotFoundException(BikeNotFoundException bnfe) {
        logger.error((bnfe.getMessage()), bnfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, bnfe.getMessage());
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

