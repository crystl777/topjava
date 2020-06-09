package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    private static final Meal MEAL_1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
            "Завтрак", 500);
    private static final Meal MEAL_2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
            "Обед", 1000);
    private static final Meal MEAL_3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
            "Ужин", 500);
    private static final Meal MEAL_4 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
            "Еда на граничное значение", 100);
    private static final Meal MEAL_5 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
            "Завтрак", 1000);
    private static final Meal MEAL_6 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
            "Обед", 500);
    private static final Meal MEAL_7 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
            "Ужин", 410);

    public static final List<Meal> LIST_MEALS = Arrays.asList(MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, MEAL_7);

    public static final List<MealTo> LIST_MEALS_TO = UserMealsUtil.filteredByCycles(LIST_MEALS, LocalTime.of(0, 0),
            LocalTime.of(23, 0), 2000);
}
