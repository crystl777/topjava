package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.get(id).getUserId().equals(id) && repository.delete(id), id);
    }

    public Meal get(int id) {
        checkNotFoundWithId(repository.get(id).getUserId().equals(id), id);
        return repository.get(id);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(meal.getUserId().equals(userId), meal.getId());
        repository.save(meal, userId);
    }
}