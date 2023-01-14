package com.svalero.pillaBike.exception;

public class SupplierNotFoundException extends Exception{

    public SupplierNotFoundException() {
        super("Supplier not found");
    }

    public SupplierNotFoundException(String message) {
        super(message);
    }
}
