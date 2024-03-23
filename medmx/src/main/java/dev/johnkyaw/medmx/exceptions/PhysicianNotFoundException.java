package dev.johnkyaw.medmx.exceptions;

public class PhysicianNotFoundException extends RuntimeException {
    public PhysicianNotFoundException(Long id) {
        super("Could not find physician " + id);
    }

}
