package com.svalero.pillaBike.exception;

public class BikeNotFoundException extends Exception{

    public BikeNotFoundException() {
        super("Bike not found");
    }

    public BikeNotFoundException(String message) {
        super(message);
    }
}
