package com.svalero.pillaBike.controller;

import com.svalero.pillaBike.domain.Bike;
import com.svalero.pillaBike.exception.BikeNotFoundException;
import com.svalero.pillaBike.exception.ErrorMessage;
import com.svalero.pillaBike.service.BikeService;
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
public class BikeController {

    @Autowired
    BikeService bikeService;

    private final Logger logger = LoggerFactory.getLogger(BikeController.class); //Creamos el objeto capaz de pintar las trazas en el log y lo asociamos a la clase que queremos controlar

    //añadir
    @PostMapping("/bikes")
    public ResponseEntity<Bike> addBike(@RequestBody Bike bike) {
        logger.debug("begin addBike");
        Bike newBike = bikeService.addBike(bike);
        logger.debug("end addBike");
        return ResponseEntity.status(HttpStatus.CREATED).body(newBike);
    }

    //borrar bike
    @DeleteMapping("/bikes/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable long id) throws BikeNotFoundException {
        bikeService.deleteBike(id);
        return ResponseEntity.noContent().build();
    }

    //modificar bike
    @PutMapping("/bikes/{id}")
    public ResponseEntity<Bike> modifyBike(@PathVariable long id, @RequestBody Bike bike) throws BikeNotFoundException {
        logger.debug("begin modifyBike");
        Bike modifiedBike = bikeService.modifyBike(id, bike);
        logger.debug("end modifyBike");
        return ResponseEntity.status(HttpStatus.OK).body(modifiedBike);
    }

    //buscar todos las bikes
    @GetMapping("/bikes")
    public ResponseEntity<List<Bike>> getBikes() {
        return ResponseEntity.ok(bikeService.findAll()); //me devuelve desde el service
    }

    //buscar libro por id
    @GetMapping("/bikes/{id}")
    public ResponseEntity<Bike> getBike(@PathVariable long id) throws BikeNotFoundException {
        logger.debug("begin getBike"); //Para indicar en el log que alguien ha llamado a esta parte
        Bike bike = bikeService.findById(id); //cojo el libro
        logger.debug("end getBike"); //Para indicar en el log que termina la llamada anterior
        return ResponseEntity.ok(bike); //devuelvo la respuesta con el book
    }

    //Excepción 404: Bike not found
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

