package ru.javawebinar.topjava.exception;

public class StorageException extends RuntimeException {
    private final String id;

    public StorageException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
