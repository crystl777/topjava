package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {

    void save(Meal meal);

    void update(Meal meal);

    Meal get(String id);

    List<Meal> getListMeals();

    void delete(String id);

    void clear();

    int size();
}
