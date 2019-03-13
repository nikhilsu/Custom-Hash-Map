package com.fullstory;

class KeyNotFoundException extends Throwable {
    private final String message;

    KeyNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "KeyNotFoundException{" +
                "No key ='" + message + '\'' +
                '}';
    }
}
