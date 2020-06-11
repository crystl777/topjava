package ru.javawebinar.topjava;

import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.storage.StorageDataMemory;
import ru.javawebinar.topjava.util.MealsUtil;

public class TestStorageData {

    public static void main(String[] args) {
        Storage storage = new StorageDataMemory();

        storage.save(MealsUtil.meals.get(0));

    }
}
