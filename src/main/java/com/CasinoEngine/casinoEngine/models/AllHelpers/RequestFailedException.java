package com.CasinoEngine.casinoEngine.models.AllHelpers;

// This code is courtesy of Professors Ellen Spertus and Rasika Bhalerao of Northeastern University as used in
// Fundamentals of Computer Science II, CS 2510, 2024
/**
 * An exception indicating that a request could not be fulfilled.
 */
public class RequestFailedException extends Exception {
    /**
     * Creates an exception.
     *
     * @param message a message describing why the request could not be fulfilled
     */
    public RequestFailedException(String message) {
        super(message);
    }
}
