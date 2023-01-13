package com.svalero.pillaBike.exception;

public class ParkingNotFoundException extends Exception{

    public ParkingNotFoundException() {
        super("Parking not found");
    }

    public ParkingNotFoundException(String message) {
        super(message);
    }
}
