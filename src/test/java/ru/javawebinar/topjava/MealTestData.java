package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_ID = START_SEQ + 2;
    public static final Meal MEAL = new Meal(MEAL_ID,LocalDateTime.of(2020, 01, 30, 10, 0), "Завтрак", 500);


    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JUNE, 1,10, 0),
                "новая еда", 500);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
         assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
      //  assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }


    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL);
        updated.setCalories(300);
        updated.setDescription("ужин");
        return updated;
    }

}
