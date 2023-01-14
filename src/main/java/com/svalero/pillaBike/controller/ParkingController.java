package com.svalero.pillaBike.controller;

import com.svalero.pillaBike.domain.Parking;
import com.svalero.pillaBike.exception.ErrorMessage;
import com.svalero.pillaBike.exception.ParkingNotFoundException;
import com.svalero.pillaBike.service.ParkingService;
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
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    private final Logger logger = LoggerFactory.getLogger(ParkingController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir
    @PostMapping("/parkings")
    public ResponseEntity<Parking> addParking(@RequestBody Parking parking) {
        logger.debug("begin addParking");
        Parking newParking = parkingService.addParking(parking);
        logger.debug("end addParking");
        return ResponseEntity.status(HttpStatus.CREATED).body(newParking);
    }

    //borrar
    @DeleteMapping("/parkings/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable long id) throws ParkingNotFoundException {
        parkingService.deleteParking(id);
        return ResponseEntity.noContent().build();
    }

    //modificar
    @PutMapping("/parkings/{id}")
    public ResponseEntity<Parking> modifyParking(@PathVariable long id, @RequestBody Parking parking) throws ParkingNotFoundException {
        logger.debug("begin modifyParking");
        Parking modifiedParking = parkingService.modifyParking(id, parking);
        logger.debug("end modifyParking");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedParking);
    }

    //buscar todos
    @GetMapping("/parkings")
    public ResponseEntity<List<Parking>> getParkings() {
        return ResponseEntity.ok(parkingService.findAll()); //me devuelve desde el service
    }

    //buscar por id
    @GetMapping("/parkings/{id}")
    public ResponseEntity<Parking> getParking(@PathVariable long id) throws ParkingNotFoundException {
        logger.debug("begin getParking");
        Parking parking = parkingService.findById(id);
        logger.debug("end getParking");
        return ResponseEntity.ok(parking);
    }

    //Excepción 404: Parking not found
    @ExceptionHandler(ParkingNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleParkingNotFoundException(ParkingNotFoundException pnfe) {
        logger.error((pnfe.getMessage()), pnfe); //traza de log
        ErrorMessage errorMessage = new ErrorMessage(404, pnfe.getMessage());
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

