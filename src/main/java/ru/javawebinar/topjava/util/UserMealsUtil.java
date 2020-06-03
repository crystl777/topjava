package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo1 = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo1.forEach(System.out::println);
        System.out.println("\n");

        List<UserMealWithExcess> mealsTo2 = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo2.forEach(System.out::println);
        System.out.println("\n");

        List<UserMealWithExcess> mealsTo3 = filteredByOneCycle(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo3.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.merge(mealDate, meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealsWithExcesses = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime dateTime = meal.getDateTime();
            addMealsWithExcess(meal, dateTime, startTime, endTime, mealsWithExcesses, caloriesSumPerDate, caloriesPerDay);
        }
        return mealsWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDate = meals.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(),
                        caloriesSumPerDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByOneCycle(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealsWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        int index = 0;
        int size = meals.size();
        recursiveCalculationSumCalories(meals, startTime, endTime, mealsWithExcesses, caloriesSumPerDate, index, size, caloriesPerDay);
        return mealsWithExcesses;
    }

    private static void recursiveCalculationSumCalories(List<UserMeal> meals, LocalTime startTime, LocalTime endTime,
                                                        List<UserMealWithExcess> mealsWithExcesses, Map<LocalDate, Integer> caloriesSumPerDate,
                                                        int index, int size, int caloriesPerDay) {
        if (index < size) {
            UserMeal meal = meals.get(index);
            LocalDateTime localDateTime = meal.getDateTime();
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            caloriesSumPerDate.merge(mealDate, meals.get(index).getCalories(), Integer::sum);
            recursiveCalculationSumCalories(meals, startTime, endTime, mealsWithExcesses, caloriesSumPerDate, index + 1, size, caloriesPerDay);
            addMealsWithExcess(meal, localDateTime, startTime, endTime, mealsWithExcesses, caloriesSumPerDate, caloriesPerDay);
        }
    }

    private static void addMealsWithExcess(UserMeal meal, LocalDateTime localDateTime, LocalTime startTime, LocalTime endTime,
                                           List<UserMealWithExcess> mealsWithExcesses, Map<LocalDate, Integer> caloriesSumPerDate,
                                           int caloriesPerDay) {
        if (TimeUtil.isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime)) {
            mealsWithExcesses.add(new UserMealWithExcess(localDateTime, meal.getDescription(), meal.getCalories(),
                    caloriesSumPerDate.get(localDateTime.toLocalDate()) > caloriesPerDay));
        }
    }
}
