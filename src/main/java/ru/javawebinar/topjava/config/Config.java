package ru.javawebinar.topjava.config;

import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.storage.StorageDataMemory;

public class Config {

    private static final Config INSTANCE = new Config();
    private final Storage STORAGE;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        STORAGE = new StorageDataMemory();
    }

    public Storage getStorage() {
        return STORAGE;
    }
}
