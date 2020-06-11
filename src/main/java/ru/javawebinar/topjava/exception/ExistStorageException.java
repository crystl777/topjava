package ru.javawebinar.topjava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String id) {
        super("Meal " + id + " already exist", id);
    }
}
