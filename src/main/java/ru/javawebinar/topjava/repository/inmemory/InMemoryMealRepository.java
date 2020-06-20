package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(MealsUtil.MEALS.get(0), 1);
        save(MealsUtil.MEALS.get(1), 1);
        save(MealsUtil.MEALS.get(2), 1);
        save(MealsUtil.MEALS.get(3), 2);
        save(MealsUtil.MEALS.get(4), 2);
        save(MealsUtil.MEALS.get(5), 2);
        save(MealsUtil.MEALS.get(6), 2);
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else if (repository.containsValue(meal) && meal.getUserId().equals(userId)) {
            return repository.computeIfPresent(meal.getId(), (key, value) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(Integer id, Integer userId) {
        if (repository.containsKey(userId) && repository.get(id).getUserId().equals(userId)) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(Integer id, Integer userId) {
        if (repository.containsKey(userId) && repository.get(id).getUserId().equals(userId)) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    public List<Meal> getAllByDate(Integer userId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .filter(meal -> DateTimeUtil.isBetweenInInclusive(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

