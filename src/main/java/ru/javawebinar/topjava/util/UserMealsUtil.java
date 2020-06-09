package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMealsUtil {
    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (Meal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.merge(mealDate, meal.getCalories(), Integer::sum);
        }

        List<MealTo> mealsWithExcesses = new ArrayList<>();
        for (Meal meal : meals) {
            LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                mealsWithExcesses.add(new MealTo(dateTime, meal.getDescription(), meal.getCalories(),
                        caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return mealsWithExcesses;
    }
}
