package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.exception.ExistStorageException;
import ru.javawebinar.topjava.exception.NotExistStorageException;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageDataMemory implements Storage {

    private final Map<String, Meal> map = new HashMap<>();

    @Override
    public void save(Meal meal) {
        if (map.containsKey(meal.getId())) {
            throw new ExistStorageException(meal.getId());
        } else {
            map.put(meal.getId(), meal);
        }
    }

    @Override
    public void update(Meal meal) {
        if (map.containsKey(meal.getId())) {
            map.put(meal.getId(), meal);
        } else {
            throw new NotExistStorageException(meal.getId());
        }
    }

    @Override
    public Meal get(String id) {
       return map.get(id);
    }

    @Override
    public List<Meal> getListMeals() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void delete(String id) {
        map.remove(id);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }
}
